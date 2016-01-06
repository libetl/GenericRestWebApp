package org.toilelibre.libe.restwebapp.model.calculation;

public class SimpleCalculator implements Calculator {

    @Override
    public int sum (final int int1, final int int2) {
        return int1 + int2;
    }

}
