package com.lzb.lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 实现一个lru
 *
 * Develop a recently-used-list class to hold strings uniquely in Last-In-First-Out order.
 *
 * o) A recently-used-list is initially empty.
 *
 * o) The most recently added item is first, the least recently added item is last.
 *
 * o) Items can be looked up by index, which counts from zero.
 *
 * o) Items in the list are unique, so duplicate insertions are moved rather than added.
 *
 * Optional extras
 *
 * o) Null insertions (empty strings) are not allowed.
 *
 * o) A bounded capacity can be specified at construction, so there is an upper limit to the number of items contained, with the least recently added items dropped on overflow.
 *
 * 开发一个最近使用的列表类，按照 "后进先出 "的顺序唯一地保存字符串。
 *
 * o) 一个最近使用的列表最初是空的。
 *
 * o) 最近添加的项目在前，最近添加的项目在后。
 *
 * o) 项目可以通过索引进行查询，索引从0开始计算。
 *
 * o) 列表中的项目是唯一的，所以重复的插入会被移动而不是添加。
 *
 * 可选的附加功能
 *
 * o) 不允许空的插入（空字符串）。
 *
 * o) 在构建时可以指定一个有边界的容量，因此包含的项目数量有一个上限，在溢出时，最近添加的项目会被丢弃。
 *
 * <br/>
 * Created on : 2023-06-22 15:43
 * @author lizebin
 */
class RecentlyUsedListTest {

    @Test
    void should_add_a() {
        RecentlyUsedList list = new RecentlyUsedList();
        String exceptString = "a";
        list.add(exceptString);
        Assertions.assertEquals(1, list.size());
    }

    @Test
    @DisplayName("添加多个元素，获取index=0的元素，返回最后一个元素")
    void should_add_a_b_and_get_b() {
        RecentlyUsedList list = new RecentlyUsedList();
        String stringB = "b", stringA = "a";
        list.add(stringA);
        list.add(stringB);
        Assertions.assertEquals(stringB, list.get(0));
        Assertions.assertEquals(stringA, list.get(1));
    }

    @Test
    void should_throw_exception_when_add_null() {
        RecentlyUsedList list = new RecentlyUsedList();
        Assertions.assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    @DisplayName("添加多个元素，最后一个元素重复，获取index=0的元素，返回最后一个元素")
    void should_add_a_b_c_b_and_get_0() {
        RecentlyUsedList list = new RecentlyUsedList();
        String stringB = "b", stringA = "a", stringC = "c";
        list.add(stringA);
        list.add(stringB);
        list.add(stringC);
        list.add(stringB);
        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(stringB, list.get(0));
    }

}
