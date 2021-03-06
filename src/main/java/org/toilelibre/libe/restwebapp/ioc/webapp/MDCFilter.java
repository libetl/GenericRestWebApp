package org.toilelibre.libe.restwebapp.ioc.webapp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class MDCFilter extends OncePerRequestFilter {

    private static final String REQUEST_PATH = "requestPath";
    private static final String REQUEST_VERB = "requestVerb";

    private void addRequestInfosIntoMDC (final HttpServletRequest request) {
        MDC.put (MDCFilter.REQUEST_PATH, request.getRequestURI ());
        MDC.put (MDCFilter.REQUEST_VERB, request.getMethod ());
    }

    @Override
    public void destroy () {

    }

    @Override
    protected void doFilterInternal (final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        this.addRequestInfosIntoMDC (request);
        filterChain.doFilter (request, response);
        this.removeRequestInfosFromMDC (request);
    }

    private void removeRequestInfosFromMDC (final HttpServletRequest request) {
        MDC.remove (MDCFilter.REQUEST_PATH);
        MDC.remove (MDCFilter.REQUEST_VERB);

    }

}
