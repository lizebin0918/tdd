package com.lzb.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.lzb.container.exception.IllegalComponentException;
import jakarta.inject.Qualifier;

import static java.util.Arrays.stream;

/**
 * <br/>
 * Created on : 2023-10-29 17:13
 * @author mac
 */
public record Injectable<E extends AccessibleObject>(E element, List<ComponentRef> componentRefs) {


    public static <E extends Executable> Injectable<E> of(E executable) {
        return new Injectable<>(executable, stream(executable.getParameters()).map(p -> ComponentRef.of(p.getParameterizedType(), getQualifier(p))).toList());
    }

    public static Injectable<Field> of(Field field) {
        return new Injectable<>(field, List.of(ComponentRef.of(field.getGenericType(), getQualifier(field))));
    }

    private static Annotation getQualifier(AnnotatedElement element) {
        List<Annotation> qualifierAnnotations = stream(element.getAnnotations()).filter(a -> a.annotationType()
                .isAnnotationPresent(Qualifier.class)).toList();
        if (qualifierAnnotations.size() > 1) throw new IllegalComponentException();
        return qualifierAnnotations.stream().findFirst().orElse(null);
    }

    public Object[] toDependencies(Context context) {
        return componentRefs.stream()
                .map(component -> context.get(component))
                .map(Optional::orElseThrow)
                .toArray(Object[]::new);
    }

}
