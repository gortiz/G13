
package com.golthiware.g13.controller.actions;

import com.golthiware.g13.controller.Mapper.MapperInterface;

/**
 *
 */
public class NopAction implements Action {
    public static NopAction INSTANCE = new NopAction();

    private NopAction() {
    }

    @Override
    public void execute(MapperInterface mapper) {
    }

}
