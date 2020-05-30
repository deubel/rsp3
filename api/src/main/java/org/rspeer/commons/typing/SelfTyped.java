package org.rspeer.commons.typing;

public interface SelfTyped<SELF extends SelfTyped<SELF>> {

    SELF self();

}
