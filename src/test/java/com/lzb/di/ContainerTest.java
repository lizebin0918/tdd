package com.lzb.di;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br/>
 * Created on : 2022-07-31 21:55
 *
 * @author lizebin
 */
public class ContainerTest {

    private Context context;

    @BeforeEach
    void setup() {
        context = new Context();
    }

    @Nested
    class ComponentConstruction {
        // todo: instance
        @Test
        void should_bind_type_to_a_specific_instance() {
            Component instance = new Component() {
            };
            context.bind(Component.class, instance);
            assertSame(instance, context.get(Component.class));
        }

        // todo: abstract class
        // todo: interface
        void should_return_empty_if_component_not_found() {
            // 如果get一个不存在的组件，返回的是null？还是抛异常？可能这个逻辑只会涉及同一个类的方法，但是对于不同上下文来说，需求不一样
            // 在这里比较好的做法是返回一个Optional
            //Optional<Component> component = context.get(Component.class);
            //assertTrue(component.isEmpty());
        }

        @Nested
        class ConstructorInjection {

            //TODO: no args constructor
            @Test
            void should_bind_type_to_a_class_with_default_constructor() {
                // 直接通过无参构造函数注入
                context.bind(Component.class, ComponentWithDefaultConstructor.class);
                Component instance = context.get(Component.class);
                assertNotNull(instance);
                assertTrue(instance instanceof ComponentWithDefaultConstructor);
            }

            //TODO: with dependencies
            @Test
            void should_bind_type_to_a_class_with_inject_constructor() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                Dependency dependency = new Dependency() {};
                context.bind(Dependency.class, dependency);

                Component instance = context.get(Component.class);
                assertNotNull(instance);
                assertSame(dependency, ((ComponentWithInjectConstructor)instance).getDependency());
            }

            //TODO: A -> B -> C
            @Test
            void should_bind_type_to_a_class_with_transitive_dependencies() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                context.bind(Dependency.class, DependencyWithInjectConstructor.class);
                String hello = "hello";
                context.bind(String.class, hello);

                Component instance = context.get(Component.class);
                assertNotNull(instance);

                Dependency dependency = ((ComponentWithInjectConstructor) instance).getDependency();
                assertNotNull(dependency);

                assertEquals(hello, ((DependencyWithInjectConstructor) dependency).getDependency());
            }

            //TODO: multi inject constructors
            @Test
            void should_throw_exception_if_multi_inject_constructors_inject() {
                assertThrows(IllegalComponentException.class, () -> context.bind(Component.class, ComponentWithMultiInjectConstructors.class));

                /*context.bind(Component.class, ComponentWithMultiInjectConstructors.class);
                assertThrows(IllegalComponentException.class, () -> {
                    context.get(Component.class);
                });*/

            }

            @Test
            void should_throw_exception_if_no_inject_nor_default_constructors_provided() {
                assertThrows(IllegalComponentException.class, () -> context.bind(Component.class, ComponentWithNoInjectConstructorsNorDefaultConstructor.class));
                /*context.bind(Component.class, ComponentWithNoInjectConstructorsNorDefaultConstructor.class);
                assertThrows(IllegalComponentException.class, () -> {
                    context.get(Component.class);
                });*/
            }

            @Test
            void should_throw_exception_if_transitive_dependency_not_found() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                context.bind(Dependency.class, DependencyWithInjectConstructor.class);

                DependencyNotFoundException dependencyNotFoundException =
                        assertThrows(DependencyNotFoundException.class, () -> context.get(Dependency.class));

                assertEquals(String.class, dependencyNotFoundException.getDependency());
            }

            @Test
            void should_throw_exception_if_dependency_not_found() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                DependencyNotFoundException dependencyNotFoundException =
                        assertThrows(DependencyNotFoundException.class, () -> context.get(Dependency.class));
                assertEquals(Dependency.class, dependencyNotFoundException.getDependency());
            }

            @Test
            @DisplayName("循环依赖抛异常")
            void should_throw_exception_if_cyclic_dependency() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                context.bind(Dependency.class, DependencyDependedOnComponent.class);

                assertThrows(CyclicDependenciesFoundException.class, () -> context.get(Component.class));
            }

            @Test
            @DisplayName("循环依赖：A->B->C->A")
            void should_throw_exception_if_transitive_cyclic() {
                context.bind(Component.class, ComponentWithInjectConstructor.class);
                context.bind(Dependency.class, DependencyDependedOnAnotherDependency.class);
                context.bind(AnotherDependency.class, AnotherDependencyDependedOnComponent.class);
                assertThrows(CyclicDependenciesFoundException.class, () -> context.get(Component.class));
            }
        }

        @Nested
        public class FieldInjection {

        }

        @Nested
        public class MethodInjection {

        }

    }

    @Nested
    public class DependenciesSelection {

    }

    @Nested
    public class LifecycleManagement {

    }

}

class ComponentWithMultiInjectConstructors implements Component {

    @Inject
    public ComponentWithMultiInjectConstructors(String name, Double value) {}

    @Inject
    public ComponentWithMultiInjectConstructors(String name) {}

}

class ComponentWithNoInjectConstructorsNorDefaultConstructor implements Component {

    public ComponentWithNoInjectConstructorsNorDefaultConstructor(String name) {}

}

interface Component {

}

class ComponentWithDefaultConstructor implements Component {
    public ComponentWithDefaultConstructor() {
    }
}

interface Dependency {

}

interface AnotherDependency {

}

class ComponentWithInjectConstructor implements Component {
    private final Dependency dependency;

    @Inject
    public ComponentWithInjectConstructor(Dependency dependency) {
        this.dependency = dependency;
    }

    public Dependency getDependency() {
        return dependency;
    }
}

class DependencyWithInjectConstructor implements Dependency {

    private final String dependency;

    @Inject
    public DependencyWithInjectConstructor(String dependency) {
        this.dependency = dependency;
    }

    public String getDependency() {
        return dependency;
    }
}

class DependencyDependedOnComponent implements Dependency {

    private final Component component;

    @Inject
    public DependencyDependedOnComponent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}

class AnotherDependencyDependedOnComponent implements AnotherDependency {
    private final Component component;

    @Inject
    public AnotherDependencyDependedOnComponent(Component component) {
        this.component = component;
    }
}

class DependencyDependedOnAnotherDependency implements Dependency {
    private final AnotherDependency anotherDependency;

    @Inject
    public DependencyDependedOnAnotherDependency(AnotherDependency anotherDependency) {
        this.anotherDependency = anotherDependency;
    }
}
