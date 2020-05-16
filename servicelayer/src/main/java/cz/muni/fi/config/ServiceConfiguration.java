package cz.muni.fi.config;

import cz.muni.fi.dto.*;
import dao.config.MainConfiguration;
import dao.entities.*;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainConfiguration.class)
@ComponentScan(basePackages = {"cz.muni.fi", "cz.muni.fi.facades"})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
            mapping(User.class, UserCreateDTO.class);

            mapping(Animal.class, AnimalCreateDTO.class);
            mapping(Animal.class, AnimalListDTO.class);
            mapping(Animal.class, AnimalUpdateDTO.class);

            mapping(AnimalInFoodChain.class, AnimalInFoodChainDTO.class);

            mapping(Environment.class, EnvironmentDTO.class);
            mapping(Environment.class, EnvironmentCreateDTO.class);
            mapping(Environment.class, EnvironmentListDTO.class);

            mapping(FoodChain.class, FoodChainCreateDTO.class);
            mapping(FoodChain.class, FoodChainDTO.class);
        }
    }
}
