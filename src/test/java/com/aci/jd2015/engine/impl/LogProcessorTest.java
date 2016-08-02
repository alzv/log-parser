package com.aci.jd2015.engine.impl;

import com.aci.jd2015.engine.CheckSumCalculator;
import com.aci.jd2015.engine.DataProvider;

import com.aci.jd2015.model.LogMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LogProcessorTest {

    DataProvider dataProviderMock1;

    DataProvider dataProviderMock2;

    DataProvider dataProviderMock3;

    CheckSumCalculator calculatorMock;

    @Before
    public void initialize() throws Exception {
        dataProviderMock1 = new DataProvider() {
            @Override
            public List<String> getDateRows() {
                String[] data = {"28.03.2015 01:31:25.025 Test1"};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getNonDateRows() {
                String[] data = {};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getCheckSums() {
                String[] data = {"CRC_28.03.2015 01:31:25.025 Test1"};
                return Arrays.asList(data);
            }
        };

        dataProviderMock2 = new DataProvider() {
            @Override
            public List<String> getDateRows() {
                String[] data = {"28.03.2015 01:31:25.025 Test1"};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getNonDateRows() {
                String[] data = {"Test2", "Test3"};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getCheckSums() {
                String[] data = {"CRC_28.03.2015 01:31:25.025 Test1Test2Test3"};
                return Arrays.asList(data);
            }
        };

        dataProviderMock3 = new DataProvider() {
            @Override
            public List<String> getDateRows() {
                String[] data = {
                        "28.03.2015 01:31:25.025 Test1",
                        "28.03.2015 01:30:25.025 Test4"};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getNonDateRows() {
                String[] data = {
                        "Test2",
                        "Test5",
                        "Test6",
                        "Test3"};
                return Arrays.asList(data);
            }

            @Override
            public List<String> getCheckSums() {
                String[] data = {
                        "CRC_28.03.2015 01:31:25.025 Test1Test2Test3",
                        "CRC_28.03.2015 01:30:25.025 Test4Test5Test6"};
                return Arrays.asList(data);
            }
        };

        calculatorMock = new CheckSumCalculator() {
            @Override
            public String calculate(String data) {
                return "CRC_" + data;
            }
        };
    }

    @Test
    public void testProcess() throws Exception {
        LogProcessor logProcessor = new LogProcessor(dataProviderMock1, calculatorMock);
        List<LogMessage> results = logProcessor.process();

        Assert.assertEquals(1, results.size());

        logProcessor = new LogProcessor(dataProviderMock2, calculatorMock);
        results = logProcessor.process();

        Assert.assertEquals(1, results.size());

        logProcessor = new LogProcessor(dataProviderMock3, calculatorMock);
        results = logProcessor.process();

        Assert.assertEquals(2, results.size());
    }
}