package com.lzb.container;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.AbstractComponent;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentDirectlyInstance;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.provider.ConstructorInjectProvider;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 *
 * 无需注入的component
 * `@Inject`标记构造方法，通过构造函数注入
 * `@Inject`标记字段，通过字段注入
 * `@Inject`标记方法，通过方法注入
 * 通过tag选择对应的依赖
 *
 * 父类注入
 * 对于非singleton场景，不可能存在循环引用
 *
 * Created on : 2023-09-30 22:50
 * @author lizebin
 */
public class ContextUnitTest extends BaseUnitTest {

    ContextConfig contextConfig;

    @BeforeEach
    public void beforeEach() {
        contextConfig = new ContextConfig();
    }

    @Nested
    @DisplayName("没有依赖的实例，直接注入容器")
    class InstanceWithoutInject {

        @Test
        @DisplayName("直接实例化到容器")
        void should_instance_to_context_directly() {
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            contextConfig.bind(Component.class, instance);
            Context context = contextConfig.getContext();
            Component component = context.get(Component.class)
                    .orElseThrow();
            Assertions.assertSame(instance, component);
        }

        @Test
        @DisplayName("获取不存在的实例")
        void should_throw_exception_when_instance_not_defined() {
            // assertThrows(DependencyNotFoundException.class, () -> context.getOrThrow(Component.class));
            // 这样更加友好，毕竟这是一个接口，可能还没有实现
            Context context = contextConfig.getContext();
            assertThat(context.get(Component.class).isEmpty());
        }

        @Test
        @DisplayName("抽象类不能实例化到容器")
        void should_exception_when_class_is_abstract() {
            assertThrows(IllegalComponentException.class, () -> new ConstructorInjectProvider<>(AbstractComponent.class));
        }

        @Test
        @DisplayName("接口不能实例化到容器")
        void should_exception_when_class_is_interface() {
            assertThrows(IllegalComponentException.class, () -> new ConstructorInjectProvider<>(Component.class));
        }

    }

    @Test
    @DisplayName("could get Provider<T> from context")
    void should_retrieve_bind_type_as_provider() {
        Component instance = new Component() { };
        contextConfig.bind(Component.class, instance);
        Context context = contextConfig.getContext();
        ParameterizedType type = (ParameterizedType) new TypeLiteral<Provider<Component>>() { }.getType();
        assertThat(Provider.class).isEqualTo(type.getRawType());
        assertThat(Component.class).isEqualTo(type.getActualTypeArguments()[0]);
    }

    static abstract class TypeLiteral<T> {
        public Type getType() {
            return ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

}
