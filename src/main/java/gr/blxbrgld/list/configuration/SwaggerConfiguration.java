package gr.blxbrgld.list.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration Class
 * @author blxbrgld
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String CONTROLLERS_PACKAGE = "gr.blxbrgld.list.rest";

    /**
     * Swagger's Docket Bean [ /swagger-ui.html, /v2/api-docs ]
     * @return {@link Docket}
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(CONTROLLERS_PACKAGE))
            .build()
            .apiInfo(apiInfo());
    }

    /**
     * Construct The Swagger's API Info
     * @return {@link ApiInfo}
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("List Application")
            .description("Swagger Documentation For List Application.")
            .version("5.0")
            .build();
    }
}
