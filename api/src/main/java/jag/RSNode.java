package jag;

public interface RSNode extends RSProvider {

    RSNode getNext();

    RSNode getPrevious();

    long getKey();

}