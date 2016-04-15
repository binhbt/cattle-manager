package com.leo.cattle.domain.interactor;


import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leobui on 3/9/2016.
 */
public class SignUpUseCase extends UseCase {

    private  String userName;
    private  String userEmail;
    private  String userPassword;
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public SignUpUseCase(com.leo.cattle.domain.repository.UserRepository userRepository,
                          com.leo.cattle.domain.executor.ThreadExecutor threadExecutor, com.leo.cattle.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.askForSignup(this.userName, this.userEmail, this.userPassword);
    }
    public void passParamForSignup(String userName, String userEmail, String userPassword){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}