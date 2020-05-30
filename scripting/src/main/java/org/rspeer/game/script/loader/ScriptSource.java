package org.rspeer.game.script.loader;

import org.rspeer.game.script.Script;
import org.rspeer.game.script.ScriptMeta;

public class ScriptSource implements Comparable<ScriptSource> {

    private final String name, description, developer;
    private final double version;
    private Class<? extends Script> target;
    private boolean local = true;

    public ScriptSource(Class<? extends Script> target) {
        this.target = target;
        ScriptMeta meta = target.getAnnotation(ScriptMeta.class);
        name = meta.name();
        description = meta.desc();
        developer = meta.developer();
        version = meta.version();
    }

    public ScriptSource(String name, double version, String description, String developer) {
        target = null;
        this.name = name;
        this.version = version;
        this.description = description;
        this.developer = developer;
    }

    public final Class<? extends Script> getTarget() {
        return target;
    }

    public final void setTarget(Class<? extends Script> target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeveloper() {
        return developer;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScriptSource) || this.getClass() != o.getClass()) {
            return false;
        }
        ScriptSource other = (ScriptSource) o;
        return other.getTarget() == getTarget()
                && other.getDescription().equals(getDescription())
                && other.getDeveloper().equals(getDeveloper())
                && other.getName().equals(getName())
                && other.local == local;
    }

    @Override
    public int compareTo(ScriptSource o) {
        return getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }

    public double getVersion() {
        return version;
    }
}
