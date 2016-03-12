package de.dreamnetworx.fxmvp.api;

import javafx.stage.Stage;

/**
 * The Presenter controls the view.
 */
public interface Presenter<V extends View> {

    /**
     * Provides the view to display. Typically the presenter registers listeners and set the viewModel.
     *
     * @param stage
     */
    void startPresenting(Stage stage);

}
