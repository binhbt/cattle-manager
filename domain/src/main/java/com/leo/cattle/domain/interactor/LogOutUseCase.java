package com.leo.cattle.domain.interactor;



import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leobui on 3/9/2016.
 */
public class LogOutUseCase extends UseCase {

    private final UserRepository userRepository;
    private String tokenKey;
    @Inject
    public LogOutUseCase(UserRepository userRepository,
                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.askForLogout(tokenKey);
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }
}