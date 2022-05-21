package com.netinfosz.smartivr.interfaces;

/**
 * 业务层核心接口工厂类，具体注入由业务层实现。
 */
public class SipCoreFactory {

    private static SipCore sipCore;

    public static void initSipCore(SipCore sipCore) {
        SipCoreFactory.sipCore = sipCore;
    }

    public static SipCore getSipCore() {
        return sipCore;
    }
}
