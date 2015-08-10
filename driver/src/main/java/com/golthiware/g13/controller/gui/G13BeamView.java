
package com.golthiware.g13.controller.gui;

import com.golthiware.g13.controller.G13Bean;
import com.golthiware.g13.controller.G13Key;
import java.util.concurrent.ConcurrentLinkedDeque;
import javafx.beans.value.ChangeListener;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;

/**
 *
 */
public class G13BeamView extends G13ViewController {

    private final Effect activatedEffect = new ColorAdjust(0, 0, 0.41, 0);
    private final G13Bean g13Bean;
    private final ConcurrentLinkedDeque<G13KeyListener> listeners;

    public G13BeamView(G13Bean g13Bean) {
        this.g13Bean = g13Bean;
        this.listeners = new ConcurrentLinkedDeque();
        listeners.add(new BeamG13KeyListener());
    }

    public void addG13KeyListener(G13KeyListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeG13KeyListener(G13KeyListener listener) {
        listeners.remove(listener);
    }

    @Override
    void initialize() {
        super.initialize();
        for (G13Key key : G13Key.values()) {
            g13Bean.getKeyProperty(key).addListener(createListener(key));
        }
    }

    public ChangeListener<Boolean> createListener(G13Key key) {
        return (observable, oldValue, newValue) -> {
            if (oldValue.equals(newValue)) {
                return ;
            }
            for (G13KeyListener listener : listeners) {
                if (newValue) {
                    listener.onKeyPressed(key);
                }
                else {
                    listener.onKeyReleased(key);
                }
            }
        };
    }
    
    @Override
    void onMouseClicked(MouseEvent event) {
    }

    @Override
    void onMouseEntered(MouseEvent event) {
    }

    @Override
    void onMouseExited(MouseEvent event) {
    }

    @Override
    Effect getActivatedEffect() {
        return activatedEffect;
    }

    @Override
    Effect getDisableEffect() {
        return null;
    }


    private class BeamG13KeyListener implements G13KeyListener {

        @Override
        public void onKeyPressed(G13Key key) {
            setPressed(key, true);
        }

        @Override
        public void onKeyReleased(G13Key key) {
            setPressed(key, false);
        }

    }
}
