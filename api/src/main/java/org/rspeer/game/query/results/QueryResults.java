package org.rspeer.game.query.results;

import org.rspeer.commons.Random;
import org.rspeer.commons.typing.SelfTyped;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class QueryResults<T, QR extends SelfTyped<QR>> implements Collection<T>, SelfTyped<QR> {

    protected final List<T> results;

    public QueryResults(Collection<T> results) {
        if (results instanceof List) {
            this.results = (List<T>) results;
        } else {
            this.results = new ArrayList<>(results);
        }
    }

    public final QR sort(Comparator<? super T> comparator) {
        results.sort(comparator);
        return self();
    }

    public boolean retainAll(Collection<?> c) {
        return results.retainAll(c);
    }

    public List<T> asList() {
        return results;
    }

    public T get(int index) {
        return index >= 0 && index < results.size() ? results.get(index) : null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int lastIndexOf(T o) {
        return results.lastIndexOf(o);
    }

    public boolean addAll(Collection<? extends T> c) {
        return results.addAll(c);
    }

    public final QR limit(int startIndex, int amount) {
        List<T> limit = new ArrayList<>(amount);

        for (int i = startIndex; i < size() && i - startIndex < amount; i++) {
            limit.add(get(i));
        }

        results.retainAll(limit);
        return self();
    }

    public final T first() {
        return size() == 0 ? null : get(0);
    }

    public void clear() {
        results.clear();
    }

    public int size() {
        return results.size();
    }

    public T[] toArray(Object[] dest) {
        return (T[]) results.toArray(dest);
    }

    @Deprecated
    public Object[] toArray() {
        return results.toArray();
    }

    public String toString() {
        return getClass().getSimpleName() + results;
    }

    public boolean removeAll(Collection<?> c) {
        return results.removeAll(c);
    }

    public boolean remove(Object o) {
        return results.remove(o);
    }

    public boolean add(T t) {
        return results.add(t);
    }

    public final QR reverse() {
        Collections.reverse(results);
        return self();
    }

    public final T last() {
        int index = size();
        return index != 0 ? get(index - 1) : null;
    }

    public int indexOf(T o) {
        return results.indexOf(o);
    }

    public final boolean accept(Consumer<T> consumer, Function<QR, T> target) {
        T value = target.apply(self());
        if (value != null) {
            consumer.accept(value);
            return true;
        }
        return false;
    }

    public final T random() {
        int index = size();
        return index != 0 ? get(Random.nextInt(index)) : null;
    }

    public boolean contains(Object o) {
        return results.contains(o);
    }

    public final QR shuffle() {
        Collections.shuffle(results);
        return self();
    }

    public boolean containsAll(Collection<?> c) {
        return results.containsAll(c);
    }

    public Iterator<T> iterator() {
        return results.iterator();
    }

    public final QR limit(int entries) {
        return limit(0, entries);
    }
}
