package com.aci.jd2015.engine;

/**
 * This class represents a "calculator" for check sums.
 *
 * @author zvyagintsev
 */
public interface CheckSumCalculator {

    /**
     * Calculate check sum.
     *
     * @param data source data
     * @return result data
     */
    String calculate(String data);

}
