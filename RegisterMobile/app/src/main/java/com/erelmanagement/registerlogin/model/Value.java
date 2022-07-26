/*-----------------------------------------------------------------------------
 - Developed by erwin                                                -
 - Last modified 6/16/19 2:50 PM                                              -
 - Subscribe : https://www.youtube.com/erwin                         -
 - Copyright (c) 2019. All rights reserved                                    -
 -----------------------------------------------------------------------------*/
package com.erelmanagement.registerlogin.model;

public class Value {
    String value;
    String message;

    public void setValue(String value) {
        this.value = value;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue(){
        return value;
    }

    public String getMessage(){
        return message;
    }
}
