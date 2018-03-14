package utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

public class CircleList<T> implements Iterable<T> {

    private final List<T> list;

    public CircleList(final List<T> list) {
        this.list = list;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Iterator<T> circleIterator() {
        return new Iterator<>() {
            private Iterator<T> iterator = list.iterator();

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                final T result = iterator.next();
                if (!iterator.hasNext()) {
                    iterator = list.iterator();
                }
                return result;
            }
        };
    }

}
