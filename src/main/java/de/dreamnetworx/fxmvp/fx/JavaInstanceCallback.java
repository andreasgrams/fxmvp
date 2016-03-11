package de.dreamnetworx.fxmvp.fx;

import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaInstanceCallback implements Callback<Class<?>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(JavaInstanceCallback.class);

    private Callback callback;

    public JavaInstanceCallback() {
    }

    public JavaInstanceCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Object call(final Class<?> param) {
        final String simpleName = param.getSimpleName();
        try {
            LOG.info("load viewController per as new Instance {}", simpleName);
            return param.newInstance();
        } catch (InstantiationException | IllegalAccessException e1) {
            if(callback != null) {
                LOG.error("Can't create a new instance of {} use fallback callback", simpleName);
                return callback.call(param);
            }
            return null;
        }
    }
}
