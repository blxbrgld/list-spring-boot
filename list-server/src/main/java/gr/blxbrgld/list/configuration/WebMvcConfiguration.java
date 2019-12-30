package gr.blxbrgld.list.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * WebMvcConfiguration
 * @author blxbrgld
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * Adds the /api path prefix to all REST controllers
     * @param configurer {@link PathMatchConfigurer}
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    /**
     * Surrenders routing control to Angular
     * @param registry {@link ResourceHandlerRegistry}
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/resources/")
            .resourceChain(true)
            .addResolver(
                new PathResourceResolver() {
                    /**
                     * {@inheritDoc}
                     */
                    @Override @SuppressWarnings("NullableProblems")
                    protected Resource getResource(String path, Resource resource) throws IOException {
                        Resource relative = resource.createRelative(path);
                        return relative.exists() && relative.isReadable() ? relative : new ClassPathResource("/resources/index.html");
                    }
                }
            );
    }
}
