package com.netinfosz.smartivr.interfaces;

import java.util.Map;

/**
 * 业务层的核心接口
 */
public interface SipCore {

    Map<String, Object> service(Map<String, Object> request);

}
