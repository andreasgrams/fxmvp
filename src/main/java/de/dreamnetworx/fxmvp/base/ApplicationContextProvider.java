package de.dreamnetworx.fxmvp.base;

import org.springframework.context.ApplicationContext;

public class ApplicationContextProvider {

    static ApplicationContextProvider INSTANCE;
    private ApplicationContext context;

    public static ApplicationContextProvider getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApplicationContextProvider();
        }
        return INSTANCE;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(final ApplicationContext context) {
        this.context = context;
    }
}
