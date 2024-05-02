package com.alchemy.woodsman.core.graphics.data;

import com.alchemy.woodsman.core.utilities.Debug;

import java.lang.String;
import java.util.*;

public class Registry<T extends Registerable> {
    private HashMap<String, T> dictionary = new HashMap<String, T>();

    public final void register(T entry) {
        if (entry != null) {
            if (entry.getID() != null) {
                dictionary.put(entry.getID(), entry);

                Debug.logNormal("Registry", "Entry \"" + entry.getID() + "\" has been registered.");
                return;
            }
        }

        Debug.logError("Registry tried to register a null entry.");
    }

    public boolean hasEntry(String id) {
        if (dictionary.containsKey(id)) {
            return true;
        }

        return false;
    }

    public final T getEntry(String id) {
        if (dictionary.containsKey(id)) {
            T entry = dictionary.get(id);

            return entry;
        }

        return null;
    }

    public final ArrayList<T> getEntries() {
        Collection<T> entries = dictionary.values();

        return new ArrayList<T>(entries);
    }
}
