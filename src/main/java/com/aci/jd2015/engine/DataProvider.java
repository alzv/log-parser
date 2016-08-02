package com.aci.jd2015.engine;

import java.util.List;

/**
 * This class represents a "provider" for the log processors implementations.
 *
 * @author zvyagintsev
 */
public interface DataProvider {

    /**
     * @return a list with first rows with dates.
     */
    List<String> getDateRows();

    /**
     * @return a list with simple rows without dates and without check sums.
     */
    List<String> getNonDateRows();

    /**
     * @return a list with check sums rows.
     */
    List<String> getCheckSums();

}
