
package com.golthiware.g13.controller.actions;

import com.golthiware.g13.controller.Mapper.MapperInterface;
import java.util.List;

/**
 *
 */
public class SequenceAction implements Action {

    private final List<Action> subActions;

    public SequenceAction(List<Action> subActions) {
        this.subActions = subActions;
    }

    @Override
    public void execute(MapperInterface mapper) {
        for (Action subAction : subActions) {
            subAction.execute(mapper);
        }
    }

}
