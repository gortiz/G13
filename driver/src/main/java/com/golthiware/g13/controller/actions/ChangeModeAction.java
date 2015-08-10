
package com.golthiware.g13.controller.actions;

import com.golthiware.g13.controller.Mapper;
import com.golthiware.g13.controller.Mapper.MapperInterface;
import com.golthiware.g13.controller.Mapper.Mode;

/**
 *
 */
public class ChangeModeAction implements Action {

    private final Mapper.Mode newMode;

    public ChangeModeAction(Mode newMode) {
        this.newMode = newMode;
    }

    @Override
    public void execute(MapperInterface mapper) {
        mapper.setNewMode(newMode);
    }

}
