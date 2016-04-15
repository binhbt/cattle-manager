package com.leo.cattle.domain.interactor;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leo on 4/1/2016.
 */
public class AddCostUseCase extends UseCase {

    private Cost cost;
    private String token;
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public AddCostUseCase(UserRepository userRepository,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.addCost(this.token, this.cost);
    }
    public void passParamForNewCost(String token, Cost cost){
        this.token = token;
        this.cost = cost;
    }
}