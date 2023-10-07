package com.lzb.container;

import java.util.Optional;
import java.util.Set;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.AbstractComponent;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentDependencyNotExist;
import com.lzb.container.constructor.ComponentDirectlyInstance;
import com.lzb.container.constructor.ComponentInstaceWithInject;
import com.lzb.container.constructor.ComponentInstanceWithDefaultContructor;
import com.lzb.container.constructor.ComponentInstanceWithMultiInject;
import com.lzb.container.constructor.Dependency;
import com.lzb.container.constructor.DependencyA;
import com.lzb.container.constructor.DependencyB;
import com.lzb.container.constructor.DependencyC;
import com.lzb.container.constructor.DependencyD;
import com.lzb.container.constructor.DependencyE;
import com.lzb.container.constructor.DependencyF;
import com.lzb.container.constructor.DependencyG;
import com.lzb.container.constructor.DependencyH;
import com.lzb.container.constructor.DependencyI;
import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.exception.IllegalComponentException;
import com.lzb.container.field.ClassA;
import com.lzb.container.field.ClassB;
import com.lzb.container.field.ClassC;
import com.lzb.container.field.ComponentWithFieldInjection;
import com.lzb.container.field.FinalFieldInjection;
import com.lzb.container.field.SubComponentWithFieldInjection;
import com.lzb.container.method.MethodInjectComponent;
import com.lzb.container.method.NonParameterAndInjectMethodComponent;
import com.lzb.container.method.NonParameterMethodComponent;
import com.lzb.container.method.SubNonParameterMethodComponent;
import com.lzb.container.method.TypeParameterMethodInjectComponent;
import com.lzb.container.provider.ConstructorInjectProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
            Context context = contextConfig.getContext();
            Component component = context.get(ComponentDirectlyInstance.class)
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

}
