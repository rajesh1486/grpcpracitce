package com.dev.service.handler;

import com.dev.repo.AccountRepository;
import com.dev.s01.DepositRequest;
import io.grpc.stub.StreamObserver;
import com.dev.s01.AccountBalance;

public class DepositRequestHandler implements StreamObserver<DepositRequest> {

    private final StreamObserver<AccountBalance> responseObserver;
    private int accountNumber;

    public DepositRequestHandler(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }
    @Override
    public void onNext(DepositRequest depositRequest) {
        switch (depositRequest.getRequestCase()) {
            case ACCOUNT_NUMBER -> this.accountNumber= depositRequest.getAccountNumber();
            case MONEY -> AccountRepository.addAmount(this.accountNumber, depositRequest.getMoney().getAmount());
        }
    }

    @Override
    public void onError(Throwable throwable) { //this method is invoked if client cancels
            //rollback
        System.out.println("Request stopped"+throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        //commit
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }
}
