package com.alchemy.woodsman.core.utilities;

import com.alchemy.woodsman.core.graphics.data.Registerable;

public class LocalName extends Registerable {
    private String localName;

    public LocalName(String id, String localName) {
        super(id);

        this.localName = localName;
    }

    public final String getLocalName() {
        return this.localName;
    }
}
