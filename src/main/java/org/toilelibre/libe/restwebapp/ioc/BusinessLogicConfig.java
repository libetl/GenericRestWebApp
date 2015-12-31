package org.toilelibre.libe.restwebapp.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.toilelibre.libe.restwebapp.model.calculation.Calculator;
import org.toilelibre.libe.restwebapp.model.calculation.SimpleCalculator;

@Configuration
public class BusinessLogicConfig implements AppConfig {

    @Override
    @Bean
    public Calculator getCalculator () {
        return new SimpleCalculator ();
    }


}
