
package com.golthiware.g13.controller;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class G13Diff {

    private final ImmutableSet<G13Key> pressedKeys;
    private final ImmutableSet<G13Key> released;
    private final int deltaX;
    private final int deltaY;

    private G13Diff(
            ImmutableSet<G13Key> pressedKeys, 
            ImmutableSet<G13Key> released, 
            int deltaX, 
            int deltaY) {
        this.pressedKeys = pressedKeys;
        this.released = released;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public static G13Diff diff(G13State from, G13State to) {
        Set<G13Key> toPressed = to.getPressedKeys();
        Set<G13Key> fromPressed = from.getPressedKeys();
        return new G13Diff(
                Sets.immutableEnumSet(Sets.difference(toPressed, fromPressed)),
                Sets.immutableEnumSet(Sets.difference(fromPressed, toPressed)),
                to.getStickX() - from.getStickX(),
                to.getStickY() - from.getStickY()
        );
    }

    public ImmutableSet<G13Key> getPressedKeys() {
        return pressedKeys;
    }

    public ImmutableSet<G13Key> getReleased() {
        return released;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
