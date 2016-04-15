package com.leo.cattle.domain.interactor;


import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leobui on 3/9/2016.
 */
public class GetSessionUseCase extends UseCase {
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public GetSessionUseCase(com.leo.cattle.domain.repository.UserRepository userRepository,
                          com.leo.cattle.domain.executor.ThreadExecutor threadExecutor, com.leo.cattle.domain.executor.PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.getLoggedUser();
    }
}
