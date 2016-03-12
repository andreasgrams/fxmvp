package de.dreamnetworx.fxmvp.api;

import de.dreamnetworx.fxmvp.base.ApplicationContextProvider;
import de.dreamnetworx.fxmvp.base.FxMvpException;
import de.dreamnetworx.fxmvp.fx.FxmlSpringLoader;
import de.dreamnetworx.fxmvp.fx.FxmlSpringLoaderFactrory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Java FX bootstrap for MVP application.
 */
public abstract class FxMvpBootstrapApplication extends Application {

    /**
     *
     * @param context
     * @param appLayoutView
     * @return
     */
    protected FxMvpResult initializeMvpApp(final org.springframework.context.ApplicationContext context, final String appLayoutView) {
        ApplicationContextProvider.getInstance().setContext(context);

        //Maybe construct in spring context and inject only FxmlSpringLoader instance
        FxmlSpringLoaderFactrory.create(context);
        final FXMLLoader result = FxmlSpringLoader.getInstance().loader(appLayoutView);
        try {
            final Node moduleNode = result.load();
            final View moduleView = result.getController();
            return new FxMvpResult(moduleNode, moduleView);
        } catch (IOException e) {
            throw new FxMvpException("Can't create instance of ViewImpl", e);
        }
    }
}
