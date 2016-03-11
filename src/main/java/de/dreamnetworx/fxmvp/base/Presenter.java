package de.dreamnetworx.fxmvp.base;

import javafx.stage.Stage;

public interface Presenter<V extends View> {

    void startPresenting(Stage stage);

}
