package cz.muni.fi.services.interfaces;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Milos Ferencik 23/4/2020
 */
public interface BeanMappingService {
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);
    public Mapper getMapper();
}
