package com.lzb.container;

import com.lzb.BaseUnitTest;
import com.lzb.container.exception.DependencyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 *
 * 无需注入的component
 * @Inject标记构造方法，通过构造函数注入
 * @Inject标记字段，通过字段注入
 * @Inject标记方法，通过方法注入
 * 通过tag选择对应的依赖
 *
 * 父类注入
 * 对于非singleton场景，不可能存在循环引用
 *
 * Created on : 2023-09-30 22:50
 * @author lizebin
 */
public class ContextUnitTest extends BaseUnitTest {

    Context context;

    @BeforeEach
    public void beforeEach() {
        context = new Context();
    }

    @Nested
    @DisplayName("没有依赖的实例，直接注入容器")
    class InstanceWithoutInject {

        @Test
        @DisplayName("直接实例化到容器")
        void should_instance_to_context_directly() {
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            context.bind(ComponentDirectlyInstance.class, instance);
            Component component = context.getOrThrow(ComponentDirectlyInstance.class);
            Assertions.assertSame(instance, component);
        }

        @Test
        @DisplayName("获取不存在的实例，抛出异常")
        void should_throw_exception_when_instance_not_exist() {
            // assertThrows(DependencyNotFoundException.class, () -> context.getOrThrow(Component.class));
            assertThat(context.get(Component.class).isEmpty());
        }

        @Test
        @DisplayName("抽象类不能实例化到容器")
        @Disabled("没想明白测的是啥")
        void should_exception_when_class_is_abstract() {
            /*Context context = new Context();
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                context.bind(Component.class, new Object());
            });*/
        }

    }

    @Nested
    @DisplayName("通过构造函数注入")
    class InjectConstruction {

        @Test
        @DisplayName("通过默认函数绑定方式")
        void should_bind_with_default_contructor() {

            // 获取ComponentB需要的实例
            //Class[] klass = ComponentB.class.getConstructors()[0].getParameterTypes();
            context.bind(Component.class, ComponentInstanceWithDefaultContructor.class);

            // 从context里面获取实例
            var instance = context.getOrThrow(Component.class);

            // 如果都有的话，那就直接返回ComponentB实例，如果没有会出现递归构造，先不考虑（步子迈太大）
            assertThat(instance).isNotNull();
            assertThat(instance).isInstanceOf(ComponentInstanceWithDefaultContructor.class);
        }

        @Test
        @DisplayName("通过构造函数注入的绑定方式")
        void should_bind_with_inject_constructor() {
            Dependency dependency = new Dependency() { };
            context.bind(Dependency.class, dependency);
            context.bind(Component.class, ComponentInstaceWithInject.class);

            Component component = context.getOrThrow(Component.class);

            assertThat(component).isNotNull();
            assertThat(component).isInstanceOf(ComponentInstaceWithInject.class);
            assertThat(((ComponentInstaceWithInject) component).getDependency()).isSameAs(dependency);

        }

        @Test
        @DisplayName("多重传递依赖：A->B->C，没有循环依赖")
        void should_ABC_dependency() {

            context.bind(DependencyA.class, DependencyA.class);
            context.bind(DependencyB.class, DependencyB.class);
            context.bind(DependencyC.class, DependencyC.class);
            String hello = "hello";
            context.bind(String.class, hello);

            DependencyA dependencyA = context.getOrThrow(DependencyA.class);
            assertThat(dependencyA).isNotNull();

            DependencyB dependencyB = context.getOrThrow(DependencyB.class);
            assertThat(dependencyB).isNotNull();

            DependencyC dependencyC = context.getOrThrow(DependencyC.class);
            assertThat(dependencyC).isNotNull();

            assertThat(dependencyC.dependencyB).isSameAs(dependencyB);
            assertThat(dependencyB.dependencyA).isSameAs(dependencyA);

            assertThat(hello).isEqualTo(dependencyC.s);
        }

        @Test
        @DisplayName("忽略bind顺序：A->B，先bind B，再bind A")
        void should_bind_orderly() {
            context.bind(DependencyD.class, DependencyD.class);
            String hello = "hello";
            context.bind(String.class, hello);

            DependencyD dependencyD = context.getOrThrow(DependencyD.class);
            assertThat(dependencyD.getName()).isEqualTo(hello);

        }

        ///////////////////////////////////////////////////////////////////////////
        // 以下是异常的情况
        ///////////////////////////////////////////////////////////////////////////

        @Test
        @DisplayName("多个inject构造函数，抛出异常，尽量把异常前置，在bind的时候检测")
        void should_throw_exception_if_multi_inject_constructors() {
            assertThrows(IllegalArgumentException.class, () -> {
                context.bind(Component.class, ComponentInstanceWithMultiInject.class);
            });
        }

        @Test
        @DisplayName("没有inject和默认的构造函数，抛出异常")
        void should_throw_exception_if_non_inject_constructor_nor_default_constructor() {
            assertThrows(IllegalArgumentException.class, () -> {
                context.bind(Component.class, ComponentInstanceWithoutInjectAndDefaultConstructor.class);
            });
        }

        @Test
        @DisplayName("依赖不存在，抛出异常")
        void should_throw_exception_when_dependency_not_exist() {
            context.bind(Component.class, ComponentDependencyNotExist.class);

            assertThrows(DependencyNotFoundException.class, () -> {
                context.getOrThrow(Component.class);
            });
        }

    }

}
