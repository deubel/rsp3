package jag.game.scene.entity;

public interface RSSceneObject extends RSEntity {

    RSEntity getEntity();

    long getUid();

    default RSDynamicObject getDynamic() {
        RSEntity entity = getEntity();
        return entity instanceof RSDynamicObject ? (RSDynamicObject) entity : null;
    }

    default int getOrientation() {
        return Integer.MIN_VALUE;
    }

    default int getLinkedOrientation() {
        return Integer.MIN_VALUE;
    }

    default int getSceneX() {
        return (int) (getUid() & 0x7fL);
    }

    default int getSceneY() {
        return (int) (getUid() >>> 7 & 0x7fL);
    }

    default int getId() {
        return (int) (getUid() >>> 17 & 0xffffffffL);
    }

    default int getType() {
        return (int) (getUid() >>> 14 & 0x3L);
    }

    default int getConfig() {
        return -1; //only valid for boundarydecor and entitymarker
    }

    default RSEntity getLinkedEntity() {
        return null; //only valid for boundarydecor and boundary
    }

    @Override
    default int getHeight() {
        RSEntity entity = getEntity();
        return entity != null ? entity.getHeight() : 0;
    }
}
