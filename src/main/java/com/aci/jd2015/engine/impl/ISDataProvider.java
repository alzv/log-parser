package com.aci.jd2015.engine.impl;

import com.aci.jd2015.engine.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This class provides a structured data from InputStream for log processors.
 *
 * @author zvyagintsev
 */
public class ISDataProvider implements DataProvider {

    private List<String> dateRows = new ArrayList<>();
    private List<String> nonDateRows = new ArrayList<>();
    private List<String> checkSums = new ArrayList<>();

    public ISDataProvider(InputStream is) {
        final int maxCharBufferSize = 1024;
        try (
                BufferedReader readerResult = new BufferedReader(
                        new InputStreamReader(is, UTF_8), maxCharBufferSize)) {
            final String dateRowRegex = "^\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}.*$";
            while (true) {
                String row = readerResult.readLine();

                if (null == row) {
                    break;
                }

                if (row.startsWith(LogCheckSumCalculator.CHECKSUM_MESSAGE_PREFIX)) {
                    checkSums.add(row);
                } else if (row.matches(dateRowRegex)) {
                    dateRows.add(row);
                } else {
                    nonDateRows.add(row);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // simple validation
        if (dateRows.size() == 0 || checkSums.size() == 0 || dateRows.size() != checkSums.size())
            throw new IllegalArgumentException("Incorrect log file format!");
    }

    @Override
    public List<String> getDateRows() {
        return dateRows;
    }

    @Override
    public List<String> getNonDateRows() {
        return nonDateRows;
    }

    @Override
    public List<String> getCheckSums() {
        return checkSums;
    }
}
