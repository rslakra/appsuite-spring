package com.rslakra.appsuite.spring.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Rohtash Lakra
 * @created 3/28/23 12:22 PM
 */

@Getter
@Setter
@NoArgsConstructor
public class Error {

    private int statusCode;
    private String error;
    private String message;
    private Date timestamp;

}
