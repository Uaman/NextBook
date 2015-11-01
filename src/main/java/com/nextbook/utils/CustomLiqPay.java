package com.nextbook.utils;

import com.liqpay.LiqPay;
import org.springframework.stereotype.Component;

/**
 * Created by Polomani on 18.08.2015.
 */
public class CustomLiqPay extends LiqPay {

    private String privateKey;

    public CustomLiqPay(String publicKey, String privateKey) {
        super(publicKey, privateKey);
        this.privateKey = privateKey;
    }

    public String str_to_sign(String str) {
        return super.str_to_sign(str);
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
