package com.nivea_be.nivea_ad.enums;

import lombok.Getter;

@Getter
public enum DimensionType {
    DIMENSION_320_480("dimension_320_480"),
    DIMENSION_480_320("dimension_480_320"),
    DIMENSION_300_600("dimension_300_600"),
    DIMENSION_300_250("dimension_300_250");

    private final String value;

    DimensionType(String value) {
        this.value = value;
    }

    public static DimensionType fromString(String text) {
        for (DimensionType d : DimensionType.values()) {
            if (d.value.equalsIgnoreCase(text)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid dimension: " + text);
    }
}

