package org.gruppe2.game.view;

import org.gruppe2.game.session.SessionContext;

public interface View {
    void setContext(SessionContext context);
    SessionContext getContext();
}
