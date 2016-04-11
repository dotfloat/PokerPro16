package org.gruppe2.game.view;

import org.gruppe2.game.controller.AbstractController;
import org.gruppe2.game.model.Model;

public abstract class AbstractView<M extends Model, T extends AbstractController<M, ?>> {
    private T controller;

    public AbstractView(T controller) {
        this.controller = controller;
    }

    T getController() {
        return controller;
    }

    public void setController(T controller) {
        this.controller = controller;
    }

    public M getModel() {
        return controller.getModel();
    }
}
