package de.dreamnetworx.fxmvp.base;

import org.springframework.util.StringUtils;

public class FxMvpDefaultNamingConventionConvention implements FxMvpNamingConvention {

    public static final String VIEW = "View";
    public static final String PRESENTER_IMPL = "PresenterImpl";

    /**
     * Determine the presenter name from fxml Filename.
     *
     * When the fxml file has the name personView.fxml then is name for Presenter PersonPresenterImpl.
     *
     * @param fxmlFileName
     * @return
     */
    @Override
    public String getPresenterName(String fxmlFileName) {
        fxmlFileName = StringUtils.uncapitalize(fxmlFileName);
        return fxmlFileName.replace(VIEW, PRESENTER_IMPL);
    }

}
