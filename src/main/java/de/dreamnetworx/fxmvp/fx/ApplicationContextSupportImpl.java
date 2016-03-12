package de.dreamnetworx.fxmvp.fx;

import de.dreamnetworx.fxmvp.base.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;

public class ApplicationContextSupportImpl implements ApplicationContextSupport {

    @Override
    public ApplicationContext getContext() {
        return ApplicationContextProvider.getInstance().getContext();
    }
}
