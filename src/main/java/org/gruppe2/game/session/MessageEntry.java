package org.gruppe2.game.session;

class MessageEntry {
    private final String name;
    private final Object[] args;
    private final Query<Boolean> returnVal;

    public MessageEntry(String name, Object[] args) {
        this.name = name;
        this.args = args;
        returnVal = new Query<>();
    }

    public String getName() {
        return name;
    }

    public Object[] getArgs() {
        return args;
    }

    public Query<Boolean> getReturnVal() {
        return returnVal;
    }
}
