package com.aci.jd2015.model;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The class <code>LogMessage</code> represents a one log message.
 *
 * @author zvyagintsev
 */
public class LogMessage implements Comparable<LogMessage> {
    private final Date date;
    private final List<String> rows;

    private LogMessage(Date date, List<String> rows) {
        this.date = date;
        this.rows = rows;
    }

    public List<String> getRows() {
        return rows;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(LogMessage o) {
        return this.getDate().compareTo(o.getDate());
    }

    public static class Builder {

        private Date date;
        private List<String> rows= new ArrayList<>();

        public Builder(String dateMessage, int[] combination, List<String> records, String checkSum) {
            rows.add(dateMessage);
            for (int i : combination) {
                rows.add(records.get(i));
            }
            rows.add(checkSum);

            try {
                final String dateFormat = "dd.MM.yyyy HH:mm:ss.SSS";
                date = DateUtils.parseDate(dateMessage.substring(0, dateFormat.length()), dateFormat);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public LogMessage build() {
            return new LogMessage(date, rows);
        }

    }
}
