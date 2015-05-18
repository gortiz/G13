
package com.golthiware.g13.controller.gui;

import com.golthiware.g13.controller.G13Key;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.annotation.Nullable;

/**
 *
 */
public abstract class G13ViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView keyboard;

    @FXML
    private ImageView bdKey;

    @FXML
    private ImageView lightKey;

    @FXML
    private ImageView g1Key;

    @FXML
    private ImageView g2Key;

    @FXML
    private ImageView g3Key;

    @FXML
    private ImageView g4Key;

    @FXML
    private ImageView g5Key;

    @FXML
    private ImageView g6Key;

    @FXML
    private ImageView g7Key;

    @FXML
    private ImageView g8Key;

    @FXML
    private ImageView g9Key;

    @FXML
    private ImageView g10Key;

    @FXML
    private ImageView g11Key;

    @FXML
    private ImageView g12Key;

    @FXML
    private ImageView g13Key;

    @FXML
    private ImageView g14Key;

    @FXML
    private ImageView g15Key;

    @FXML
    private ImageView g16Key;

    @FXML
    private ImageView g17Key;

    @FXML
    private ImageView g18Key;

    @FXML
    private ImageView g19Key;

    @FXML
    private ImageView g20Key;

    @FXML
    private ImageView g21Key;

    @FXML
    private ImageView g22Key;

    @FXML
    private ImageView m1Key;

    @FXML
    private ImageView m2Key;

    @FXML
    private ImageView m3Key;

    @FXML
    private ImageView mrKey;

    @FXML
    private ImageView downKey;

    @FXML
    private ImageView leftKey;

    @FXML
    private ImageView l1Key;

    @FXML
    private ImageView l2Key;

    @FXML
    private ImageView l3Key;

    @FXML
    private ImageView l4Key;

    @FXML
    void initialize() {
        assert keyboard != null : "fx:id=\"keyboard\" was not injected: check your FXML file 'interface.fxml'.";
        assert bdKey != null : "fx:id=\"bdKey\" was not injected: check your FXML file 'interface.fxml'.";
        assert lightKey != null : "fx:id=\"lightKey\" was not injected: check your FXML file 'interface.fxml'.";
        assert g1Key != null : "fx:id=\"g1Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g2Key != null : "fx:id=\"g2Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g3Key != null : "fx:id=\"g3Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g4Key != null : "fx:id=\"g4Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g5Key != null : "fx:id=\"g5Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g6Key != null : "fx:id=\"g6Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g7Key != null : "fx:id=\"g7Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g8Key != null : "fx:id=\"g8Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g9Key != null : "fx:id=\"g9Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g10Key != null : "fx:id=\"g10Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g11Key != null : "fx:id=\"g11Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g12Key != null : "fx:id=\"g12Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g13Key != null : "fx:id=\"g13Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g14Key != null : "fx:id=\"g14Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g15Key != null : "fx:id=\"g15Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g16Key != null : "fx:id=\"g16Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g17Key != null : "fx:id=\"g17Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g18Key != null : "fx:id=\"g18Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g19Key != null : "fx:id=\"g19Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g20Key != null : "fx:id=\"g20Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g21Key != null : "fx:id=\"g21Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert g22Key != null : "fx:id=\"g22Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert m1Key != null : "fx:id=\"m1Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert m2Key != null : "fx:id=\"m2Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert m3Key != null : "fx:id=\"m3Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert mrKey != null : "fx:id=\"mrKey\" was not injected: check your FXML file 'interface.fxml'.";
        assert downKey != null : "fx:id=\"leftKey\" was not injected: check your FXML file 'interface.fxml'.";
        assert leftKey != null : "fx:id=\"rightKey\" was not injected: check your FXML file 'interface.fxml'.";
        assert l1Key != null : "fx:id=\"l1Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert l2Key != null : "fx:id=\"l2Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert l3Key != null : "fx:id=\"l3Key\" was not injected: check your FXML file 'interface.fxml'.";
        assert l4Key != null : "fx:id=\"l4Key\" was not injected: check your FXML file 'interface.fxml'.";
    }
    
    ImageView getImageView(G13Key key) {
        switch (key) {
            case G13_KEY_G1:
                return g1Key;
            case G13_KEY_G2:
                return g2Key;
            case G13_KEY_G3:
                return g3Key;
            case G13_KEY_G4:
                return g4Key;
            case G13_KEY_G5:
                return g5Key;
            case G13_KEY_G6:
                return g6Key;
            case G13_KEY_G7:
                return g7Key;
            case G13_KEY_G8:
                return g8Key;
            case G13_KEY_G9:
                return g9Key;
            case G13_KEY_G10:
                return g10Key;
            case G13_KEY_G11:
                return g11Key;
            case G13_KEY_G12:
                return g12Key;
            case G13_KEY_G13:
                return g13Key;
            case G13_KEY_G14:
                return g14Key;
            case G13_KEY_G15:
                return g15Key;
            case G13_KEY_G16:
                return g16Key;
            case G13_KEY_G17:
                return g17Key;
            case G13_KEY_G18:
                return g18Key;
            case G13_KEY_G19:
                return g19Key;
            case G13_KEY_G20:
                return g20Key;
            case G13_KEY_G21:
                return g21Key;
            case G13_KEY_G22:
                return g22Key;
            case G13_KEY_L1:
                return l1Key;
            case G13_KEY_L2:
                return l2Key;
            case G13_KEY_L3:
                return l3Key;
            case G13_KEY_L4:
                return l4Key;
            case G13_KEY_DOWN:
                return downKey;
            case G13_KEY_LEFT:
                return leftKey;
            case G13_KEY_BD:
                return bdKey;
            case G13_KEY_LIGHT:
                return lightKey;
            case G13_KEY_M1:
                return m1Key;
            case G13_KEY_M2:
                return m2Key;
            case G13_KEY_M3:
                return m3Key;
            case G13_KEY_MR:
                return mrKey;
            default:
                return null;
        }
    }
    
    void setPressed(G13Key key, boolean state) {
        ImageView imageView = getImageView(key);
        if (imageView == null) {
            return ;
        }
        if (state) {
            imageView.setEffect(getActivatedEffect());
        }
        else {
            imageView.setEffect(getDisableEffect());
        }
    }

    @FXML
    abstract void onMouseClicked(MouseEvent event);
    
    @FXML
    abstract void onMouseEntered(MouseEvent event);

    @FXML
    abstract void onMouseExited(MouseEvent event);
    
    @Nullable
    abstract Effect getActivatedEffect();
    
    @Nullable
    abstract Effect getDisableEffect();
}
