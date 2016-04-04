package org.gruppe2.ui.javafx;

import javafx.application.Platform;
import javafx.fxml.FXML;

class CloseMe extends VBoxController {
    @FXML
    public void quit() {
        Platform.exit();
    }
}
