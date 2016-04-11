package org.gruppe2.game.session;

import org.gruppe2.game.event.TestEvent;
import org.gruppe2.game.event.TestEventHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurrentEventQueueTest {
    private ConcurrentEventQueue queue;

    @Before
    public void setup() {
        queue = new ConcurrentEventQueue();
    }

    /**
     * Processing with an empty queue shouldn't block or throw an exception.
     */
    @Test
    public void emptyQueueTest() {
        queue.process();
    }

    /**
     * Processing when there is an event, but no handler for the event,
     * shouldn't throw exceptions or block.
     */
    @Test
    public void processWithOneEventWithNoHandlerTest() {
        queue.addEvent(TestEvent.class, new TestEvent());
        queue.process();
    }

    /**
     * When processing, the entire queue should be consumed.
     */
    @Test
    public void processConsumesEventTest() {
        queue.addEvent(TestEvent.class, new TestEvent());

        assertEquals(1, queue.numPending());

        queue.process();

        assertEquals(0, queue.numPending());
    }

    /**
     * If there are no events waiting, but there is a handler for the event,
     * then the handler shouldn't do anything.
     */
    @Test
    public void eventHandlerWithNoEventTest() {
        TestEventHandler handler = new TestEventHandler();

        queue.registerHandler(TestEvent.class, handler);
        queue.process();

        assertFalse(handler.isHandled());
    }

    /**
     * If there is an event waiting and a handler for the event, the event should be handled.
     */
    @Test
    public void eventHandlerWithEventTest() {
        TestEventHandler handler = new TestEventHandler();

        queue.addEvent(TestEvent.class, new TestEvent());
        queue.registerHandler(TestEvent.class, handler);
        queue.process();

        assertTrue(handler.isHandled());
    }
}