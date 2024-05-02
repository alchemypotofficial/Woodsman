package com.alchemy.woodsman.core.handlers;

import com.alchemy.woodsman.core.graphics.data.Registry;
import com.alchemy.woodsman.core.utilities.LocalName;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class LangHandler {

    private static Registry<LocalName> localNames = new Registry<>();

    public void loadLocalNames() {
        FileHandle englishLang = Gdx.files.internal("lang/EN_US.json");

        if (englishLang != null) {
            JsonReader json = new JsonReader();
            JsonValue jsonBase = json.parse(englishLang);
            for (JsonValue value : jsonBase) {
                String id = value.name;
                String localName = jsonBase.getString(id);

                localNames.register(new LocalName(id, localName));
            }
        }
    }

    public static String getLocalName(String staticName) {
        if (localNames.hasEntry(staticName)) {
            return localNames.getEntry(staticName).getLocalName();
        }

        return staticName;
    }
}
