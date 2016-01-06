package org.toilelibre.libe.restwebapp.actions.calculation;

import org.toilelibre.libe.restwebapp.actions.exception.ErrorCode;
import org.toilelibre.libe.restwebapp.actions.exception.RestException;

public class WrongCalculationAnswerException extends RestException {

    /**
     *
     */
    private static final long serialVersionUID = 7021598640223129005L;

    public WrongCalculationAnswerException () {
    }

    @Override
    public ErrorCode getCode () {
        return ErrorCode.WRONG_ANSWER;
    }

}
