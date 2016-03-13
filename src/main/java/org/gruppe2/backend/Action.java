package org.gruppe2.backend;

/**
 * Get
 */
public class Action {
    public static final int WAIT = 0; // TODO: Remove this

    private Type type;
    private int intVal;

    public Action(Type type, int intVal) {
        this.type = type;
        this.intVal = intVal;
    }

    public Action(Type type) {
        this(type, 0);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public enum Type {
        DISCONNECT,
        FOLD,
        ADD_CHIPS, /* Check, Call or Raise */
        BIG_BLIND,
        SMALL_BLIND
    }
}
