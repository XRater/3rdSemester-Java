package utils;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class provides cycle iterator for any collection.
 *
 * @param <T> type of the elements.
 */
public class CycleCollection<T> extends AbstractCollection<T> {

    private final Collection<T> collection;

    /**
     * Wraps the given collection to the CycleCollection.
     */
    public CycleCollection(final Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return collection.iterator();
    }

    /**
     * The method returns new Iterator, which provides collection elements in cycle order.
     *
     * The iterator will always have next element, if collection is not empty.
     *
     * At the beginning iterator will point at the first element of the collection. If
     * iterator points in the last element, next method will move it to the first one again.
     */
    @NotNull
    public Iterator<T> cycleIterator() {
        return new Iterator<>() {
            @NotNull
            private Iterator<T> iterator = collection.iterator();

            @Override
            public boolean hasNext() {
                return collection.size() != 0;
            }

            @Override
            public T next() {
                final T result = iterator.next();
                if (!iterator.hasNext()) {
                    iterator = collection.iterator();
                }
                return result;
            }
        };
    }

}
