package cz.muni.fi.services.interfaces;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Milos Ferencik 23/4/2020
 */
public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
