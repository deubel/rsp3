package jag.opcode;

import jag.RSProvider;

public interface RSMouseRecorder extends RSProvider {

    long[] getTimeHistory();

    Object getLock();

    int getIndex();

    void setIndex(int index);

    int[] getXHistory();

    int[] getYHistory();

    boolean isEnabled();

    default void snapshot(boolean reset, int[] xs, int[] ys, long[] ts) {
        int len = ys.length;
        if (xs.length != len || ts.length != len) {
            throw new IllegalArgumentException();
        }

        if (reset || getIndex() >= 500) {
            setIndex(0);
        }

        for (int i = 0; i < len; i++) {
            snapshot(xs[i], ys[i], ts[i]);
        }
    }

    default void snapshot(int x, int y, long time) {
        int index = getIndex();
        if (index >= 500) {
            setIndex(0);
        }
        getXHistory()[index] = x;
        getYHistory()[index] = y;
        getTimeHistory()[index] = time;
        setIndex(index + 1);
    }
}