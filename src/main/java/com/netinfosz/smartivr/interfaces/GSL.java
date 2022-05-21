package com.netinfosz.smartivr.interfaces;

import java.util.Map;

/**
 *
 *  smartIVR的核心服务接口
 */
public interface GSL {


    Map<String, Object> service(Map<String, Object> request);
}
