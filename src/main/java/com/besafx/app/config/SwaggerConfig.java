package com.besafx.app.config;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
@Import({
        springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class
})
public class SwaggerConfig implements WebMvcConfigurer {

    private final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Bean
    public Docket swaggerSpringfoxDocket() {
        Contact contact = new Contact(
                "FlairsTech © 2018",
                "https://flairstech.com",
                "bassam.mahdy@flairstech.com");

        List<VendorExtension> vext = new ArrayList<>();
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Code Checker Backend API")
                .description("<p style=\"color: #89BF04\">Project Manager: Eman Khairy</p><p style=\"color: #89BF04\">Developer: Bassam Almahdy</p>")
                .version("1.0.0")
                .termsOfServiceUrl("https://flairstech.com")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .extensions(vext)
                .build();

        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder()
                        .code(200)
                        .message("Successfully Calling")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Bad Request")
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("You are not authorized to view the resource")
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Accessing the resource you were trying to reach is forbidden")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("The resource you were trying to reach is not found")
                        .build(),
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Internal Server Error")
                        .build());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class, java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, globalResponses)
//                .globalResponseMessage(RequestMethod.PUT, globalResponses)
//                .globalResponseMessage(RequestMethod.POST, globalResponses)
//                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
//                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .tags(
                        new Tag("Authorization (Using JSON Web Token)", "", 1),
                        new Tag("JIRA Integration", "", 2),
                        new Tag("Sonar Cube Integration", "<p style=\"color: #e53935; font-weight:bold\">Under Construction</p>", 3),
                        new Tag("Rest API - Users", "<p style=\"color: #e53935; font-weight:bold\">Under Construction</p>", 4),
                        new Tag("Rest API - Projects", "<p style=\"color: #e53935; font-weight:bold\">Under Construction</p>", 5)
                );

        docket = docket
                .select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
                .apiInfo(apiInfo)
                .directModelSubstitute(Temporal.class, String.class);

        return docket;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(true)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(true)
                .docExpansion(DocExpansion.NONE)
                .filter(true)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(true)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/template/swagger-ui.html");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
