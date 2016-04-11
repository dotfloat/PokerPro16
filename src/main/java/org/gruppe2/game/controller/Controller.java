package org.gruppe2.game.controller;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;
import org.gruppe2.game.view.AbstractView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Controller<M extends Model, V extends AbstractView<M>> {
    private final SessionContext sessionContext;

    public Controller(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public abstract void init();

    public abstract void update();

    public abstract M getModel();

    public abstract V getView();

    /**
     * Find a Method in SessionContext with the name of get(ClassName)[Model|View] with no arguments that returns a T
     *
     * eg. If the controller is called "GameController" and type is "Model", then this will try to return the method SessionContext.getGameModel()
     *
     * @return A method object
     */
    protected Method findContextGetMethod(String type) {
        String methodName = "get" + getClass().getSimpleName().replace("Controller", type);
        Method method = null;

        try {
            method = sessionContext.getClass().getDeclaredMethod(methodName);
        } catch(NoSuchMethodException | NullPointerException e) {
            System.err.println("SessionContext must have a public method named " + methodName + ".");
            e.printStackTrace();
        }

        return method;
    }
}
