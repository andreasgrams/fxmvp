package de.dreamnetworx.fxmvp.base;

public class FxMvpException extends RuntimeException {

    public FxMvpException(final String messgae) {
        super(messgae);
    }

    public FxMvpException(final String messgae, final Throwable t) {
        super(messgae, t);
    }
}
