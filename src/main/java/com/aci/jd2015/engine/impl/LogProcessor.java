package com.aci.jd2015.engine.impl;

import com.aci.jd2015.engine.DataProvider;
import com.aci.jd2015.engine.CheckSumCalculator;
import com.aci.jd2015.engine.Processor;
import com.aci.jd2015.model.LogMessage;

import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements business logic for log parsing.
 *
 * The implementation based on <a href="http://en.wikipedia.org/wiki/Combination">
 * combinations</a> {@code (n, k)} of {@code k} elements in a set of
 * {@code n} elements and some simple heuristics.
 *
 * @author zvyagintsev
 */
public class LogProcessor implements Processor<LogMessage> {

    private static final int NOT_FOUND = -1;

    private DataProvider dataProvider;
    private CheckSumCalculator checkSumCalculator;

    public LogProcessor(DataProvider dataProvider, CheckSumCalculator checkSumCalculator) {
        this.dataProvider = dataProvider;
        this.checkSumCalculator = checkSumCalculator;
    }

    @Override
    public List<LogMessage> process() {
        List<LogMessage> logMessages = new ArrayList<>(dataProvider.getDateRows().size());

        // Size of the set from which subsets are selected
        int n = dataProvider.getNonDateRows().size();
        // Size of the subsets to be enumerated.
        int k = n / dataProvider.getDateRows().size();

        List<String> checkSums = new ArrayList<>(dataProvider.getCheckSums());
        for (String dateMessage : dataProvider.getDateRows()) {
            Combinations combinations = new Combinations(n, k);
            for (int[] combination : combinations) {
                String combinationLogMessage = getCombinationLogMessage(dateMessage, combination);
                String checkSum = checkSumCalculator.calculate(combinationLogMessage);

                int index = findCheckSum(checkSums, checkSum);
                if (index != NOT_FOUND) {
                    logMessages.add(new LogMessage.Builder(
                            dateMessage,
                            combination,
                            dataProvider.getNonDateRows(),
                            checkSum
                    ).build());
                    checkSums.remove(index);

                    break; // go to the next first message with date in log
                }
            }
        }

        if (logMessages.size() != dataProvider.getDateRows().size())
            throw new IllegalStateException("Unable to parse log file, please check format");

        return logMessages;
    }

    private String getCombinationLogMessage(String dateMessage, int[] combination) {
        StringBuilder msgBuilder = new StringBuilder(dateMessage);
        for (int i : combination) {
            msgBuilder.append(dataProvider.getNonDateRows().get(i));
        }
        return msgBuilder.toString();
    }

    private int findCheckSum(List<String> checkSums, String calculatedCheckSum) {
        for (int i = 0; i < checkSums.size(); i++) {
            String checkSum = checkSums.get(i);
            if (checkSum.equals(calculatedCheckSum)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

}
