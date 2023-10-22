package com.lzb.container;

import java.util.Set;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentDependencyNotExist;
import com.lzb.container.constructor.ComponentInstaceWithInject;
import com.lzb.container.constructor.CyclicDependencyProviderConstructor;
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
import com.lzb.container.constructor.MissingDependencyProviderConstructor;
import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.field.ClassA;
import com.lzb.container.field.ClassB;
import com.lzb.container.field.ClassC;
import com.lzb.container.field.MissingDependencyProviderField;
import com.lzb.container.method.MissingDependencyProviderMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <br/>
 * Created on : 2023-10-07 21:44
 * @author lizebin
 */
@DisplayName("依赖检测")
class DependencyCheckUnitTest extends BaseUnitTest {

    ContextConfig contextConfig;

    @BeforeEach
    public void beforeEach() {
        contextConfig = new ContextConfig();
    }

    @Test
    @DisplayName("依赖不存在，抛出异常")
    void should_throw_exception_when_dependency_not_exist() {
        contextConfig.bind(Component.class, ComponentDependencyNotExist.class);

        DependencyNotBindException e = assertThrows(DependencyNotBindException.class, () -> {
            contextConfig.getContext();
        });

        assertThat(e.getDependencyType()).isEqualTo(String.class);
        assertThat(e.getComponentType()).isEqualTo(Component.class);
    }

    @Test
    @DisplayName("循环依赖，抛出异常")
    void should_throw_exception_cyclic() {

        contextConfig.bind(DependencyE.class, DependencyE.class);
        contextConfig.bind(DependencyF.class, DependencyF.class);

        var e = assertThrows(CyclicDependencyException.class, () -> {
            contextConfig.getContext();
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
            contextConfig.getContext();
        });

    }

    @Test
    @DisplayName("属性之间出现循环依赖")
    void should_throw_exception_when_field_cyclic_dependency() {
        contextConfig.bind(ClassA.class, ClassA.class);
        contextConfig.bind(ClassB.class, ClassB.class);
        contextConfig.bind(ClassC.class, ClassC.class);
        assertThrows(CyclicDependencyException.class, () -> contextConfig.getContext());
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
        DependencyA dependencyA = (DependencyA) context.get(ComponentRef.of(DependencyA.class))
                .orElseThrow();
        assertThat(dependencyA).isNotNull();

        DependencyB dependencyB = (DependencyB) context.get(ComponentRef.of(DependencyB.class))
                .orElseThrow();
        assertThat(dependencyB).isNotNull();

        DependencyC dependencyC = (DependencyC) context.get(ComponentRef.of(DependencyC.class))
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
        DependencyD dependencyD = (DependencyD) context.get(ComponentRef.of(DependencyD.class))
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

    @Test
    @DisplayName("不存在的Provider，抛出异常")
    void should_missing_dependency_provider_with_constructor() {
        contextConfig.bind(Component.class, MissingDependencyProviderConstructor.class);

        DependencyNotBindException e = assertThrows(DependencyNotBindException.class, () -> {
            contextConfig.getContext();
        });

        // 希望测试告诉我哪个Provider没找到
        // TODO:lizebin provider inject field/method
        assertThat(e.getDependencyType()).isEqualTo(Dependency.class);
    }

    @Test
    @DisplayName("不存在的Provider，抛出异常")
    void should_missing_dependency_provider_with_field() {
        contextConfig.bind(Component.class, MissingDependencyProviderField.class);

        DependencyNotBindException e = assertThrows(DependencyNotBindException.class, () -> {
            contextConfig.getContext();
        });

        // 希望测试告诉我哪个Provider没找到
        assertThat(e.getDependencyType()).isEqualTo(Dependency.class);
    }

    @Test
    @DisplayName("不存在的Provider，抛出异常")
    void should_missing_dependency_provider_with_method() {
        contextConfig.bind(Component.class, MissingDependencyProviderMethod.class);

        DependencyNotBindException e = assertThrows(DependencyNotBindException.class, () -> {
            contextConfig.getContext();
        });

        // 希望测试告诉我哪个Provider没找到
        assertThat(e.getDependencyType()).isEqualTo(Dependency.class);
    }

    @Test
    @DisplayName("provider不存在循环依赖")
    void should_not_throw_exception_if_cyclic_dependency_via_provider() {
        contextConfig.bind(Component.class, ComponentInstaceWithInject.class);
        contextConfig.bind(Dependency.class, CyclicDependencyProviderConstructor.class);

        Context context = contextConfig.getContext();
        assertTrue(context.get(ComponentRef.of(Component.class))
                .isPresent());
    }

}
