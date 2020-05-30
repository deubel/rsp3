package jag;

public interface RSQueue<T extends RSDoublyLinkedNode> extends RSProvider {

    RSDoublyLinkedNode getRoot();

    default int size() {
        RSDoublyLinkedNode tail = getRoot();
        int size = 0;
        for (RSDoublyLinkedNode node = tail.getNextDoubly(); node != tail; node = node.getNextDoubly()) {
            size++;
        }
        return size;
    }

    default T head() {
        RSDoublyLinkedNode tail = getRoot();
        RSDoublyLinkedNode front = tail.getNextDoubly();
        if (tail == front) {
            return null;
        }

        return (T) front;
    }
}