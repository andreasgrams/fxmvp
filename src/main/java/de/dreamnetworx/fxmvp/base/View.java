package de.dreamnetworx.fxmvp.base;

public interface View<T extends ViewObserver> {

    void setViewObserver(T viewObserver);

}
