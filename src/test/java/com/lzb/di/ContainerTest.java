package com.lzb.di;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
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
