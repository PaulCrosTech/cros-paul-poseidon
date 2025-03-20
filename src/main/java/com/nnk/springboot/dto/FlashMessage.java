package com.nnk.springboot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FlashMessage class to display flash message on pages
 */
@AllArgsConstructor
@Data
public class FlashMessage {

    private AlertClass alertClass;
    private String message;

}
