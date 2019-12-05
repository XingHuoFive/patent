package com.sxp.patMag.enums;

public enum NumberEnum {
    ONE("1"),
    ZERO("0");

    private String value;

    NumberEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
