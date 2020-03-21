package com.nin.xloyalty.util;

public enum InterestedField
{
    _1("Special Offers"),
    _2("Food"),
    _3("Drinks"),
    _4("Phone Cards");
 
    private String stringValue;
 
    InterestedField(String envstringValue) {
        this.stringValue = envstringValue;
    }
 
    public String getstringValue() {
        return stringValue;
    }    
    
}
