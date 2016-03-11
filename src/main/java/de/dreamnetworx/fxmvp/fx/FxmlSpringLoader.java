package de.dreamnetworx.fxmvp.fx;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FxmlSpringLoader {

    public static final String FXML_SOURCE = "fxml";

    private static ApplicationContext applicationContext;

    private static FxmlSpringLoader instance;

    FxmlSpringLoader() {
    }

    public FXMLLoader loader(final String viewName) {
        final ClassPathResource classPathResource = new ClassPathResource(FXML_SOURCE + "/" + viewName + "." + FXML_SOURCE);

        FXMLLoader loader;
        try {
            loader = new FXMLLoader(classPathResource.getURL());
            loader.setResources(Messages.getResourceBundle());

            loader.setControllerFactory(new SpringContextCallback(new JavaInstanceCallback(), applicationContext));

            return loader;
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FxmlSpringLoader getInstance() {
        if(instance == null) {
            instance = new FxmlSpringLoader();
        }
        return instance;
    }

    FxmlSpringLoader setApplicationContext(final ApplicationContext applicationContext) {
        FxmlSpringLoader.applicationContext = applicationContext;
        return instance;
    }
}
