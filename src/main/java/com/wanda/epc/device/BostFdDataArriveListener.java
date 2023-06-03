package com.wanda.epc.device;

import java.util.EventListener;

/**
 * @author: tangz
 * @since: 2023/2/13 20:03
 * @description:
 */
public interface BostFdDataArriveListener extends EventListener {
    void dataArrive(String paramString);
}
