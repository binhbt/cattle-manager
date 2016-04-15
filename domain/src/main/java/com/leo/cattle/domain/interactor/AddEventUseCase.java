package com.leo.cattle.domain.interactor;

import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leo on 4/1/2016.
 */
public class AddEventUseCase extends UseCase {

    private Event cost;
    private String token;
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public AddEventUseCase(UserRepository userRepository,
                          ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.addEvent(this.token, this.cost);
    }
    public void passParamForNewEvent(String token, Event cost){
        this.token = token;
        this.cost = cost;
    }
}