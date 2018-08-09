package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PidContainer {

    public static String PATH_SHUTDOWN_PID = "./shutdown.pid";

    @Value("{path.shutdown.pid}")
    public void setPathShutdownPid(String pathShutdownPid) {
        PATH_SHUTDOWN_PID = pathShutdownPid;
    }

}
