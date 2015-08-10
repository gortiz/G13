
package com.golthiware.g13.controller.actions;

import com.golthiware.g13.controller.Mapper.MapperInterface;

/**
 *
 */
public class WaitAction implements Action {

    private final int millis;

    public WaitAction(int millis) {
        this.millis = millis;
    }

    @Override
    public void execute(MapperInterface mapper) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
        }
    }

}
