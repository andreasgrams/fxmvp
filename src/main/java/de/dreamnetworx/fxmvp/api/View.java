package de.dreamnetworx.fxmvp.api;

public interface View<T extends ViewObserver> {

    /**
     * Bound the viewObserver to the view.
     *
     * @param viewObserver
     */
    void setViewObserver(T viewObserver);

}
