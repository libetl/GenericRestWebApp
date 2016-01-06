package org.toilelibre.libe.restwebapp.ioc;

import org.toilelibre.libe.restwebapp.model.calculation.Calculator;

interface AppConfig {

    Calculator getCalculator ();

}
