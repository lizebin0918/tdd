package com.lzb.container;

import java.lang.annotation.Annotation;
import java.util.List;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.AbstractComponent;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentDirectlyInstance;
import com.lzb.container.constructor.Dependency;
import com.lzb.container.constructor.DependencyA;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.provider.InjectProvider;
import com.lzb.container.qualifier.QualifierInjectConstructor;
import jakarta.inject.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

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
class ContextUnitTest extends BaseUnitTest {

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
            Component component = (Component) context.get(ComponentRef.of(Component.class))
                    .orElseThrow();
            assertSame(instance, component);
        }

        @Test
        @DisplayName("获取不存在的实例")
        void should_throw_exception_when_instance_not_defined() {
            // assertThrows(DependencyNotFoundException.class, () -> context.getOrThrow(Component.class));
            // 这样更加友好，毕竟这是一个接口，可能还没有实现
            Context context = contextConfig.getContext();
            assertThat(context.get(ComponentRef.of(Component.class)).isEmpty()).isTrue();
        }

        @Test
        @DisplayName("抽象类不能实例化到容器")
        void should_exception_when_class_is_abstract() {
            assertThrows(IllegalComponentException.class, () -> new InjectProvider<>(AbstractComponent.class));
        }

        @Test
        @DisplayName("接口不能实例化到容器")
        void should_exception_when_class_is_interface() {
            assertThrows(IllegalComponentException.class, () -> new InjectProvider<>(Component.class));
        }

    }

    @Test
    @DisplayName("could get Provider<T> from context")
    void should_retrieve_bind_type_as_provider() {
        Component instance = new Component() { };
        contextConfig.bind(Component.class, instance);
        Context context = contextConfig.getContext();
        /*ParameterizedType type = new TypeLiteral<Provider<Component>>() { }.getType();
        assertThat(Provider.class).isEqualTo(type.getRawType());
        assertThat(Component.class).isEqualTo(type.getActualTypeArguments()[0]);*/

        Provider<Component> provider = context.get(new ComponentRef<Provider<Component>>(){}).orElseThrow();
        assertSame(instance, provider.get());
    }

    @Test
    @DisplayName("获取不到Provider")
    void should_not_retrieve_bind_type_as_unsupported_container() {
        Component instance = new Component() { };
        contextConfig.bind(Component.class, instance);
        Context context = contextConfig.getContext();
        assertFalse(context.get(new ComponentRef<List<Component>>() {}).isPresent());
    }

    @Nested
    class WithQualifier {

        @Test
        @DisplayName("实例绑定qualifier")
        void should_bind_instance_with_qualifier() {
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            // 这样无法支持
            // contextConfig.bind(Component.class, instance, @Named("myComponent"));
            contextConfig.bind(Component.class, instance, new NamedLiteral("myComponent"));
            Context context = contextConfig.getContext();

            ComponentRef<Component> myComponentType = ComponentRef.of(Component.class, new NamedLiteral("myComponent"));
            Component myComponent = context.get(myComponentType)
                    .orElseThrow();
            assertThat(instance).isSameAs(myComponent);
        }

        @Test
        @DisplayName("实例绑定多个qualifier")
        void should_bind_instance_with_multiple_qualifier() {
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            // 这样无法支持
            // contextConfig.bind(Component.class, instance, @Named("myComponent"));
            contextConfig.bind(Component.class, instance, new NamedLiteral("myComponent"), new NamedLiteral("myComponent1"));
            Context context = contextConfig.getContext();

            ComponentRef<Component> myComponentType = ComponentRef.of(Component.class, new NamedLiteral("myComponent"));
            Component myComponent = context.get(myComponentType).orElseThrow();

            ComponentRef<Component> myComponentType1 = ComponentRef.of(Component.class, new NamedLiteral("myComponent1"));
            Component myComponent1 = context.get(myComponentType1).orElseThrow();

            assertThat(instance).isSameAs(myComponent);
            assertThat(instance).isSameAs(myComponent1);
            assertThat(myComponent).isSameAs(myComponent);
        }

        @Test
        @DisplayName("类实例绑定多个qualifier")
        void should_bind_dependency_with_multiple_qualifier() {
            contextConfig.bind(Dependency.class, DependencyA.class, new NamedLiteral("d1"), new NamedLiteral("d2"));
            Context context = contextConfig.getContext();

            ComponentRef<Dependency> d1 = ComponentRef.of(Dependency.class, new NamedLiteral("d1"));
            Dependency dependency1 = context.get(d1).orElseThrow();

            ComponentRef<Dependency> d2 = ComponentRef.of(Dependency.class, new NamedLiteral("d2"));
            Dependency dependency2 = context.get(d2).orElseThrow();

            assertThat(dependency1).isNotNull();
            assertThat(dependency2).isNotNull();
        }

        @Test
        @DisplayName("如果qualifier声明的实例不存在，应该抛出异常")
        void should_throw_exception_if_illegal_qualifier_given_to_instance() {
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            assertThrows(IllegalComponentException.class, () -> contextConfig.bind(Component.class, instance, new TestLiteral()));
        }

        @Test
        @DisplayName("如果qualifier声明的实例不存在，应该抛出异常")
        void should_throw_exception_if_illegal_qualifier_given_to_component_instance() {
            assertThrows(IllegalComponentException.class, () -> contextConfig.bind(Component.class, ComponentDirectlyInstance.class, new TestLiteral()));
        }

        // todo provider

        ///////////////////////////////////////////////////////////////////////////
        // check
        ///////////////////////////////////////////////////////////////////////////

        @Test
        @DisplayName("qualifier 声明的实例找不到")
        void should_throw_exception_if_dependency_with_qualifier_not_found() {
            contextConfig.bind(Dependency.class, new Dependency() { });
            contextConfig.bind(QualifierInjectConstructor.class, QualifierInjectConstructor.class);
            assertThrows(DependencyNotBindException.class, () -> contextConfig.getContext());
        }

    }
}

record TestLiteral() implements Test {

    @Override
    public Class<? extends Annotation> annotationType() {
        return Test.class;
    }
}
