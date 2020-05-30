package jag;

public interface RSLinkedList<T extends RSNode> extends RSProvider {

    RSNode getSentinel();

    RSNode getTail();

    default T head() {
        RSNode node = getSentinel();
        if (node == null) {
            return null;
        }

        RSNode head = node.getNext();
        if (node == head) {
            return null;
        }
        return (T) head;
    }
}