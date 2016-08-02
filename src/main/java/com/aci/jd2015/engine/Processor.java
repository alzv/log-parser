package com.aci.jd2015.engine;

import java.util.List;

/**
 * This interface is used to implement a main business logic for a log parsers.
 *
 * @param <T> parameterized model for operation results
 *
 * @author zvyagintsev
 */
public interface Processor<T> {

    List<T> process();

}
