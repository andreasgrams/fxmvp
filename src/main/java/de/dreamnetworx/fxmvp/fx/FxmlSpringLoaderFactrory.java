package de.dreamnetworx.fxmvp.fx;

import org.springframework.context.ApplicationContext;

public class FxmlSpringLoaderFactrory {

    public static FxmlSpringLoader create(final ApplicationContext context) {
        return FxmlSpringLoader.getInstance().setApplicationContext(context);
    }

}
