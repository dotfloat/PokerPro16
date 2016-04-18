package org.gruppe2.game.session;

class MessageEntry {
    private final String name;
    private final Object[] args;
    private final SimpleFuture<Boolean> returnVal;

    public MessageEntry(String name, Object[] args) {
        this.name = name;
        this.args = args;
        returnVal = new SimpleFuture<>();
    }

    public String getName() {
        return name;
    }

    public Object[] getArgs() {
        return args;
    }

    public SimpleFuture<Boolean> getReturnVal() {
        return returnVal;
    }
}
