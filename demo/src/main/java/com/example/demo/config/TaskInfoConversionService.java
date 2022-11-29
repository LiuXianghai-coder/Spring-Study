package com.example.demo.config;

import org.apache.ibatis.reflection.ReflectorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * @author lxh
 */
@Service
public class TaskInfoConversionService
        implements ConversionService {

    private final StringToReflectFactoryConvert convert;

    @Autowired
    public TaskInfoConversionService(StringToReflectFactoryConvert convert) {
        this.convert = convert;
    }

    @Override
    public boolean canConvert(Class<?> sourceType, @Nonnull Class<?> targetType) {
        return sourceType == String.class && targetType == ReflectorFactory.class;
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, @Nonnull TypeDescriptor targetType) {
        assert sourceType != null;
        return canConvert(sourceType.getObjectType(), targetType.getObjectType());
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return (T) convert.convert((String) source);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return convert(source, targetType.getObjectType());
    }
}
