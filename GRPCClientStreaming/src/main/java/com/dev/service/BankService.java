package com.dev.service;

import com.dev.s01.AccountBalance;
import com.dev.s01.DepositRequest;
import com.dev.service.handler.DepositRequestHandler;
import io.grpc.stub.StreamObserver;
import com.dev.s01.BankServiceGrpc;

public class BankService  extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public StreamObserver<DepositRequest> deposit(StreamObserver<AccountBalance> responseObserver) {

            return  new DepositRequestHandler(responseObserver);


        /*  return new StreamObserver<DepositRequest >(){

            @Override
            public void onNext(DepositRequest depositRequest) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        }*/
    }
}
