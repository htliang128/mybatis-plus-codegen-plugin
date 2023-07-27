package com.htliang.db.mybatisplus.codegen.utils;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLServerSocketFactory;
import java.net.ServerSocket;
import java.security.SecureRandom;

@Slf4j
public class AvailablePort {
    public static int get() {
        SecureRandom random = new SecureRandom();
        int port = 60606;

        while (isPortOccupied(port)) {
            port = random.nextInt(60000) + 2000;
        }

        return port;
    }

    private static boolean isPortOccupied(int port) {
        try {
            ServerSocket socket = SSLServerSocketFactory
                .getDefault()
                .createServerSocket(port);
            socket.close();
            return false;
        } catch (Exception exception) {
            log.error("port {} is in use, message: {}", port, exception.getMessage());
            return true;
        }
    }
}
