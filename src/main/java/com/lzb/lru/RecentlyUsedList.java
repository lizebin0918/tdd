package com.lzb.lru;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-06-22 19:34
 * @author lizebin
 */
public class RecentlyUsedList implements Iterable<String> {

    private final LinkedList<String> list = new LinkedList<>();

    public void add(String element) {
        Objects.requireNonNull(element);
        int index;
        if (-1 < (index = list.indexOf(element))) {
            remove(index);
        }
        list.offerFirst(element);
    }

    int size() {
        return list.size();
    }

    public String get(int index) {
        String remove = remove(index);
        list.offerFirst(remove);
        return remove;
    }

    private String remove(int index) {
        String remove = list.remove(index);
        return remove;
    }

    @Override
    public Iterator<String> iterator() {
        return list.iterator();
    }
}
