package com.golthiware.g13.controller.gui;

import com.golthiware.g13.controller.G13Finder;
import com.golthiware.g13.controller.G13ToBeanAdaptor;
import com.golthiware.g13.controller.HardwareG13;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 */
public class GuiMain extends Application {

    public static void main(String[] args) {
        Application.launch(GuiMain.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        G13Finder finder = new G13Finder();
        List<HardwareG13.Builder> g13Builders = finder.getG13Builders();
        if (g13Builders.isEmpty()) {
            throw new RuntimeException("No G13 found");
        }
        if (g13Builders.size() > 1) {
            throw new RuntimeException("There are at least two G13 devices "
                    + "and only one can be managed. Devices are: " + g13Builders);
        }
        G13ToBeanAdaptor adaptor = new G13ToBeanAdaptor(g13Builders.get(0).build());
        G13BeamView keyboardController = new G13BeamView(adaptor.getBean());

        Thread readerThread = new Thread(
                () -> {
                    while (!Thread.interrupted()) {
                        try {
                            adaptor.refresh();
                        } catch (InterruptedException ex) {
                            break;
                        }
                    }
                },
                "g13-read-thread"
        );
        readerThread.start();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/keyboard.fxml"));
        fxmlLoader.setController(keyboardController);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("G13 Driver");
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            readerThread.interrupt();
            try {
                readerThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GuiMain.class.getName()).log(Level.SEVERE, "It was impossible to close the read thread", ex);
            }
            finder.close();
        });
    }

}
