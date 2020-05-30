package org.rspeer.game.adapter;

import jag.RSProvider;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class Adapter<T extends RSProvider> {

    private final Reference<T> provider;

    protected Adapter(T provider) {
        this.provider = new WeakReference<>(provider);
    }

    public T getProvider() {
        return provider.get();
    }
}
