package org.toilelibre.libe.restwebapp.model.calculation;

public class SimpleCalculator implements Calculator {

    @Override
    public int sum (int int1, int int2) {
        return int1 + int2;
    }

}
