package org.gruppe2.game.controller;

import org.gruppe2.game.helper.RoundHelper;
import org.gruppe2.game.session.Helper;
import org.gruppe2.game.session.Message;

public class RoundController extends AbstractController {
    @Helper
    private RoundHelper round;

    @Override
    public void update() {
        if (round.isPlaying()) {
            // Go to next player and do shit
        }
    }

    @Message
    public boolean roundStart() {
        if (!round.isPlaying()) {
            round.setPlaying(true);
            return true;
        }

        return false;
    }
}
