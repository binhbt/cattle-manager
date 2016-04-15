package com.leo.cattle.domain.interactor;

import com.leo.cattle.domain.Cattle;
import com.leo.cattle.domain.executor.PostExecutionThread;
import com.leo.cattle.domain.executor.ThreadExecutor;
import com.leo.cattle.domain.repository.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by leo on 4/1/2016.
 */
public class AddCattleUseCase extends UseCase {

    private Cattle cattle;
    private String token;
    private final com.leo.cattle.domain.repository.UserRepository userRepository;

    @Inject
    public AddCattleUseCase(UserRepository userRepository,
                         ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override protected Observable buildUseCaseObservable() {
        return this.userRepository.addCattle(this.token, this.cattle);
    }
    public void passParamForNewCattle(String token, Cattle cattle){
        this.token = token;
        this.cattle = cattle;
    }
}