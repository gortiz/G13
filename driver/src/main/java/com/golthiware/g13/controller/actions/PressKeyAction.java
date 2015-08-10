
package com.golthiware.g13.controller.actions;

import com.golthiware.g13.controller.Mapper.MapperInterface;
import java.awt.Robot;

/**
 *
 */
public class PressKeyAction implements Action {

    private final Robot robot;
    private final int key;

    public PressKeyAction(Robot robot, int key) {
        this.robot = robot;
        this.key = key;
    }

    @Override
    public void execute(MapperInterface mapper) {
        robot.keyPress(key);
    }

}
