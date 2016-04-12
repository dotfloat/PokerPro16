package org.gruppe2.game.session;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.gruppe2.game.model.Model;
import org.gruppe2.game.model.PlayerModel;
import org.gruppe2.game.view.View;

import java.util.*;

public abstract class Session implements Runnable {
    private final MainEventQueue eventQueue = new MainEventQueue();

    private final Map<Class<? extends Model>, List<Model>> modelMap = Collections.synchronizedMap(new HashMap<>());

    private final SessionContext sessionContext = new SessionContext(this);

    public static SessionContext start(Class<? extends Session> klass) {
        Session session;

        try {
            session = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        return session.start();
    }

    public SessionContext start() {
        new Thread(this).start();

        return new SessionContext(this);
    }

    @Override
    public void run() {
        while (true) {

            eventQueue.process();

            update();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    MainEventQueue getEventQueue() {
        return eventQueue;
    }

    public <M extends Model> void addModels(Class<M> klass) {
        modelMap.put(klass, Collections.synchronizedList(new ArrayList<>()));
    }

    public <M extends Model> List<M> getModels(Class<M> klass) {
        return (List<M>) modelMap.get(klass);
    }

    public <M extends Model> M getModel(Class<M> klass) {
        List<Model> list = modelMap.get(klass);

        return list != null ? (M) list.get(0) : null;
    }

    public abstract void update();

    public abstract void exit();

    // TODO: Move these to a "GameController" or something

    public abstract boolean addPlayer(PlayerModel model);

    public SessionContext getSessionContext() {
        return sessionContext;
    }
}
