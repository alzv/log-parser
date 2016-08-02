package com.aci.jd2015.engine.impl;

import org.junit.Assert;
import org.junit.Test;

public class LogCheckSumCalculatorTest {

    @Test
    public void testCalculate() throws Exception {
        LogCheckSumCalculator calculator = new LogCheckSumCalculator();

        Assert.assertTrue("CRC_098f6bcd4621d373cade4e832627b4f6".equals(calculator.calculate("test")));

        Assert.assertNull(calculator.calculate(null));

        Assert.assertTrue("".equals(calculator.calculate("")));
    }
}