package cz.muni.fi.rest;

import cz.muni.fi.config.ServiceConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @Author Ondrej Slimak on 07/05/2020.
 */

@Configuration
@EnableWebMvc
@Import({ServiceConfiguration.class})
@ComponentScan(basePackages = "cz.muni.fi.rest.controllers")
public class RestConfiguration implements WebMvcConfigurer {

    @Bean
    public MappingJackson2HttpMessageConverter jackson2Http(){
        return new MappingJackson2HttpMessageConverter();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2Http());
    }

    @Bean
    public InternalResourceViewResolver resolver(){
        return new InternalResourceViewResolver("/static/", ".html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/*").addResourceLocations("classpath:/static");
    }
}
