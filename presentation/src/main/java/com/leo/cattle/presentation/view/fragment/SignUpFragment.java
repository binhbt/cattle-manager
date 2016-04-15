package com.leo.cattle.presentation.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.presenter.SignUpPresenter;
import com.leo.cattle.presentation.view.SignUpMvpView;
import com.leo.cattle.presentation.view.activity.MainActivity;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func4;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

/**
 * Created by leobui on 3/15/2016.
 */
public class SignUpFragment   extends BaseFragment implements SignUpMvpView {
    @Inject
    SignUpPresenter mSignUpPresenter;
    @Bind(R.id.signup_name)
    EditText mName;
    @Bind(R.id.signup_email)
    EditText mEmail;
    @Bind(R.id.signup_password)
    EditText mPassword;
    @Bind(R.id.signup_re_password)
    EditText mRePassword;
    @Bind(R.id.signup_btn_submit)
    Button mSubMit;
    ProgressDialog mProgressBar;


    private Observable<CharSequence> nameChangeObservable;
    private Observable<CharSequence> emailChangeObservable;
    private Observable<CharSequence> passwordChangeObservable;
    private Observable<CharSequence> rePasswordChangeObservable;


    private Subscription subscription = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sign_up,
                null);
        ButterKnife.bind(this, v);

        nameChangeObservable = RxTextView.textChanges(mName).skip(1);
        emailChangeObservable = RxTextView.textChanges(mEmail).skip(1);
        passwordChangeObservable = RxTextView.textChanges(mPassword).skip(1);
        rePasswordChangeObservable = RxTextView.textChanges(mRePassword).skip(1);
        _combineLatestEvents();
        return v;
    }
    private void _combineLatestEvents() {
        subscription = Observable.combineLatest(
                nameChangeObservable,
                emailChangeObservable,
                passwordChangeObservable,
                rePasswordChangeObservable,
                new Func4<CharSequence, CharSequence, CharSequence,CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence newname,
                                        CharSequence newEmail,
                                        CharSequence newPassword,
                                        CharSequence newRePassWord) {
                        boolean nameValid = (!isEmpty(newname))&&newname.length()>=4;
                        if (!nameValid) {
                            mName.setError(getString(R.string.name_err));
                        }
                        boolean emailValid = !isEmpty(newEmail) &&
                                EMAIL_ADDRESS.matcher(newEmail).matches();
                        if (!emailValid) {
                            mEmail.setError(getString(R.string.email_err));
                        }

                        boolean passValid = !isEmpty(newPassword) && newPassword.length() > 5;
                        if (!passValid) {
                            mPassword.setError(getString(R.string.pass_err));
                        }

                        boolean rePassValid = newRePassWord.equals(newPassword)?true:false;
                        if (!rePassValid) {
                            mRePassword.setError(getString(R.string.pass_not_match));
                        }

                        return nameValid&& emailValid && passValid;

                    }
                })//
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "there was an error");
                    }

                    @Override
                    public void onNext(Boolean formValid) {
                        if (formValid) {
                            mSubMit.setEnabled(true);
                        } else {
                            mSubMit.setEnabled(false);
                        }
                    }
                });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
        mSignUpPresenter.setView(this);
    }
    @OnClick(R.id.signup_btn_submit)
    public void onSubMit(){
        this.mSignUpPresenter.initialize(mName.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString());
    }
    @Override public void onDestroy() {
        super.onDestroy();

    }

    @Override public void onDetach() {
        super.onDetach();

    }

    @Override public void showLoading() {
        mProgressBar = new ProgressDialog(getActivity());
        mProgressBar.setCancelable(true);
        mProgressBar.setMessage("Loading");
        mProgressBar.show();
    }

    @Override public void hideLoading() {
        mProgressBar.dismiss();
    }

    @Override public void showRetry() {

    }

    @Override public void hideRetry() {

    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }
    @Override public Context context() {
        return getActivity().getApplicationContext();
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void signupSuccess(UserModel userModel) {
        if (userModel.getId() == 0){
            this.showToastMessage("Sign up Failed ");
        }else{
            this.showToastMessage("Sign up success ");
            AndroidApplication.sessionUser = userModel;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            //Bundle b = new Bundle();
            //b.putString("username", username); //Your id
            //intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            getActivity().finish();
        }
    }

}