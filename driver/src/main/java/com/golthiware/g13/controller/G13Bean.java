
package com.golthiware.g13.controller;

import java.util.EnumMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 */
public class G13Bean {

    private final Map<G13Key, BooleanProperty> properties;

    public G13Bean() {
        properties = new EnumMap<>(G13Key.class);
        for (G13Key value : G13Key.values()) {
            properties.put(value, new SimpleBooleanProperty(false));
        }
    }
    
    public BooleanProperty getKeyProperty(G13Key key) {
        BooleanProperty result = properties.get(key);
        if (result == null) {
            throw new IllegalArgumentException("There is no key to map " + key);
        }
        return result;
    }
    
    public void disableAll() {
        properties.values().stream().forEach((value) -> {
            value.set(false);
        });
    }
    
    public void enableAll() {
        properties.values().stream().forEach((value) -> {
            value.set(true);
        });
    }
    
    public void setState(G13State state) {
        disableAll();
        state.getPressedKeys().stream().forEach((pressedKey) -> {
            properties.get(pressedKey).set(true);
        });
    }

    public void apply(G13Diff diff) {
        for (G13Key pressedKey : diff.getPressedKeys()) {
            properties.get(pressedKey).set(true);
        }
        for (G13Key releasedKey : diff.getReleasedKeys()) {
            properties.get(releasedKey).set(false);
        }
    }
}
