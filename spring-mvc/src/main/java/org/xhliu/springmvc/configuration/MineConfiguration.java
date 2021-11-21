package org.xhliu.springmvc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.xhliu.springmvc.entity.Person;

@Configuration
public class MineConfiguration {
    public static class StringToPersonConvert implements Converter<String, Person> {
        public Person convert(String source) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(source, Person.class);
        }
    }

    @Bean(name = "stringToPersonConvert")
    public StringToPersonConvert convert() {
        return new StringToPersonConvert();
    }
}
