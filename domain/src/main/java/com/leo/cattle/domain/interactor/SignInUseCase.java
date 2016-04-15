package com.leo.cattle.domain.interactor;


import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leobui on 3/9/2016.
 */
public class SignInUseCase extends UseCase {

    private  String userEmail;
    private  String userPassword;
    private final UserRepository userRepository;

    @Inject
    public SignInUseCase(UserRepository userRepository,
                         com.leo.cattle.domain.executor.ThreadExecutor threadExecutor, com.leo.cattle.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.askForSignIn(this.userEmail, this.userPassword);
    }
    public void passParamForSignIn(String userEmail, String userPassword){
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}