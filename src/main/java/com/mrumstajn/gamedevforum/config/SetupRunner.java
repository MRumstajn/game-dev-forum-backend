package com.mrumstajn.gamedevforum.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.auth.service.query.AuthorizationQueryService;
import com.mrumstajn.gamedevforum.category.dto.request.CreateCategoryRequest;
import com.mrumstajn.gamedevforum.category.dto.request.SearchCategoriesRequestPageable;
import com.mrumstajn.gamedevforum.category.entity.Category;
import com.mrumstajn.gamedevforum.chat.server.WebSocketServer;
import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.user.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.section.entity.Section;
import com.mrumstajn.gamedevforum.section.dto.request.CreateSectionRequest;
import com.mrumstajn.gamedevforum.section.dto.request.SearchSectionRequest;
import com.mrumstajn.gamedevforum.category.service.command.CategoryCommandService;
import com.mrumstajn.gamedevforum.user.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.section.service.command.SectionCommandService;
import com.mrumstajn.gamedevforum.category.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.user.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.section.service.query.SectionQueryService;
import com.mrumstajn.gamedevforum.user.dto.request.CreateForumUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SetupRunner {
    private final static String ALPHA = "abcdefghijABCDEFGHIJ123456789";

    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final SectionQueryService sectionQueryService;

    private final SectionCommandService sectionCommandService;

    private final CategoryQueryService categoryQueryService;

    private final CategoryCommandService categoryCommandService;

    private final ForumUserQueryService forumUserQueryService;

    private final ForumUserCommandService forumUserCommandService;
    
    private final AuthorizationQueryService authorizationQueryService;
    
    private final ObjectMapper objectMapper;

    private final ModelMapper modelMapper;

    @Bean
    public CommandLineRunner setup(@Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        return args -> {
            logger.info("Running setup...");

            // generate JWT secret if not provided
            String jwtSecret = System.getenv("jwtsecret");
            if (jwtSecret == null){
                logger.info("jwtsecret env variable not found, generating secret...");
                System.setProperty("jwtsecret", generateRandomPassword(64));
            }
            else {
                System.setProperty("jwtsecret", jwtSecret);
                logger.info("jwtsecret env variable found, using that value");
            }

            // create root sections
            Section newsSection = null;

            List<Section> matchedSections = sectionQueryService.search(generateSectionSearchRequest("Forum"));
            if (matchedSections.size() == 0) {
                logger.info("Creating root section Forum...");
                Section forumSection = sectionCommandService.create(generateCreateSectionRequest("Forum"));

                if (forumSection == null) {
                    throw new RuntimeException("Failed to create root \"Forum\" section on setup");
                }
            } else {
                logger.info("Root section Forum already exists, skip create");
            }

            matchedSections = sectionQueryService.search(generateSectionSearchRequest("News"));
            if (matchedSections.size() > 0) {
                newsSection = matchedSections.get(0);
                logger.info("Root section News already exists, skip create");
            } else {
                logger.info("Creating root section News...");
                newsSection = sectionCommandService.create(generateCreateSectionRequest("News"));
                if (newsSection == null) {
                    throw new RuntimeException("Failed to create root \"News\" section on setup");
                }
            }

            // create root categories
            SearchCategoriesRequestPageable searchCategoryRequest = new SearchCategoriesRequestPageable();
            searchCategoryRequest.setTitle("News");
            searchCategoryRequest.setSectionId(newsSection.getId());

            Page<Category> matchedCategories = categoryQueryService.search(searchCategoryRequest);

            Category newsCategory;
            if (matchedCategories.getContent().size() > 0) {
                newsCategory = matchedCategories.getContent().get(0);
                logger.info("Root category News already exists, skip create");
            } else {
                CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
                createCategoryRequest.setSectionIdentifier(newsSection.getId());
                createCategoryRequest.setTitle("News");

                logger.info("Creating root category News...");
                newsCategory = categoryCommandService.create(createCategoryRequest);
            }

            if (newsCategory == null) {
                throw new RuntimeException("Failed to create \"News\" category on setup");
            }

            // create admin user and assign admin privileges
            ForumUser admin = forumUserQueryService.getByUsernameExact("Admin");
            String defaultAdminPassword = null;
            if (admin == null) {
                defaultAdminPassword = generateRandomPassword(10);

                CreateForumUserRequest createForumUserRequest = new CreateForumUserRequest();
                createForumUserRequest.setUsername("Admin");
                createForumUserRequest.setPassword(defaultAdminPassword);

                logger.info("Creating admin user...");
                admin = forumUserCommandService.create(createForumUserRequest);
            } else {
                logger.info("Admin user already exists, skip create");
            }

            if (admin == null) {
                throw new RuntimeException("Failed to create admin user");
            }

            if (admin.getRole() != ForumUserRole.ADMIN) {
                logger.info("Assigning admin with ADMIN role");
                admin = forumUserCommandService.grantRole(admin.getId(), ForumUserRole.ADMIN);
            }

            if (admin.getRole() != ForumUserRole.ADMIN) {
                throw new RuntimeException("Failed to assign admin privileges to admin user");
            }

            // start the websocket server for handling chat messages
            WebSocketServer webSocketServer = new WebSocketServer(10000);
            webSocketServer.setAuthorizationQueryService(authorizationQueryService);
            webSocketServer.setObjectMapper(objectMapper);
            webSocketServer.setModelMapper(modelMapper);

            taskExecutor.execute(webSocketServer::start);
            logger.info("Started web socket server");

            logger.info("Setup complete!");

            if (defaultAdminPassword != null) {
                logger.info("Default admin login is: ");
                logger.info("\tUsername: Admin");
                logger.info("\tPassword: " + defaultAdminPassword);
                logger.info("<<< Change the default password! >>>");
            }
        };
    }

    private SearchSectionRequest generateSectionSearchRequest(String title) {
        SearchSectionRequest searchSectionRequest = new SearchSectionRequest();
        searchSectionRequest.setTitle(title);

        return searchSectionRequest;
    }

    private CreateSectionRequest generateCreateSectionRequest(String title) {
        CreateSectionRequest createSectionRequest = new CreateSectionRequest();
        createSectionRequest.setTitle(title);

        return createSectionRequest;
    }

    private String generateRandomPassword(int chars) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i <= chars; i++) {
            password.append(ALPHA.charAt(random.nextInt(ALPHA.length())));
        }

        return password.toString();
    }
}
