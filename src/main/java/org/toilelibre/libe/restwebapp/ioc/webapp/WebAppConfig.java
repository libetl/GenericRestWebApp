package org.toilelibre.libe.restwebapp.ioc.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.toilelibre.libe.restwebapp.actions.LinkHelper;
import org.toilelibre.libe.restwebapp.actions.LinkLister;
import org.toilelibre.libe.restwebapp.ioc.BusinessLogicConfig;

@EnableWebMvc
@ComponentScan (basePackages = "org.toilelibre.libe.restwebapp")
public class WebAppConfig extends BusinessLogicConfig {

    @Bean
    public LinkHelper linkHelper () {
        return new LinkHelper ();
    }

    @Bean
    public LinkLister linkLister () {
        return new LinkLister ();
    }
}
