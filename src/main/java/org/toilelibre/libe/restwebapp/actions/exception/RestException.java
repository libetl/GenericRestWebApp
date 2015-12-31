package org.toilelibre.libe.restwebapp.actions.exception;

public abstract class RestException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -7784462491935784057L;

    public RestException () {
    }

    public RestException (Exception e) {
        super (e);
    }

    public abstract ErrorCode getCode ();

    @Override
    public String getMessage () {
        return this.getCode ().getDescription ();
    }
}
