package com.lzb.container;

import java.util.Optional;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentInstaceWithInject;
import com.lzb.container.constructor.ComponentInstanceWithDefaultContructor;
import com.lzb.container.constructor.ComponentInstanceWithMultiInject;
import com.lzb.container.constructor.Dependency;
import com.lzb.container.constructor.DependencyA;
import com.lzb.container.constructor.DependencyB;
import com.lzb.container.constructor.DependencyC;
import com.lzb.container.constructor.DependencyD;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.field.ComponentWithFieldInjection;
import com.lzb.container.field.FinalFieldInjection;
import com.lzb.container.field.SubComponentWithFieldInjection;
import com.lzb.container.method.MethodInjectComponent;
import com.lzb.container.method.NonParameterAndInjectMethodComponent;
import com.lzb.container.method.NonParameterMethodComponent;
import com.lzb.container.method.SubNonParameterMethodComponent;
import com.lzb.container.method.TypeParameterMethodInjectComponent;
import com.lzb.container.provider.ConstructorInjectProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * <br/>
 * Created on : 2023-10-07 21:40
 * @author lizebin
 */
@Nested
@DisplayName("注入测试")
public class InjectUnitTest extends BaseUnitTest {

    ContextConfig contextConfig;

    @BeforeEach
    public void beforeEach() {
        contextConfig = new ContextConfig();
    }


    @Nested
    @DisplayName("通过构造函数注入")
    class InjectConstruction {

        @Test
        @DisplayName("通过默认函数绑定方式")
        void should_bind_with_default_constructor() {

            // 获取ComponentB需要的实例
            //Class[] klass = ComponentB.class.getConstructors()[0].getParameterTypes();
            contextConfig.bind(Component.class, ComponentInstanceWithDefaultContructor.class);

            // 从context里面获取实例
            Context context = contextConfig.getContext();
            var instance = context.get(Component.class)
                    .orElseThrow();

            // 如果都有的话，那就直接返回ComponentB实例，如果没有会出现递归构造，先不考虑（步子迈太大）
            assertThat(instance).isNotNull();
            assertThat(instance).isInstanceOf(ComponentInstanceWithDefaultContructor.class);
        }

        @Test
        @DisplayName("通过构造函数注入的绑定方式")
        void should_bind_with_inject_constructor() {
            Dependency dependency = new Dependency() { };
            contextConfig.bind(Dependency.class, dependency);
            contextConfig.bind(Component.class, ComponentInstaceWithInject.class);

            Context context = contextConfig.getContext();
            Component component = context.get(Component.class)
                    .orElseThrow();

            assertThat(component).isNotNull();
            assertThat(component).isInstanceOf(ComponentInstaceWithInject.class);
            assertThat(((ComponentInstaceWithInject) component).getDependency()).isSameAs(dependency);

        }

        @Test
        @DisplayName("多重传递依赖：A->B->C，没有循环依赖")
        void should_ABC_dependency() {

            contextConfig.bind(DependencyA.class, DependencyA.class);
            contextConfig.bind(DependencyB.class, DependencyB.class);
            contextConfig.bind(DependencyC.class, DependencyC.class);
            String hello = "hello";
            contextConfig.bind(String.class, hello);

            Context context = contextConfig.getContext();
            DependencyA dependencyA = context.get(DependencyA.class)
                    .orElseThrow();
            assertThat(dependencyA).isNotNull();

            DependencyB dependencyB = context.get(DependencyB.class)
                    .orElseThrow();
            assertThat(dependencyB).isNotNull();

            DependencyC dependencyC = context.get(DependencyC.class)
                    .orElseThrow();
            assertThat(dependencyC).isNotNull();

            // assertThat(dependencyC.dependencyB).isSameAs(dependencyB);
            // assertThat(dependencyB.dependencyA).isSameAs(dependencyA);

            assertThat(hello).isEqualTo(dependencyC.s);
        }

        @Test
        @DisplayName("忽略bind顺序：A->B，先bind B，再bind A")
        void should_bind_orderly() {
            contextConfig.bind(DependencyD.class, DependencyD.class);
            String hello = "hello";
            contextConfig.bind(String.class, hello);

            Context context = contextConfig.getContext();
            DependencyD dependencyD = context.get(DependencyD.class)
                    .orElseThrow();
            assertThat(dependencyD.getName()).isEqualTo(hello);
        }

        @Test
        @DisplayName("依赖不存在，抛出异常，判断异常的成员属性是否正确")
        void should_throw_dependency_not_found_exception() {
            contextConfig.bind(DependencyA.class, DependencyA.class);
            contextConfig.bind(DependencyB.class, DependencyB.class);
            contextConfig.bind(DependencyC.class, DependencyC.class);

            var e = assertThrows(DependencyNotBindException.class, () -> contextConfig.getContext());
            assertThat(e.getDependencyType()).isEqualTo(String.class);
            assertThat(e.getComponentType()).isEqualTo(DependencyC.class);
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
                new ConstructorInjectProvider<>(FinalFieldInjection.class);
            });
        }

    }

    @Nested
    @DisplayName("属性注入")
    class FieldInjection {

        @Test
        @DisplayName("属性注入（第一种写法）")
        void should_inject_dependency_via_field() {
            Dependency dependency = new Dependency() { };
            contextConfig.bind(Dependency.class, dependency);
            contextConfig.bind(ComponentWithFieldInjection.class, ComponentWithFieldInjection.class);
            Context context = contextConfig.getContext();

            ComponentWithFieldInjection component = context.get(ComponentWithFieldInjection.class)
                    .orElseThrow();
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
        }

        @Test
        @DisplayName("属性注入（第二种写法，更接近于我们平时说的单元测试），但是这个写法会有一个问题就是你需要知道里面的实现细节，因为在contextConfig.getContext()的时候，通过各种检查，但是这个测试无法模拟")
        void should_inject_dependency_vis_field_1() {
            Context context = Mockito.mock(Context.class);
            Dependency dependency = Mockito.mock(Dependency.class);
            when(context.get(Dependency.class)).thenReturn(Optional.of(dependency));

            var provider = new ConstructorInjectProvider<ComponentWithFieldInjection>(ComponentWithFieldInjection.class);
            ComponentWithFieldInjection component = provider.get(context);
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
        }

        @Test
        @DisplayName("父类继承属性")
        void should_inject_dependency_via_superclass_inject_field() {
            Dependency dependency = new Dependency() { };
            contextConfig.bind(Dependency.class, dependency);
            contextConfig.bind(SubComponentWithFieldInjection.class, SubComponentWithFieldInjection.class);
            Context context = contextConfig.getContext();

            SubComponentWithFieldInjection component = context.get(SubComponentWithFieldInjection.class)
                    .orElseThrow();
            assertThat(component.getDependency()).isNotNull();
            assertThat(component.getDependency()).isSameAs(dependency);
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

            contextConfig.bind(Dependency.class, DependencyA.class);
            contextConfig.bind(MethodInjectComponent.class, MethodInjectComponent.class);

            Context context = contextConfig.getContext();
            MethodInjectComponent methodInjection = context.get(MethodInjectComponent.class)
                    .orElseThrow();

            assertThat(methodInjection.getDependency()).isNotNull();

        }

        @Test
        @DisplayName("inject方法没有任何参数")
        void should_inject_non_parameter_method() {
            contextConfig.bind(NonParameterMethodComponent.class, NonParameterMethodComponent.class);
            Context context = contextConfig.getContext();
            NonParameterMethodComponent component = context.get(NonParameterMethodComponent.class)
                    .orElseThrow();
            assertThat(component.called).isTrue();
        }

        @Test
        @DisplayName("@Inject存在于继承关系")
        void should_inject_in_extends() {
            contextConfig.bind(SubNonParameterMethodComponent.class, SubNonParameterMethodComponent.class);
            Context context = contextConfig.getContext();
            SubNonParameterMethodComponent component = context.get(SubNonParameterMethodComponent.class)
                    .orElseThrow();
            assertThat(component.called).isTrue();
            assertThat(component.subCalled).isTrue();
        }

        @Test
        @DisplayName("子类重写父类inject方法，但是子类没有inject注解，不会注入")
        void should_sub_class_without_inject() {
            contextConfig.bind(NonParameterAndInjectMethodComponent.class, NonParameterAndInjectMethodComponent.class);
            Context context = contextConfig.getContext();
            NonParameterAndInjectMethodComponent nonParameterAndInjectMethodComponent = context.get(NonParameterAndInjectMethodComponent.class)
                    .orElseThrow();
            assertThat(nonParameterAndInjectMethodComponent.called).isFalse();
        }

        @Test
        @DisplayName("注入方法不能有泛型")
        void should_throw_exception_when_inject_method_has_type_parameter() {
            assertThrows(IllegalComponentException.class, () -> {
                new ConstructorInjectProvider<>(TypeParameterMethodInjectComponent.class);
            });
        }

    }

}
