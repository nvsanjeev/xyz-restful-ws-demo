package com.xyz.cta.rest.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT =
            new Contact("Joe Xyz",
                          "http://www.xyz.com",
                         "info.xyz@xyz.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("XYZ Contact Restful WS Api Documentation",
            "This is for XYZ users to provide their names, email address, street address and phone numbers.",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
             new ArrayList());


    private static final Set<String> DEFAULT_CONSUMES =
            new HashSet<String>(Arrays.asList("application/jason","application/xml"));

    private static final Set<String> DEFAULT_PRODUCES =
            new HashSet<String>(Arrays.asList("application/jason","application/xml"));

    @Bean
    public Docket api(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .consumes(DEFAULT_CONSUMES)
                .produces(DEFAULT_PRODUCES);

        return docket;
    }
}
