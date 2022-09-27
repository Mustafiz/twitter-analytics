package edu.miu.bdt.util;

/**
 * @author Mustafizur Rahman Hilaly
 */
public enum DateFormat {
    DD_MM_YYYY_SLASH("dd/MM/yyyy"),
    MM_DD_YYYY_SLASH("MM/dd/yyyy"),
    YYYY_MM_DD_SLASH("yyyy/MM/dd"),
    YYYY_MM_DD_DASH("yyyy-MM-dd"),
    YYYY_MM_DDTHH_MM_SS_SSS_DASH("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final String value;

    DateFormat(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
