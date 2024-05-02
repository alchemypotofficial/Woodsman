package com.alchemy.woodsman.core.graphics.data;

public class Registerable {
    private String id;

    public Registerable(String id) {
        this.id = id;
    }

    protected final void setID(String id) {
        this.id = id;
    }
    public final String getID() {
        return id;
    }
}
