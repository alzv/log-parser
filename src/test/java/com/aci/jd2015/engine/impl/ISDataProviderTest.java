package com.aci.jd2015.engine.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ISDataProviderTest {

    ISDataProvider dataProvider;

    @Before
    public void initialize() throws Exception {
        InputStream is = ISDataProviderTest.class.getResourceAsStream("/testInputExample.log");
        dataProvider = new ISDataProvider(is);
    }

    @Test
    public void testGetDateRows() throws Exception {
        Assert.assertEquals(4, dataProvider.getDateRows().size());
    }

    @Test
    public void testGetNonDateRows() throws Exception {
        Assert.assertEquals(12, dataProvider.getNonDateRows().size());
    }

    @Test
    public void testGetCheckSums() throws Exception {
        Assert.assertEquals(4, dataProvider.getCheckSums().size());
    }
}