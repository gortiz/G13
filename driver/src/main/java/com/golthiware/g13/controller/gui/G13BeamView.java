
package com.golthiware.g13.controller.gui;

import com.golthiware.g13.controller.G13Bean;
import com.golthiware.g13.controller.G13Key;
import javafx.beans.property.BooleanProperty;
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

    public G13BeamView(G13Bean g13Bean) {
        this.g13Bean = g13Bean;
    }

    @Override
    void initialize() {
        super.initialize();
        for (G13Key key : G13Key.values()) {
            g13Bean.getKeyProperty(key).addListener(getListener(key));
        }
    }
    
    private ChangeListener<Boolean> getListener(G13Key key) {
        return (observable, oldValue, newValue) -> {
            setPressed(key, newValue);
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

}
