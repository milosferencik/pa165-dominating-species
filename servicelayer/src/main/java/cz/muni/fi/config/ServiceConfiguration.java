package cz.muni.fi.config;

import dao.config.MainConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainConfiguration.class)
@ComponentScan(basePackages = "cz.muni.fi")
public class ServiceConfiguration {
}
