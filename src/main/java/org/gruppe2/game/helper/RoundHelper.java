package org.gruppe2.game.helper;

import org.gruppe2.game.model.RoundModel;
import org.gruppe2.game.session.SessionContext;

public class RoundHelper {
    private RoundModel model;

    public RoundHelper(SessionContext context) {
        model = context.getModel(RoundModel.class);
    }

    public boolean isPlaying() {
        return model.isPlaying();
    }

    public void setPlaying(boolean playing) {
        model.setPlaying(playing);
    }
}
