package com.lzb.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * 容器<br/>
 * Created on : 2023-10-05 08:50
 * @author lizebin
 */
public interface Context {

    <T> Optional<T> get(Ref<T> ref);

    /**
     * <br/>
     * Created on : 2023-10-20 10:15
     * @author lizebin
     */
    @Getter
    @EqualsAndHashCode
    class Ref<T> {
        private Type containerType;
        private Class<T> componentType;
        private Annotation qualifier;

        public Ref(Type type) {
            init(type);
        }

        public Ref(Class<T> componentType) {
            init(componentType);
        }

        public Ref(@NonNull Class<T> componentType, Annotation qualifier) {
            if (Objects.isNull(qualifier)) {
                init(componentType);
                return;
            }
            this.componentType = componentType;
            this.qualifier = qualifier;
        }

        public static Ref of(Type type) {
            return new Ref<>(type);
        }

        public static <T> Ref<T> of(Class<T> componentClass) {
            return new Ref<>(componentClass);
        }

        public boolean isContainerType() {
            return containerType != null;
        }

        protected Ref() {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            init(type);
        }

        private void init(Type type) {
            if (type instanceof ParameterizedType container) {
                this.componentType = (Class<T>) container.getActualTypeArguments()[0];
                this.containerType = container.getRawType();
            } else {
                this.componentType = (Class<T>) type;
            }
        }

    }
}
