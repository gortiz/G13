
package com.golthiware.g13.controller;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class G13State {

    public static final G13State REPOSE_STATE = new G13State(ImmutableSet.of(), 0, 0);

    private final ImmutableSet<G13Key> pressedKeys;
    private final int stickX;
    private final int stickY;

    public G13State(Set<G13Key> pressedKeys, int stickX, int stickY) {
        this.pressedKeys = Sets.immutableEnumSet(pressedKeys);
        this.stickX = stickX;
        this.stickY = stickY;
    }

    public ImmutableSet<G13Key> getPressedKeys() {
        return pressedKeys;
    }

    public int getStickX() {
        return stickX;
    }

    public int getStickY() {
        return stickY;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.pressedKeys);
        hash = 67 * hash + this.stickX;
        hash = 67 * hash + this.stickY;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final G13State other = (G13State) obj;
        if (!Objects.equals(this.pressedKeys, other.pressedKeys)) {
            return false;
        }
        if (this.stickX != other.stickX) {
            return false;
        }
        if (this.stickY != other.stickY) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G13State{\n\t" + "pressedKeys=" + pressedKeys + ",\n\t stickX=" + stickX + ",\n\t stickY=" + stickY + '}';
    }
    
}
