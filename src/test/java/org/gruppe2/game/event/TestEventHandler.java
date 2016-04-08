package org.gruppe2.game.event;

public class TestEventHandler implements EventHandler<TestEvent> {
    private boolean handled = false;

    @Override
    public void handle(TestEvent event) {
        handled = true;
    }

    public boolean isHandled() {
        return handled;
    }
}
