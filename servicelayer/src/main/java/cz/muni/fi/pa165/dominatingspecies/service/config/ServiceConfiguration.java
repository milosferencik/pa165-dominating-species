package cz.muni.fi.pa165.dominatingspecies.service.config;

import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalInFoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.AnimalUpdateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.EnvironmentListDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.FoodChainDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserCreateDTO;
import cz.muni.fi.pa165.dominatingspecies.api.dto.UserDTO;
import cz.muni.fi.pa165.dominatingspecies.dao.config.MainConfiguration;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Animal;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.AnimalInFoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.Environment;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.FoodChain;
import cz.muni.fi.pa165.dominatingspecies.dao.entities.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MainConfiguration.class)
@ComponentScan(basePackages = {"cz.muni.fi.pa165.dominatingspecies.service", "cz.muni.fi.pa165.dominatingspecies.api.facades"})
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
