package org.gruppe2.game.view;

import org.gruppe2.game.controller.AbstractController;

public abstract class AbstractView<T extends AbstractController> {
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
}
