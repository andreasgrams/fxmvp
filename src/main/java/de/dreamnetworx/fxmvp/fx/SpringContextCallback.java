package de.dreamnetworx.fxmvp.fx;

import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

public class SpringContextCallback implements Callback<Class<?>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(SpringContextCallback.class);

    private final ApplicationContext applicationContext;
    private final Callback callback;

    public SpringContextCallback(final Callback callback, final ApplicationContext applicationContext) {
        this.callback = callback;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object call(final Class<?> param) {
        final String simpleName = param.getSimpleName();
        final String springPresenterName = StringUtils.uncapitalize(simpleName);
        try {
            final Object controllerBean = applicationContext.getBean(springPresenterName);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(controllerBean);
            LOG.info("load viewController from spring context {}", springPresenterName);
            return controllerBean;

        } catch (final NoSuchBeanDefinitionException e) {
            if(callback != null) {
                return callback.call(param);
            }
            return null;
        }
    }
}
