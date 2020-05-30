package jag;

public interface RSReferenceCache<T extends RSNode> extends RSProvider {

    RSIterableNodeTable<T> getTable();

}