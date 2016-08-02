package com.aci.jd2015.engine.impl;

import com.aci.jd2015.engine.CheckSumCalculator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This class implements a check sum calculator for a specific log format: "CRS_" + {md5 hash}>.
 * Example: "CRC_098f6bcd4621d373cade4e832627b4f6"
 *
 * @author zvyagintsev
 */
public class LogCheckSumCalculator implements CheckSumCalculator {

    public static final String CHECKSUM_MESSAGE_PREFIX = "CRC_";

    @Override
    public String calculate(String data) {
        if (StringUtils.isEmpty(data)) {
            return data;
        }

        return CHECKSUM_MESSAGE_PREFIX + DigestUtils.md5Hex(data);
    }
}
