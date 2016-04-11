package org.gruppe2.game.view;

import org.gruppe2.game.model.Model;
import org.gruppe2.game.session.SessionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractView<T extends Model> {
    private SessionContext sessionContext;
    private Method getModelMethod;

    public AbstractView(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public T getModel() throws InvocationTargetException, IllegalAccessException {
        return (T) getModelMethod.invoke(sessionContext);
    }

    /**
     * Find a Method in SessionContext with the name of get[ClassName]Model with no arguments that returns a T
     *
     * eg. If the view is called "GameView", then this will try to return the method SessionContext.getGameModel()
     *
     * @return A method object
     */
    private Method findGetModelMethod() {
        String methodName = "get" + getClass().getSimpleName().replace("View", "Model");
        Method method = null;

        try {
            method = sessionContext.getClass().getDeclaredMethod(methodName);
        } catch(NoSuchMethodException e) {
            System.err.println("SessionContext must have a public method named " + methodName + ".");
            e.printStackTrace();
        }

        return method;
    }
}
