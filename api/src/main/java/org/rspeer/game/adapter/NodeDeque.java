package org.rspeer.game.adapter;

import jag.RSNode;
import jag.RSNodeDeque;

import java.util.Iterator;

public class NodeDeque<T extends RSNode> extends Adapter<RSNodeDeque<T>> implements Iterable<T> {

    private RSNode current;

    public NodeDeque(RSNodeDeque<T> raw) {
        super(raw);
        if (raw.getTail() != null) {
            current = raw.getTail().getNext();
        }
    }

    public int getSize() {
        int size = 0;

        RSNodeDeque<T> provider = getProvider();
        if (provider == null) {
            return size;
        }

        RSNode head = provider.getTail();
        RSNode node = head.getNext();
        while (node != head) {
            node = node.getNext();
            size++;
        }
        return size;
    }

    public T head() {
        RSNodeDeque<T> provider = getProvider();
        if (provider == null) {
            return null;
        }

        RSNode tail = provider.getTail();
        if (tail == null) {
            return null;
        }

        RSNode head = tail.getNext();
        if (tail == head) {
            current = null;
            return null;
        }

        current = head.getNext();
        return (T) head;
    }

    public T next() {
        RSNodeDeque<T> provider = getProvider();
        if (provider == null) {
            return null;
        }

        RSNode node = current;
        if (node == provider.getTail()) {
            current = null;
            return null;
        }
        current = node.getNext();
        return (T) node;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                RSNodeDeque<T> provider = getProvider();
                return provider != null && provider.getTail() != current;
            }

            @Override
            public T next() {
                RSNodeDeque<T> provider = getProvider();
                if (provider == null) {
                    return null;
                }

                RSNode node = current;
                if (provider.getTail() == node) {
                    node = null;
                    current = null;
                } else {
                    current = node.getNext();
                }
                return (T) node;
            }
        };
    }
}
