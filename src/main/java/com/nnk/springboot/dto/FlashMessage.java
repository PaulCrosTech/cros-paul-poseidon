package com.nnk.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FlashMessage class to display flash message on pages
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlashMessage {

    private AlertClass alertClass = AlertClass.ALERT_DANGER;
    private String message = "An error occurred. Please try again later.";
    
}
