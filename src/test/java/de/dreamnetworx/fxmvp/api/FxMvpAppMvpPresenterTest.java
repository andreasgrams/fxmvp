package de.dreamnetworx.fxmvp.api;

import de.dreamnetworx.fxmvp.base.FxMvpException;
import de.dreamnetworx.fxmvp.fx.ApplicationContextSupport;
import de.dreamnetworx.fxmvp.fx.FxmlSpringLoaderSupport;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class FxMvpAppMvpPresenterTest {

    private static final String FILENAME = "filenameView";
    private FxMvpAppMvpPresenter cut;

    private Stage stage = Mockito.mock(Stage.class);
    private FxmlSpringLoaderSupport fxmlSpringLoaderSupport = Mockito.mock(FxmlSpringLoaderSupport.class);
    private FXMLLoader fxmlLoader = Mockito.mock(FXMLLoader.class);
    private ApplicationContextSupport applicationContextSupport = Mockito.mock(ApplicationContextSupport.class);
    private ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);

    @Before
    public void setup() {
        when(applicationContextSupport.getContext()).thenReturn(applicationContext);
        cut = new FxMvpAppMvpPresenter() {
            @Override
            public void startPresenting(final Stage stage) {

            }
        };
        cut.setFxmlSpringLoaderSupport(fxmlSpringLoaderSupport);
        cut.setApplicationContextSupport(applicationContextSupport);

    }

    /**
     *
     */
    @Test(expected = FxMvpException.class)
    public void shouldProvideExceptionWhenFxmlFileNotFound() {
        //given
        when(fxmlSpringLoaderSupport.load(anyString())).thenReturn(fxmlLoader);
        //when
        cut.initFxPresenter(stage, FILENAME);
        //then
    }

    /**
     *
     */
    @Test(expected = FxMvpException.class)
    public void shouldProvideExceptionWhenViewHasNoController() throws IOException {
        //given
        when(fxmlLoader.load()).thenReturn(new HBox());
        when(fxmlLoader.getController()).thenReturn(null);
        when(fxmlSpringLoaderSupport.load(anyString())).thenReturn(fxmlLoader);
        //when
        cut.initFxPresenter(stage, FILENAME);
        //then
    }

    /**
     *
     */
    @Test(expected = FxMvpException.class)
    public void shouldProvideExceptionWhenSpringPresenterNotFound() throws IOException {
        //given
        when(fxmlLoader.load()).thenReturn(new HBox());
        when(fxmlSpringLoaderSupport.load(anyString())).thenReturn(fxmlLoader);

        when(fxmlLoader.getController()).thenReturn(null);
        when(applicationContext.getBean(anyString())).thenThrow(NoSuchBeanDefinitionException.class);
        //when
        cut.initFxPresenter(stage, FILENAME);
        //then
    }

    class ViewToTest implements View<PresenterToTest> {

        @Override
        public void setViewObserver(final PresenterToTest viewObserver) {

        }
    }

    class PresenterToTest implements Presenter<ViewToTest>, ViewObserver {

        @Override
        public void startPresenting(final Stage stage) {

        }
    }
}