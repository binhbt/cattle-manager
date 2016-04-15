package com.leo.cattle.domain.interactor;


import com.leo.cattle.domain.Weight;
import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leo on 4/1/2016.
 */
public class AddWeightUseCase extends UseCase {

    private Weight cost;
    private String token;
    private final UserRepository userRepository;

    @Inject
    public AddWeightUseCase(UserRepository userRepository,
                           ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.addWeight(this.token, this.cost);
    }
    public void passParamForNewWeight(String token, Weight cost){
        this.token = token;
        this.cost = cost;
    }
}