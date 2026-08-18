package com.example.MapperProject.config;

import com.example.MapperProject.enums.Department;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        mm.addConverter(new AbstractConverter<Department, String>() {
            @Override
            protected String convert(Department source) {
                return source == null ? null : source.name();
            }
        });

        mm.addConverter(new AbstractConverter<String, Department>() {
            @Override
            protected Department convert(String source) {
                return source == null ? null : Department.valueOf(source.toUpperCase());
            }
        });

        return mm;
    }
}
