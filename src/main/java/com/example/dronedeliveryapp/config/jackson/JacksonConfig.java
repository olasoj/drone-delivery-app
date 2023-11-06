package com.example.dronedeliveryapp.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Instant;

@Configuration
public class JacksonConfig {

    @Primary
    @Bean("jacksonObjectMapper")
    public ObjectMapper createJacksonObjectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Instant.class, DeliveryDroneAppInstantJacksonAdapter.instantJsonDeserializer);
        simpleModule.addSerializer(Instant.class, DeliveryDroneAppInstantJacksonAdapter.instantJsonSerializer);

        objectMapper.registerModule(simpleModule);

        JavaTimeModule timeModule = new JavaTimeModule();
        objectMapper.registerModule(timeModule);
        return objectMapper;
    }

}
