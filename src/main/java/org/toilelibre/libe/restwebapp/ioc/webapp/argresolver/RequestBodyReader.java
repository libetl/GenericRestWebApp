package org.toilelibre.libe.restwebapp.ioc.webapp.argresolver;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;

class RequestBodyReader {

    private static final String BODY_ATTRIBUTE = "REQUEST_BODY";

    static String getRequestBody (final NativeWebRequest webRequest) {
        final HttpServletRequest servletRequest = webRequest.getNativeRequest (HttpServletRequest.class);
        final String entityBody = (String) servletRequest.getAttribute (RequestBodyReader.BODY_ATTRIBUTE);
        if (entityBody == null) {
            try {
                final Scanner scanner = new Scanner (servletRequest.getInputStream (), "UTF-8");
                final String body = scanner.useDelimiter ("\\A").next ();
                servletRequest.setAttribute (RequestBodyReader.BODY_ATTRIBUTE, body);
                scanner.close ();
                return body;
            } catch (final IOException e) {
                throw new RuntimeException (e);
            }
        }
        return entityBody;

    }
}
