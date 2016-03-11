package de.dreamnetworx.fxmvp.base;

import com.google.common.eventbus.EventBus;
import de.dreamnetworx.fxmvp.fx.FxmlSpringLoader;
import de.dreamnetworx.fxmvp.fx.JavaInstanceCallback;
import de.dreamnetworx.fxmvp.fx.SpringContextCallback;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.io.IOException;

public abstract class FxMvpAppMvpPresenter<V extends View> extends FxMvpPresenter<V> {

    private SpringContextCallback springContextCallback;

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
     * @throws FxMvpException
     */
    protected FxMvpResult initFxPresenter(Stage stage, String fxmlFileName) {
        try {
            final FXMLLoader result = FxmlSpringLoader.getInstance().loader(fxmlFileName);
            final Node moduleNode = result.load();
            final View moduleView = result.getController();
            final String presenterName = getPresenterName(fxmlFileName);
            final Presenter presenter = getSpringPresenter(presenterName);
            if(presenter instanceof ViewObserver) {
                moduleView.setViewObserver((ViewObserver) presenter);
            }
            ((FxMvpPresenter) presenter).construct(moduleView, moduleNode, getEventBus());
            presenter.startPresenting(stage);

            return new FxMvpResult(moduleNode, moduleView, presenter);

        } catch (IOException e) {
            return null;
        }
    }

    private Presenter getSpringPresenter(String presenter) {


        final String springPresenterName = StringUtils.uncapitalize(presenter);
        final ApplicationContext context = ApplicationContextProvider.getInstance().getContext();
        final Object controllerBean = context.getBean(springPresenterName);

        return (Presenter) springContextCallback.call(controllerBean.getClass());
    }

    private String getPresenterName(String fxmlFileName) {
        fxmlFileName = StringUtils.uncapitalize(fxmlFileName);
        return fxmlFileName.replace("View", "PresenterImpl");
    }



}
