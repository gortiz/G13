package com.golthiware.g13.controller.gui;

import com.golthiware.g13.controller.*;
import com.golthiware.g13.controller.Mapper.Binding;
import com.golthiware.g13.controller.Mapper.Mode;
import com.golthiware.g13.controller.actions.ChangeModeAction;
import com.golthiware.g13.controller.actions.PressKeyAction;
import com.golthiware.g13.controller.actions.ReleaseKeyAction;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Consumer;
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
        G13 g13 = g13Builders.get(0).build();

        G13Bean bean = new G13Bean();

        G13Poller poller = new G13Poller(
                g13,
                50,
                createViewRefresher(bean)
                        .andThen(
                                (diff) -> {
                                    System.out.println(diff);
                                }
                        ).andThen(
                                createMapper()
                        )
        );
        poller.start();

        G13BeamView keyboardController = new G13BeamView(bean);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/keyboard.fxml"));
        fxmlLoader.setController(keyboardController);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("G13 Driver");
        primaryStage.show();

        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            try {
                poller.stopAndWait();
            } catch (InterruptedException ex) {
                Logger.getLogger(GuiMain.class.getName()).log(Level.SEVERE, "It was impossible to close g13 poller thread", ex);
            }
            finder.close();
        });
    }

    private Consumer<G13Diff> createViewRefresher(G13Bean bean) {
        return (diff) -> {
            bean.apply(diff);
        };
    }

    private Consumer<G13Diff> createMapper() {
        try {
            Robot robot = new Robot();

            return new Mapper()
                    .setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_G10,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_P),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_P)
                            )
                    ).setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_G11,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_E),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_E)
                            )
                    ).setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_G12,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_N),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_N)
                            )
                    ).setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_G13,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_E),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_E)
                            )
                    )
                    .setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_G4,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_W),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_W)
                            )
                    )
                    .setBinding(
                            Mode.M2,
                            G13Key.G13_KEY_G4,
                            new Binding(
                                    new PressKeyAction(robot, KeyEvent.VK_S),
                                    new ReleaseKeyAction(robot, KeyEvent.VK_S)
                            )
                    )
                    .setBinding(
                            Mode.M1,
                            G13Key.G13_KEY_DOWN,
                            new Binding(
                                    new ChangeModeAction(Mode.M2),
                                    new ChangeModeAction(Mode.M1)
                            )
                    );
        }
        catch (AWTException ex) {
            throw new RuntimeException(ex);
        }
    }

}
