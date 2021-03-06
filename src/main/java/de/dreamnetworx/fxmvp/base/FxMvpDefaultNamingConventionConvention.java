package de.dreamnetworx.fxmvp.base;

import org.springframework.util.StringUtils;

public class FxMvpDefaultNamingConventionConvention implements FxMvpNamingConvention {

    public static final String VIEW = "View";
    public static final String PRESENTER_IMPL = "PresenterImpl";

    /**
     * Determine the presenter name from fxml filename.
     * <p>
     * When the fxml file has the name personView.fxml then is name for presenter personPresenterImpl.
     *
     * @param fxmlFileName filename without path.
     * @return the name of view presenter.
     */
    @Override
    public String getPresenterName(String fxmlFileName) {
        fxmlFileName = StringUtils.uncapitalize(fxmlFileName);
        if(!fxmlFileName.contains(VIEW)) {
            throw new FxMvpException("The view follows not the naming convention! The filename must ends with '*View'");
        }
        return fxmlFileName.replace(VIEW, PRESENTER_IMPL);
    }

}
