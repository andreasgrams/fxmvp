package de.dreamnetworx.fxmvp.api;

public interface View<T extends ViewObserver> {

    void setViewObserver(T viewObserver);

}
