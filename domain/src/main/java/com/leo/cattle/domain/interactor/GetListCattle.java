package com.leo.cattle.domain.interactor;

import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leo on 4/1/2016.
 */
public class GetListCattle extends UseCase {
    private String accessToken;
    private final UserRepository userRepository;

    @Inject
    public GetListCattle(UserRepository userRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.userRepository.cattles(accessToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}