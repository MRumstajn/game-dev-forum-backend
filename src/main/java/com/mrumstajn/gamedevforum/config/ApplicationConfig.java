package com.mrumstajn.gamedevforum.config;

import net.croz.nrich.search.api.converter.StringToEntityPropertyMapConverter;
import net.croz.nrich.search.api.converter.StringToTypeConverter;
import net.croz.nrich.search.api.factory.RepositoryFactorySupportFactory;
import net.croz.nrich.search.api.factory.SearchExecutorJpaRepositoryFactoryBean;
import net.croz.nrich.search.converter.DefaultStringToEntityPropertyMapConverter;
import net.croz.nrich.search.converter.DefaultStringToTypeConverter;
import net.croz.nrich.search.factory.SearchRepositoryFactorySupportFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories(basePackages = "com.mrumstajn", repositoryFactoryBeanClass = SearchExecutorJpaRepositoryFactoryBean.class)
@EnableTransactionManagement
@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public StringToTypeConverter<Object> defaultStringToTypeConverter() {
        List<String> dateFormatList = Arrays.asList("dd.MM.yyyy.", "dd.MM.yyyy.'T'HH:mm");
        List<String> decimalFormatList = Arrays.asList("#0.00", "#0,00");
        String booleanTrueRegexPattern = "^(?i)\\s*(true|yes)\\s*$";
        String booleanFalseRegexPattern = "^(?i)\\s*(false|no)\\s*$";

        return new DefaultStringToTypeConverter(dateFormatList, decimalFormatList, booleanTrueRegexPattern, booleanFalseRegexPattern);
    }

    @Bean
    public StringToEntityPropertyMapConverter stringToEntityPropertyMapConverter(List<StringToTypeConverter<?>> stringToTypeConverterList) {
        return new DefaultStringToEntityPropertyMapConverter(stringToTypeConverterList);
    }

    @Bean
    public RepositoryFactorySupportFactory searchRepositoryFactorySupportFactory(StringToEntityPropertyMapConverter stringToEntityPropertyMapConverter) {
        return new SearchRepositoryFactorySupportFactory(stringToEntityPropertyMapConverter);
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }

    /*@Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }*/

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> dispatcherServletRegistration() {
        ServletRegistrationBean<DispatcherServlet> registration = new DispatcherServletRegistrationBean(
                dispatcherServlet(), "/api/");
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        return registration;
    }

    @Bean
    public DispatcherServletPath dispatcherServletPath(){
        return () -> "/api";
    }

    @Bean
    public Integer webSocketServerPort(){
        return 10000;
    }
}
