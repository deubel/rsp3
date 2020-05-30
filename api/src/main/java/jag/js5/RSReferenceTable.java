package jag.js5;

import jag.RSProvider;

public interface RSReferenceTable extends RSProvider {

    RSIdentityTable getEntry();

    RSIdentityTable[] getChildren();

    Object[][] getBuffer();

    byte[] unpack(int arg0, int arg1, int[] arg2);

}