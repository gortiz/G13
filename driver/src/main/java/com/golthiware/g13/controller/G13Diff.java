
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
    private final ImmutableSet<G13Key> releasedKeys;
    private final int deltaX;
    private final int deltaY;

    private G13Diff(
            ImmutableSet<G13Key> pressedKeys, 
            ImmutableSet<G13Key> releasedKeys,
            int deltaX, 
            int deltaY) {
        this.pressedKeys = pressedKeys;
        this.releasedKeys = releasedKeys;
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

    public ImmutableSet<G13Key> getReleasedKeys() {
        return releasedKeys;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    @Override
    public String toString() {
        return "G13Diff{" + "pressedKeys=" + pressedKeys + ", releasedKeys=" +
                releasedKeys + ", deltaX=" + deltaX + ", deltaY=" + deltaY + '}';
    }
}
