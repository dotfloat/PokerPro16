package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.Session;

public interface Controller {
    void setSession(Session session);
    Session getSession();

    void init();
    void update();

    Class<? extends Model> getModelClass();
}
