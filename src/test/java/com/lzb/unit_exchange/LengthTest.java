package com.lzb.unit_exchange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 单位换算测试<br/>
 *
 * 第一个测试是什么？<br/>
 * 有些人觉得是：1英尺可以换算成12英寸<br/>
 * 我：1英寸=1英寸<br/>
 * 老师：
 * 1.上面的测试步子太大，应该是希望创建代表"1英寸"的对象：new Inch(1)<br/>
 * 2.希望1英寸=1英寸
 * 3.assertEquals(new Inch(1), new Inch(1));//运行失败，因为没有重写equals方法。
 * 这里可能需要停下来思考怎么做，才能让运行通过，其实在编程的过程中，会冒出一些新的想法或者新的发现，
 * 但是当前处于红色的状态，千万不要做另一件事，先用便利贴把新的想法写下来，贴在电脑上，等完成当前的任务，测试回归到绿色状态时，才考虑要不要处理便利贴上的任务
 * 4.重写了equals和toString方法
 * ----------------------------------------我的实现有点问题-----------------------
 * 5.我的实现：Assertions.assertEquals(new Foot(1), new Inch(12)); 重点：为了避免不同类型的对象做equals比较，所以需要引入一个新的类型：Length
 * 老师做法：按照最简单的解决方法，对Inch类重构，引入Length类，代表长度，
 * 构造函数：new Length((int)amount, (String)unit); amount 再转成最小单位 inchAmount
 * 6.通过重构，引入单位枚举：Unit。需要把Length的构造函数重构：new Length((int)amount, (String)unit, (Unit)FIXME)，先新增再移除，所有用到的地方都会编译异常
 *
 *
 *
 *
 * Created on : 2023-06-29 23:08
 * @author mac
 */
class LengthTest {

    ///////////////////////////////////////////////////////////////////////////
    // foot -> inch
    // foot -> yard
    // inch -> foot
    // inch -> yard
    // yard -> foot
    // yard -> inch
    // foot -> foot
    // inch -> inch
    // yard -> yard
    ///////////////////////////////////////////////////////////////////////////

    @Test
    void should_foot_to_inch() {
        Length length = new Length(1, Unit.FOOT);
        Assertions.assertEquals(12, length.toInch());
    }

    @Test
    void should_foot_to_yard() {
        Length length = new Length(3, Unit.FOOT);
        Assertions.assertEquals(1, length.toYard());
    }

    @Test
    void should_foot_to_foot() {
        Length length = new Length(1, Unit.FOOT);
        Assertions.assertEquals(1, length.toFoot());
    }

    @Test
    void should_inch_to_foot() {
        Length length = new Length(12, Unit.INCH);
        Assertions.assertEquals(1, length.toFoot());
    }

    @Test
    void should_inch_to_yard() {
        Length length = new Length(36, Unit.INCH);
        Assertions.assertEquals(1, length.toYard());
    }

    @Test
    void should_inch_to_inch() {
        Length length = new Length(1, Unit.INCH);
        Assertions.assertEquals(1, length.toInch());
    }

    @Test
    void should_yard_to_inch() {
        Length length = new Length(1, Unit.YARD);
        Assertions.assertEquals(36, length.toInch());
    }

    @Test
    void should_yard_to_foot() {
        Length length = new Length(1, Unit.YARD);
        Assertions.assertEquals(3, length.toFoot());
    }

    @Test
    void should_yard_to_yard() {
        Length length = new Length(1, Unit.YARD);
        Assertions.assertEquals(1, length.toYard());
    }

}
