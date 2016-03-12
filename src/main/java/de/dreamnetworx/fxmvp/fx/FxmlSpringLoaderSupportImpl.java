package de.dreamnetworx.fxmvp.fx;

import javafx.fxml.FXMLLoader;

public class FxmlSpringLoaderSupportImpl implements FxmlSpringLoaderSupport {

    @Override
    public FXMLLoader load(final String fxmlFileName) {
        return FxmlSpringLoader.getInstance().loader(fxmlFileName);
    }
}
