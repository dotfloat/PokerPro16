package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;

public class PlayerController extends AbstractController<PlayerModel> {

    @Override
    public Class<? extends Model> getModelClass() {
        return PlayerModel.class;
    }
}
