package com.lzb.container;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentInstaceWithInject;
import com.lzb.container.constructor.ComponentInstanceWithDefaultContructor;
import com.lzb.container.constructor.ComponentInstanceWithMultiInject;
import com.lzb.container.constructor.Dependency;
import com.lzb.container.constructor.ProviderInjectConstructor;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.field.ComponentWithFieldInjection;
import com.lzb.container.field.FinalFieldInjection;
import com.lzb.container.field.ProviderInjectField;
import com.lzb.container.field.SubComponentWithFieldInjection;
import com.lzb.container.method.MethodInjectComponent;
import com.lzb.container.method.NonParameterAndInjectMethodComponent;
import com.lzb.container.method.NonParameterMethodComponent;
import com.lzb.container.method.ProviderInjectMethod;
import com.lzb.container.method.SubNonParameterMethodComponent;
import com.lzb.container.method.TypeParameterMethodInjectComponent;
import com.lzb.container.provider.InjectProvider;
import jakarta.inject.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * <br/>
 * Created on : 2023-10-07 21:40
 * @author lizebin
 */
@Nested
@DisplayName("注入测试")
public class InjectUnitTest extends BaseUnitTest {

    ContextConfig contextConfig;

    @Mock
    private Context context;

    Dependency dependency = new Dependency() { };

    @Mock
    Provider<Dependency> dependencyProvider;


    private ParameterizedType dependencyProviderType;

    @BeforeEach
    public void beforeEach() throws NoSuchFieldException {
        dependencyProviderType = (ParameterizedType) InjectUnitTest.class.getDeclaredField("dependencyProvider").getGenericType();
        contextConfig = new ContextConfig();
        lenient().when(context.get(eq(Context.Ref.of(Dependency.class)))).thenReturn(Optional.of(dependency));
        lenient().when(context.get(eq(Context.Ref.of(dependencyProviderType)))).thenReturn(Optional.of(dependencyProvider));
    }


    @Nested
    @DisplayName("通过构造函数注入")
    class InjectConstruction {

        @Test
        @DisplayName("通过默认函数绑定方式")
        void should_bind_with_default_constructor() {

            // 获取ComponentB需要的实例
            //Class[] klass = ComponentB.class.getConstructors()[0].getParameterTypes();
            Class<ComponentInstanceWithDefaultContructor> implementation = ComponentInstanceWithDefaultContructor.class;
            var instance = getComponent(Component.class, implementation);

            // 如果都有的话，那就直接返回ComponentB实例，如果没有会出现递归构造，先不考虑（步子迈太大）
            assertThat(instance).isNotNull();
            assertThat(instance).isInstanceOf(implementation);
        }

        @Test
        @DisplayName("通过构造函数注入的绑定方式")
        void should_bind_with_inject_constructor() {

            ComponentInstaceWithInject component = (ComponentInstaceWithInject) getComponent(Component.class, ComponentInstaceWithInject.class);

            assertThat(component).isNotNull();
            assertThat(component).isInstanceOf(ComponentInstaceWithInject.class);
            assertThat(((ComponentInstaceWithInject) component).getDependency()).isSameAs(dependency);

        }

        ///////////////////////////////////////////////////////////////////////////
        // 以下是异常的情况
        ///////////////////////////////////////////////////////////////////////////

        @Test
        @DisplayName("多个inject构造函数，抛出异常，尽量把异常前置，在bind的时候检测")
        void should_throw_exception_if_multi_inject_constructors() {
            assertThrows(IllegalArgumentException.class, () -> {
                contextConfig.bind(Component.class, ComponentInstanceWithMultiInject.class);
            });
        }

        @Test
        @DisplayName("绑定final字段抛异常")
        void should_throw_exception_if_field_is_final() {
            assertThrows(IllegalComponentException.class, () -> {
                new InjectProvider<>(FinalFieldInjection.class);
            });
        }

        @Test
        @DisplayName("通过构造函数注入Provider")
        void should_inject_provider_via_inject_constructor() {
            ProviderInjectConstructor instance = new InjectProvider<ProviderInjectConstructor>(ProviderInjectConstructor.class).get(context);
            assertSame(dependencyProvider, instance.dependency);
        }

        @Test
        @DisplayName("通过构造函数注入Provider，通过dependencyType返回对应类型")
        void should_include_provider_type_from_inject_constructor() {
            var provider = new InjectProvider<ProviderInjectConstructor>(ProviderInjectConstructor.class);
            assertArrayEquals(new Context.Ref[]{Context.Ref.of(dependencyProviderType)}, provider.getDependencyRefs().toArray(Context.Ref[]::new));
        }

    }

    private <T, I extends T> T getComponent(Class<T> component, Class<I> implementation) {
        return new InjectProvider<T>(implementation).get(context);
    }

    @Nested
    @DisplayName("属性注入")
    class FieldInjection {

        @Test
        @DisplayName("属性注入（第一种写法）")
        void should_inject_dependency_via_field() {
            ComponentWithFieldInjection component = (ComponentWithFieldInjection) getComponent(Component.class, ComponentWithFieldInjection.class);
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
        }

        @Test
        @DisplayName("属性注入（第二种写法，更接近于我们平时说的单元测试），但是这个写法会有一个问题就是你需要知道里面的实现细节，因为在contextConfig.getContext()的时候，通过各种检查，但是这个测试无法模拟")
        void should_inject_dependency_vis_field_1() {
            ComponentWithFieldInjection component = (ComponentWithFieldInjection) getComponent(Component.class, ComponentWithFieldInjection.class);
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
        }

        @Test
        @DisplayName("父类继承属性")
        void should_inject_dependency_via_superclass_inject_field() {

            SubComponentWithFieldInjection component = (SubComponentWithFieldInjection) getComponent(Component.class, SubComponentWithFieldInjection.class);
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
        }

        @Test
        @DisplayName("通过方法注入Provider")
        void should_inject_provider_via_inject_field() {
            var instance = new InjectProvider<ProviderInjectField>(ProviderInjectField.class).get(context);
            assertSame(dependencyProvider, instance.dependency);
        }

        @Test
        @DisplayName("通过Field注入Provider，通过dependencyType返回对应类型")
        void should_include_provider_type_from_inject_field() {
            var provider = new InjectProvider<ProviderInjectField>(ProviderInjectField.class);
            assertArrayEquals(new Context.Ref[]{Context.Ref.of(dependencyProviderType)}, provider.getDependencyRefs().toArray(Context.Ref[]::new));
        }



    /*@Test
    @Disabled
    @DisplayName("延伸第一种：属性依赖缺失")
    void should_throw_exception_when_field_dependency_missing() {
        contextConfig.bind(ComponentWithFieldInjection.class, ComponentWithFieldInjection.class);
        assertThrows(DependencyNotBindException.class, () -> {
            contextConfig.getContext();
        });
    }

    @Test
    @Disabled
    @DisplayName("延伸第二种：属性依赖缺失，针对于provider来处理。最后发现Provider只是提供数据，不提供功能，功能都在ContextConfig")
    void should_include_field_dependency_in_dependencies() {
        var provider = new ConstructorInjectProvider<ComponentWithFieldInjection>(ComponentWithFieldInjection.class);
        assertThat(provider.getDependencies()).contains(Dependency.class);
    }

    @Test
    @DisplayName("延伸第一种：判断循环依赖")
    void should_throw_exception_when_field_has_cyclic_dependency() {
        contextConfig.bind(ComponentWithFieldInjection.class, ComponentWithFieldInjection.class);
        assertThrows(CyclicDependencyException.class, () -> {
            contextConfig.getContext();
        });
    }*/

    }

    @Nested
    @DisplayName("方法注入")
    class MethodInjection {

        @Test
        @DisplayName("方法注入")
        void should_method_injection() {
            MethodInjectComponent methodInjection = (MethodInjectComponent) getComponent(Component.class, MethodInjectComponent.class);
            assertThat(methodInjection.getDependency()).isNotNull();
        }

        @Test
        @DisplayName("inject方法没有任何参数")
        void should_inject_non_parameter_method() {
            NonParameterMethodComponent component = (NonParameterMethodComponent) getComponent(Component.class, NonParameterMethodComponent.class);
            assertThat(component.called).isTrue();
        }

        @Test
        @DisplayName("@Inject存在于继承关系")
        void should_inject_in_extends() {
            SubNonParameterMethodComponent component = (SubNonParameterMethodComponent) getComponent(Component.class, SubNonParameterMethodComponent.class);
            assertThat(component.called).isTrue();
            assertThat(component.subCalled).isTrue();
        }

        @Test
        @DisplayName("子类重写父类inject方法，但是子类没有inject注解，不会注入")
        void should_sub_class_without_inject() {
            NonParameterAndInjectMethodComponent component = (NonParameterAndInjectMethodComponent) getComponent(Component.class, NonParameterAndInjectMethodComponent.class);
            assertThat(component.called).isFalse();
        }

        @Test
        @DisplayName("注入方法不能有泛型")
        void should_throw_exception_when_inject_method_has_type_parameter() {
            assertThrows(IllegalComponentException.class, () -> {
                new InjectProvider<>(TypeParameterMethodInjectComponent.class);
            });
        }

        @Test
        @DisplayName("通过方法注入Provider")
        void should_inject_provider_via_inject_method() {
            ProviderInjectMethod instance = new InjectProvider<ProviderInjectMethod>(ProviderInjectMethod.class).get(context);
            assertSame(dependencyProvider, instance.dependency);
        }

        @Test
        @DisplayName("通过method注入Provider，通过dependencyType返回对应类型")
        void should_include_provider_type_from_inject_method() {
            var provider = new InjectProvider<ProviderInjectMethod>(ProviderInjectMethod.class);
            assertArrayEquals(new Context.Ref[]{new Context.Ref(dependencyProviderType)}, provider.getDependencyRefs().toArray(Context.Ref[]::new));
        }

    }

}
