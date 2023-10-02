package com.lzb.container;

import com.lzb.BaseUnitTest;
import org.junit.jupiter.api.Assertions;
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

    @Nested
    @DisplayName("没有依赖的实例，直接注入容器")
    class InstanceWithoutInject {

        @Test
        @DisplayName("直接实例化到容器")
        void should_instance_to_context_directly() {
            Context context = new Context();
            ComponentDirectlyInstance instance = new ComponentDirectlyInstance();
            context.bind(ComponentDirectlyInstance.class, instance);
            Component component = context.get(ComponentDirectlyInstance.class);
            Assertions.assertSame(instance, component);
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
        @DisplayName("通过默认函数注入")
        void should_inject_with_default_contructor() {
            // ComponentB
            Context context = new Context();

            // 获取ComponentB需要的实例
            //Class[] klass = ComponentB.class.getConstructors()[0].getParameterTypes();
            context.bind(Component.class, ComponentInstanceWithDefaultContructor.class);

            // 从context里面获取实例
            var instance = context.get(Component.class);

            // 如果都有的话，那就直接返回ComponentB实例，如果没有会出现递归构造，先不考虑（步子迈太大）
            assertThat(instance).isNotNull();
            assertThat(instance).isInstanceOf(ComponentInstanceWithDefaultContructor.class);
        }

    }

}
