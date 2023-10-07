package com.lzb.container;

import java.util.Set;

import com.lzb.BaseUnitTest;
import com.lzb.container.constructor.Component;
import com.lzb.container.constructor.ComponentDependencyNotExist;
import com.lzb.container.constructor.DependencyE;
import com.lzb.container.constructor.DependencyF;
import com.lzb.container.constructor.DependencyG;
import com.lzb.container.constructor.DependencyH;
import com.lzb.container.constructor.DependencyI;
import com.lzb.container.exception.CyclicDependencyException;
import com.lzb.container.exception.DependencyNotBindException;
import com.lzb.container.field.ClassA;
import com.lzb.container.field.ClassB;
import com.lzb.container.field.ClassC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-10-07 21:44
 * @author lizebin
 */
@Nested
@DisplayName("依赖检测")
public class DependencyCheck extends BaseUnitTest {

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

}
