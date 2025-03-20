package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AlertClass enum to choose type of message
 */
@AllArgsConstructor
@Getter
public enum AlertClass {
    ALERT_SUCCESS("alert-success"),
    ALERT_DANGER("alert-danger"),
    ALERT_WARNING("alert-warning");

    private final String value;

}
