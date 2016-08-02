package com.aci.jd2015;

import com.aci.jd2015.engine.CheckSumCalculator;
import com.aci.jd2015.engine.DataProvider;
import com.aci.jd2015.engine.Processor;
import com.aci.jd2015.engine.impl.ISDataProvider;
import com.aci.jd2015.engine.impl.LogProcessor;
import com.aci.jd2015.engine.impl.LogCheckSumCalculator;
import com.aci.jd2015.model.LogMessage;

import org.apache.commons.io.IOUtils;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.*;

/**
 * <code>GreedyLogParser</code> provides the main method for parsing logs.
 *
 * @author zvyagintsev
 */
public class GreedyLogParser implements LogParser {

    @Override
    public void process(InputStream is, OutputStream os) throws IOException {

        DataProvider dataProvider = new ISDataProvider(is);
        CheckSumCalculator checkSumCalculator = new LogCheckSumCalculator();

        Processor<LogMessage> processor = new LogProcessor(dataProvider, checkSumCalculator);
        List<LogMessage> logMessages = processor.process();

        Collections.sort(logMessages);

        for (LogMessage logMessage : logMessages) {
            IOUtils.writeLines(logMessage.getRows(), null, os, UTF_8);
        }
    }
}
