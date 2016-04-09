package com.thyago.complexserviceui;

/**
 * Created by thyago on 4/8/16.
 */
public class SmsServiceResult {
    private final boolean result;

    public SmsServiceResult(boolean result) {
        this.result = result;
    }

    public boolean result() {
        return result;
    }
}
