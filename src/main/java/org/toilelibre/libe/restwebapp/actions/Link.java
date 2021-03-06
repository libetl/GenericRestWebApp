package org.toilelibre.libe.restwebapp.actions;

import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Link {

    public static class Param {
        @JacksonXmlElementWrapper (useWrapping = false)
        private final String binding;
        @JacksonXmlElementWrapper (useWrapping = false)
        private final String type;

        public Param (final String binding, final String type) {
            super ();
            this.binding = binding;
            this.type = type;
        }

        public String getBinding () {
            return this.binding;
        }

        public String getType () {
            return this.type;
        }

    }

    @JacksonXmlProperty (namespace = "xsi", isAttribute = true)
    private final String           rel;
    @JacksonXmlElementWrapper (useWrapping = false)
    private final String           href;
    @JacksonXmlElementWrapper (useWrapping = false)
    private final RequestMethod [] methods;
    @JacksonXmlElementWrapper (useWrapping = false)
    private final Param []         params;

    public Link (final String rel1, final String href1, final RequestMethod [] methods1, final Param [] params) {
        this.rel = rel1;
        this.href = href1;
        this.methods = methods1;
        this.params = params;
    }

    public String getHref () {
        return this.href;
    }

    public RequestMethod [] getMethods () {
        return this.methods.clone ();
    }

    public Param [] getParams () {
        return this.params;
    }

    public String getRel () {
        return this.rel;
    }

    @Override
    public String toString () {
        final StringBuffer stringBuffer = new StringBuffer ();
        for (final RequestMethod method : this.methods) {
            if (stringBuffer.length () > 0) {
                stringBuffer.append ('\n');
            }
            stringBuffer.append (method.name ()).append (' ').append (this.href).append (' ');
        }
        return stringBuffer.append ("(" + this.rel + ")").toString ();
    }
}
