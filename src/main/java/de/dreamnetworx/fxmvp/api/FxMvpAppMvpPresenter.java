package de.dreamnetworx.fxmvp.api;

import com.google.common.eventbus.EventBus;
import de.dreamnetworx.fxmvp.base.ApplicationContextProvider;
import de.dreamnetworx.fxmvp.base.FxMvpDefaultNamingConventionConvention;
import de.dreamnetworx.fxmvp.base.FxMvpException;
import de.dreamnetworx.fxmvp.base.FxMvpNamingConvention;
import de.dreamnetworx.fxmvp.fx.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.IOException;

public abstract class FxMvpAppMvpPresenter<V extends View> extends FxMvpPresenter<V> {

    private SpringContextCallback springContextCallback;

    private FxMvpNamingConvention fxMvpNamingConvention = new FxMvpDefaultNamingConventionConvention();

    private FxmlSpringLoaderSupport fxmlSpringLoaderSupport = new FxmlSpringLoaderSupportImpl();

    private ApplicationContextSupport applicationContextSupport = new ApplicationContextSupportImpl();

    public FxMvpAppMvpPresenter() {
        springContextCallback = new SpringContextCallback(new JavaInstanceCallback(),
                ApplicationContextProvider.getInstance().getContext());
    }

    @Override
    public void construct(final V view, final Node viewNode, final EventBus eventBus) {
        setView(view);
        setViewNode(viewNode);
        setEventBus(eventBus);
    }

    /**
     * @param stage
     * @param fxmlFileName
     * @return
     * @throws FxMvpException when fxml file not found or
     *                        when fxml file has no controller
     */
    protected FxMvpResult initFxPresenter(Stage stage, String fxmlFileName) {
        try {
            final FXMLLoader result = fxmlSpringLoaderSupport.load(fxmlFileName);
            final Node moduleNode = result.load();
            if(moduleNode == null) {
                throw new FxMvpException("FXML file '\" + fxmlFileName + \"'  can't be loaded, check naming of filename!");
            }
            final View moduleView = result.getController();
            if(moduleView == null) {
                throw new FxMvpException("No fx controller in fxml '" + fxmlFileName + "' file definite!");
            }
            final String presenterName = fxMvpNamingConvention.getPresenterName(fxmlFileName);
            final Presenter presenter = getSpringPresenter(presenterName);
            if(presenter instanceof ViewObserver) {
                moduleView.setViewObserver((ViewObserver) presenter);
            }
            getEventBus().register(this);
            ((FxMvpPresenter) presenter).construct(moduleView, moduleNode, getEventBus());
            presenter.startPresenting(stage);

            return new FxMvpResult(moduleNode, moduleView, presenter);

        } catch (IOException e) {
            throw new FxMvpException("Presenter can't be initialized", e);
        }
    }

    private Presenter getSpringPresenter(String presenter) {

        final String springPresenterName = StringUtils.uncapitalize(presenter);
        final ApplicationContext context = applicationContextSupport.getContext();
        try {
            final Object controllerBean = context.getBean(springPresenterName);
            return (Presenter) springContextCallback.call(controllerBean.getClass());
        } catch (NoSuchBeanDefinitionException e) {
            throw new FxMvpException("No such presenter found with name " + presenter, e);
        }
    }

    public void setFxMvpNamingConvention(final FxMvpNamingConvention fxMvpNamingConvention) {
        this.fxMvpNamingConvention = fxMvpNamingConvention;
    }

    public void setFxmlSpringLoaderSupport(final FxmlSpringLoaderSupport fxmlSpringLoaderSupport) {
        this.fxmlSpringLoaderSupport = fxmlSpringLoaderSupport;
    }

    public void setApplicationContextSupport(final ApplicationContextSupport applicationContextSupport) {
        this.applicationContextSupport = applicationContextSupport;
    }
}
