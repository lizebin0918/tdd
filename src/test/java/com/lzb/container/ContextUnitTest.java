package com.lzb.container;

import java.util.Set;

import com.lzb.BaseUnitTest;
import com.lzb.container.exception.CyclicDependencyException;
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
            contextConfig.bind(ComponentDirectlyInstance.class, instance);
            Component component = contextConfig.get(ComponentDirectlyInstance.class).orElseThrow();
            Assertions.assertSame(instance, component);
        }

        @Test
        @DisplayName("获取不存在的实例")
        void should_throw_exception_when_instance_not_defined() {
            // assertThrows(DependencyNotFoundException.class, () -> context.getOrThrow(Component.class));
            // 这样更加友好，毕竟这是一个接口，可能还没有实现
            assertThat(contextConfig.get(Component.class).isEmpty());
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
        void should_bind_with_default_constructor() {

            // 获取ComponentB需要的实例
            //Class[] klass = ComponentB.class.getConstructors()[0].getParameterTypes();
            contextConfig.bind(Component.class, ComponentInstanceWithDefaultContructor.class);

            // 从context里面获取实例
            var instance = contextConfig.get(Component.class).orElseThrow();

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

            Component component = contextConfig.get(Component.class).orElseThrow();

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

            DependencyA dependencyA = contextConfig.get(DependencyA.class).orElseThrow();
            assertThat(dependencyA).isNotNull();

            DependencyB dependencyB = contextConfig.get(DependencyB.class).orElseThrow();
            assertThat(dependencyB).isNotNull();

            DependencyC dependencyC = contextConfig.get(DependencyC.class).orElseThrow();
            assertThat(dependencyC).isNotNull();

            assertThat(dependencyC.dependencyB).isSameAs(dependencyB);
            assertThat(dependencyB.dependencyA).isSameAs(dependencyA);

            assertThat(hello).isEqualTo(dependencyC.s);
        }

        @Test
        @DisplayName("忽略bind顺序：A->B，先bind B，再bind A")
        void should_bind_orderly() {
            contextConfig.bind(DependencyD.class, DependencyD.class);
            String hello = "hello";
            contextConfig.bind(String.class, hello);

            DependencyD dependencyD = contextConfig.get(DependencyD.class).orElseThrow();
            assertThat(dependencyD.getName()).isEqualTo(hello);
        }

        @Test
        @DisplayName("依赖不存在，抛出异常，判断异常的成员属性是否正确")
        void should_throw_dependency_not_found_exception() {
            contextConfig.bind(DependencyA.class, DependencyA.class);
            contextConfig.bind(DependencyB.class, DependencyB.class);
            contextConfig.bind(DependencyC.class, DependencyC.class);

            var e = assertThrows(DependencyNotFoundException.class, () -> contextConfig.get(DependencyC.class));
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
        @DisplayName("没有inject和默认的构造函数，抛出异常")
        void should_throw_exception_if_non_inject_constructor_nor_default_constructor() {
            assertThrows(IllegalArgumentException.class, () -> {
                contextConfig.bind(Component.class, ComponentInstanceWithoutInjectAndDefaultConstructor.class);
            });
        }

        @Test
        @DisplayName("依赖不存在，抛出异常")
        void should_throw_exception_when_dependency_not_exist() {
            contextConfig.bind(Component.class, ComponentDependencyNotExist.class);

            DependencyNotFoundException e = assertThrows(DependencyNotFoundException.class, () -> {
                contextConfig.get(Component.class);
            });

            assertThat(e.getDependencyType()).isEqualTo(String.class);
            assertThat(e.getComponentType()).isEqualTo(ComponentDependencyNotExist.class);
        }

        @Test
        @DisplayName("循环依赖，抛出异常")
        void should_throw_exception_cyclic() {

            contextConfig.bind(DependencyE.class, DependencyE.class);
            contextConfig.bind(DependencyF.class, DependencyF.class);

            var e = assertThrows(CyclicDependencyException.class, () -> {
                contextConfig.get(DependencyE.class);
            });

            assertThat(e.getComponents()).isEqualTo(Set.of(DependencyE.class, DependencyF.class));
        }

        @Test
        @DisplayName("循环依赖检测的第二种情况(G->H->I->G)，抛出异常")
        void should_throw_exception_multiple_cyclic() {

            contextConfig.bind(DependencyG.class, DependencyG.class);
            contextConfig.bind(DependencyH.class, DependencyH.class);
            contextConfig.bind(DependencyI.class, DependencyI.class);

            assertThrows(CyclicDependencyException.class, () -> {
                contextConfig.get(DependencyG.class);
            });

            assertThrows(CyclicDependencyException.class, () -> {
                contextConfig.get(DependencyH.class);
            });

            assertThrows(CyclicDependencyException.class, () -> {
                contextConfig.get(DependencyI.class);
            });

        }

    }

}
