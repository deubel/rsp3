package jag.js5;

import jag.RSNode;

public interface RSResourceRequest extends RSNode {

    RSResourceCache getIndex();

    byte[] getBuffer();

    RSArchive getArchive();

}