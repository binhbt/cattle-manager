package com.leo.cattle.domain.interactor;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leobui on 3/16/2016.
 */
public class GetSessionListUseCase extends UseCase {
    private String accessToken;
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public GetSessionListUseCase(com.leo.cattle.domain.repository.UserRepository userRepository, com.leo.cattle.domain.executor.ThreadExecutor threadExecutor,
                       com.leo.cattle.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.userRepository.sessions(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
