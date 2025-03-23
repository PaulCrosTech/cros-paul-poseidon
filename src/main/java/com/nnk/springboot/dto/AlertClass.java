package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AlertClass enum to choose type of message
 */
@AllArgsConstructor
@Getter
public enum AlertClass {
    /**
     * Alert success alert class.
     */
    ALERT_SUCCESS("alert-success"),
    /**
     * Alert info alert class.
     */
    ALERT_DANGER("alert-danger"),
    /**
     * Alert warning alert class.
     */
    ALERT_WARNING("alert-warning");

    private final String value;

}
