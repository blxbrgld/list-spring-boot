package gr.blxbrgld.list.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration
 * @author blxbrgld
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String FORWARD = "forward:/index.html";

    /**
     * Adds the /api path prefix to all REST controllers
     * @param configurer {@link PathMatchConfigurer}
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    /**
     * Redirect all to index.html except everything under /api or urls ending with a file extension (i.e. ".js")
     * @param registry {@link ViewControllerRegistry}
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(FORWARD);
        registry.addViewController("/{x:[\\w\\-]+}").setViewName(FORWARD);
        registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}").setViewName(FORWARD);
    }
}
