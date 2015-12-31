package org.toilelibre.libe.restwebapp.actions.exception;


public enum ErrorCode {

    WRONG_ANSWER (Kind.BAD_INPUT, "Your calculation answer was not correct.");

    public enum Kind {
        NOT_FOUND, BAD_INPUT, CONFLICT, FORBIDDEN, ERROR;
    }

    private String description;
    private Kind kind;

    ErrorCode (final Kind kind1, final String description1) {
        this.kind = kind1;
        this.description = description1;
    }

    public String getDescription () {
        return this.description;
    }

    public Kind getKind () {
        return this.kind;
    }
}
