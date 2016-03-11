package de.dreamnetworx.fxmvp.base;

public interface FxMvpNamingConvention {

    /**
     * Determine the presenter name from fxml Filename.
     *
     * @param fxmlFileName
     * @return
     */
    String getPresenterName(String fxmlFileName);
}
