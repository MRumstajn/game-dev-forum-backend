package com.mrumstajn.gamedevforum.config;

import net.croz.nrich.search.api.converter.StringToEntityPropertyMapConverter;
import net.croz.nrich.search.api.converter.StringToTypeConverter;
import net.croz.nrich.search.api.factory.RepositoryFactorySupportFactory;
import net.croz.nrich.search.api.factory.SearchExecutorJpaRepositoryFactoryBean;
import net.croz.nrich.search.converter.DefaultStringToEntityPropertyMapConverter;
import net.croz.nrich.search.converter.DefaultStringToTypeConverter;
import net.croz.nrich.search.factory.SearchRepositoryFactorySupportFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories(basePackages = "com.mrumstajn", repositoryFactoryBeanClass = SearchExecutorJpaRepositoryFactoryBean.class)
@EnableTransactionManagement
@Configuration
public class ApplicationConfig {

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
}
