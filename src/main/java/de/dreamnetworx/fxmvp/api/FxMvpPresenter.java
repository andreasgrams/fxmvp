package de.dreamnetworx.fxmvp.api;

import com.google.common.eventbus.EventBus;
import de.dreamnetworx.fxmvp.api.Presenter;
import de.dreamnetworx.fxmvp.api.View;
import javafx.scene.Node;

public abstract class FxMvpPresenter<V extends View> implements Presenter<V> {

    private V view;
    private Node viewNode;
    private EventBus eventBus;

    public void construct(final V view, final Node viewNode, final EventBus eventBus) {
        setView(view);
        setViewNode(viewNode);
        setEventBus(eventBus);
        eventBus.register(this);
    }

    public Node getViewNode() {
        return viewNode;
    }

    public void setViewNode(final Node viewNode) {
        this.viewNode = viewNode;
    }

    public V getView() {
        return view;
    }

    public void setView(final V view) {
        this.view = view;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
