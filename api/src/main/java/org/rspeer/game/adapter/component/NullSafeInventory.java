package org.rspeer.game.adapter.component;

import jag.RSNode;
import jag.game.RSInventory;

class NullSafeInventory extends Inventory {

    static final NullSafeInventory INSTANCE = new NullSafeInventory();

    public NullSafeInventory() {
        super(Provider.INSTANCE, Format.INDIVIDUAL, null);
    }

    static class Provider implements RSInventory {

        static final Provider INSTANCE = new Provider();

        @Override
        public int[] getIds() {
            return new int[0];
        }

        @Override
        public int[] getStackSizes() {
            return new int[0];
        }

        @Override
        public RSNode getNext() {
            return null;
        }

        @Override
        public RSNode getPrevious() {
            return null;
        }

        @Override
        public long getKey() {
            return 0;
        }
    }
}
