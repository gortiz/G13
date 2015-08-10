
package com.golthiware.g13.controller;

import com.golthiware.g13.controller.actions.Action;
import com.golthiware.g13.controller.actions.NopAction;
import com.google.common.base.Preconditions;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.EnumMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 */
public class Mapper implements Consumer<G13Diff> {

    private @Nonnull Mode mode;

    private static final Logger LOGGER = Logger.getLogger(Mapper.class.getName());
    
    private EnumMap<G13Key, Mapper.Binding> m1Binding;
    private EnumMap<G13Key, Mapper.Binding> m2Binding;
    private EnumMap<G13Key, Mapper.Binding> m3Binding;

    private final Robot robot;
    private G13State inferedState;

    private final Executor executor;
    private static final AtomicInteger threadIdProvider = new AtomicInteger(0);

    public Mapper() {
        mode = Mode.M1;
        try {
            robot = new Robot();
        }
        catch (AWTException ex) {
            throw new RuntimeException(ex);
        }

        m1Binding = new EnumMap<>(G13Key.class);
        m2Binding = new EnumMap<>(G13Key.class);
        m3Binding = new EnumMap<>(G13Key.class);

        executor = Executors.newSingleThreadExecutor((Runnable r) -> {
            Thread t = new Thread(r, "G13 executor thread " + threadIdProvider.getAndIncrement());
            t.setDaemon(true);
            t.setUncaughtExceptionHandler(
                    (Thread t1, Throwable ex) -> {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
            );

            return t;
        });
    }

    @Override
    public void accept(G13Diff diff) {
        executor.execute(() -> {
            EnumMap<G13Key, Mapper.Binding> tempM1Binding = m1Binding;
            EnumMap<G13Key, Mapper.Binding> tempM2Binding = m2Binding;
            EnumMap<G13Key, Mapper.Binding> tempM3Binding = m3Binding;
            Function<G13Key, Mapper.Binding> bindingProvider;
            
            switch (mode) {
                case M1:
                    bindingProvider = (key) -> {return tempM1Binding.get(key);};
                    break;
                case M2:
                    bindingProvider = (key) -> {
                        Mapper.Binding result = tempM2Binding.get(key);
                        if (result == null) {
                            result = tempM1Binding.get(key);
                        }
                        return result;
                    };
                    break;
                case M3:
                    bindingProvider = (key) -> {
                        Mapper.Binding result = tempM3Binding.get(key);
                        if (result == null) {
                            result = tempM2Binding.get(key);
                        }
                        if (result == null) {
                            result = tempM1Binding.get(key);
                        }
                        return result;
                    };
                    break;
                default:
                    throw new AssertionError("Unexpected mode: " + mode);
            }
            MapperInterface mapperInterface = new MapperInterface(robot);
            for (G13Key pressedKey : diff.getPressedKeys()) {
                Binding binding = bindingProvider.apply(pressedKey);
                if (binding != null) {
                    binding.getOnPressAction().execute(mapperInterface);
                }
            }
            for (G13Key releasedKey : diff.getReleasedKeys()) {
                Binding binding = bindingProvider.apply(releasedKey);
                if (binding != null) {
                    binding.getOnReleaseAction().execute(mapperInterface);
                }
            }
            if (mapperInterface.getNewMode() != null && !mapperInterface.getNewMode().equals(getMode())) {
            }
        }
        );
    }

    @Nonnull
    public Mode getMode() {
        return mode;
    }

    public void setMode(@Nonnull Mode mode) {
        Preconditions.checkArgument(mode != null, "Mode must be non null");
        this.mode = mode;
    }

    public Mapper setBinding(
            @Nonnull Mode mode,
            @Nonnull G13Key key,
            @Nullable Mapper.Binding binding) {
        switch (mode) {
            case M1:
                m1Binding = new EnumMap<>(m1Binding);
                m1Binding.put(key, binding);
                break;
            case M2:
                m2Binding = new EnumMap<>(m2Binding);
                m2Binding.put(key, binding);
                break;
            case M3:
                m3Binding = new EnumMap<>(m3Binding);
                m3Binding.put(key, binding);
                break;
        }
        return this;
    }

    public static enum Mode {
        M1,
        M2,
        M3
    }

    public static class Binding {
        private final Action onPressAction;
        private final Action onReleaseAction;

        public Binding(@Nullable Action onPressAction, @Nullable Action onReleaseAction) {
            this.onPressAction = onPressAction != null ? onPressAction : NopAction.INSTANCE;
            this.onReleaseAction = onReleaseAction != null ? onReleaseAction : NopAction.INSTANCE;
        }

        public Action getOnPressAction() {
            return onPressAction;
        }

        public Action getOnReleaseAction() {
            return onReleaseAction;
        }
    }

    public static class MapperInterface {
        private final Robot robot;
        private Mapper.Mode newMode;

        public MapperInterface(Robot robot) {
            this.robot = robot;
        }

        public void pressKey(int keycode) {
            robot.keyPress(keycode);
        }

        public void releaseKey(int keycode) {
            robot.keyRelease(keycode);
        }

        public Mode getNewMode() {
            return newMode;
        }

        public void setNewMode(Mode newMode) {
            this.newMode = newMode;
        }
    }
}
