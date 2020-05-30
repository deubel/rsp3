package jag;

public interface RSNodeDeque<T extends RSNode> extends RSProvider {

    RSNode getHead();

    RSNode getTail();

    T next();

    T current();

}