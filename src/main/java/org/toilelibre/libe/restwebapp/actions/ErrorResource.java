package org.toilelibre.libe.restwebapp.actions;

import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.toilelibre.libe.restwebapp.actions.entity.Node;
import org.toilelibre.libe.restwebapp.actions.entity.NodeFactory;
import org.toilelibre.libe.restwebapp.actions.entity.ObjectNode;
import org.toilelibre.libe.restwebapp.actions.exception.ErrorCode;
import org.toilelibre.libe.restwebapp.actions.exception.ErrorCode.Kind;
import org.toilelibre.libe.restwebapp.actions.exception.RestException;

@RestController
@ControllerAdvice
public class ErrorResource {
    private static Logger LOGGER = LoggerFactory.getLogger (ErrorResource.class);

    @Inject
    private LinkHelper linkHelper;

    @RequestMapping (path = "badEntityFormat")
    @ExceptionHandler (HttpMediaTypeNotSupportedException.class)
    @ResponseStatus (code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ObjectNode<Node> badEntityFormat () {
        final NodeFactory factory = NodeFactory.instance;
        return factory.objectNode ().put ("ok", 0).put ("name", "BadEntityFormat").put ("description", "This API does not understand this input format.").put ("kind",
                Kind.BAD_INPUT.name ());
    }

    @RequestMapping (path = "badMethod")
    @ExceptionHandler (HttpRequestMethodNotSupportedException.class)
    @ResponseStatus (code = HttpStatus.METHOD_NOT_ALLOWED)
    public ObjectNode<Node> badMethod () {
        final NodeFactory factory = NodeFactory.instance;
        return this.linkHelper.findSuggestedLink (factory.objectNode ().put ("ok", 0).put ("name", "BadMethodOfAPI")
                .put ("description", "This API exists, but the request method does not exist.").put ("kind", Kind.BAD_INPUT.name ()));
    }

    @RequestMapping (path = "badRequest")
    @ExceptionHandler ({ IllegalArgumentException.class, IllegalStateException.class, ServletException.class, HttpMessageNotReadableException.class })
    @ResponseStatus (code = HttpStatus.BAD_REQUEST)
    public ObjectNode<Node> badRequest (final Exception exception) {
        final NodeFactory factory = NodeFactory.instance;
        return factory.objectNode ().put ("ok", 0).put ("name", "BadRequest").put ("description", exception.getMessage ()).put ("kind", Kind.BAD_INPUT.name ());
    }

    @RequestMapping (path = "error")
    @ExceptionHandler (RestException.class)
    public ObjectNode<Node> handleApplicationException (final HttpServletRequest request, final HttpServletResponse response, final RestException exception) {
        final ErrorCode code = exception.getCode ();
        ErrorResource.LOGGER.error (code.getKind ().name () + " while trying to use \"" + request.getRequestURI () + "\" with verb \"" + request.getMethod () + "\" : ", exception);
        response.setStatus (KindToHttpStatus.from (code.getKind ()));
        final NodeFactory factory = NodeFactory.instance;
        return factory.objectNode ().put ("ok", 0).put ("name", code.name ()).put ("description", code.getDescription ()).put ("kind", code.getKind ().name ());
    }

    @RequestMapping (path = "internalServerError")
    @ExceptionHandler (RuntimeException.class)
    @ResponseStatus (code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ObjectNode<Node> internalServerError (final RuntimeException exception) {
        final NodeFactory factory = NodeFactory.instance;
        final UUID errorUuid = UUID.randomUUID ();
        ErrorResource.LOGGER.error ("An internal server error has happened (errorUuid : " + errorUuid + ")", exception);
        return factory.objectNode ().put ("ok", 0).put ("name", "InternalServerError")
                .put ("description", "This API could not respond correctly. There was an API bug during the service").put ("kind", HttpStatus.INTERNAL_SERVER_ERROR.name ())
                .put ("errorUuid", errorUuid.toString ());
    }

    @RequestMapping (path = "notFound")
    @ExceptionHandler (NoHandlerFoundException.class)
    @ResponseStatus (code = HttpStatus.NOT_FOUND)
    public ObjectNode<Node> notFound (final HttpServletRequest request) {
        final NodeFactory factory = NodeFactory.instance;
        return this.linkHelper.findSuggestedLink (
                factory.objectNode ().put ("ok", 0).put ("name", "APINotFound").put ("description", "This API does not exist").put ("kind", Kind.NOT_FOUND.name ()));
    }
}
