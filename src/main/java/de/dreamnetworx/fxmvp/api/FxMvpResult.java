package de.dreamnetworx.fxmvp.api;

import javafx.scene.Node;

public class FxMvpResult {

    private Node node;
    private View view;
    private Presenter presenter;

    public FxMvpResult(final Node node, final View view) {
        this(node, view, null);
    }

    public FxMvpResult(final Node node, final View view, Presenter presenter) {
        this.node = node;
        this.view = view;
        this.presenter = presenter;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public Node getNode() {
        return node;
    }

    public View getView() {
        return view;
    }
}
