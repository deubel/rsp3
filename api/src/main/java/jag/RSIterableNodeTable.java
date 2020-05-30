package jag;

import java.util.ArrayList;
import java.util.List;

public interface RSIterableNodeTable<T extends RSNode> extends RSProvider {

    RSNode getHead();

    int getSize();

    RSNode getTail();

    RSNode[] getBuckets();

    int getIndex();

    default T lookup(long key) {
        try {
            RSNode node = getBuckets()[(int) (key & (long) (getSize() - 1))];
            for (RSNode head = node.getNext(); head != node; head = head.getNext()) {
                if (head.getKey() == key) {
                    return (T) head;
                }
            }
        } catch (Exception ignored) {

        }
        return   null;
    }

    default List<T> toList() {
        List<T> nodes = new ArrayList<>();
        RSNode[] buckets = getBuckets();
        for (RSNode sentinel : buckets) {
            RSNode cur = sentinel.getNext();
            while (cur != sentinel) {
                nodes.add((T) cur);
                cur = cur.getNext();
            }
        }
        return nodes;
    }
}