package cz.muni.fi.pa165.dominatingspecies.service.services.interfaces;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Milos Ferencik 23/4/2020
 */
public interface BeanMappingService {

    /**
     * Maps a collection of objects to specific class
     * @param objects collections of objects to be mapped
     * @param mapToClass class to which the objects should be mapped
     * @param <T> type of class to which the collection should be mapped
     * @return a collection of newly mapped objects
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps a single object to specific class
     * @param u object to be mapped
     * @param mapToClass class to which the object should be mapped
     * @param <T> type of class to which the object should be mapped
     * @return a newly mapped object
     */
    <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * getter method for mapper
     * @return mapper instance
     */
    Mapper getMapper();
}
