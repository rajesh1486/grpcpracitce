package com.dev;

import com.dev.server.GrpcServer;
import com.dev.service.BankService;

public class Main {
    public static void main(String[] args) {

        GrpcServer.create(new BankService()).start().await();
    }
}