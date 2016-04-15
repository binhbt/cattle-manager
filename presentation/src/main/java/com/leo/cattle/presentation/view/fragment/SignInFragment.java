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

import com.facebook.CallbackManager;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.model.UserModel;
import com.leo.cattle.presentation.presenter.SignInPresenter;
import com.leo.cattle.presentation.view.SignInMvpView;
import com.leo.cattle.presentation.view.activity.MainActivity;
import com.leo.cattle.presentation.view.activity.SignUpActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by leobui on 3/15/2016.
 */
public class SignInFragment extends BaseFragment implements SignInMvpView {
    @Inject
    SignInPresenter mSignInPresenter;

    @Bind(R.id.btn_submit)
    Button mSubmit;
    @Bind(R.id.login_et_username)
    EditText mName;
    @Bind(R.id.login_et_password)
    EditText mPassword;
    private ProgressDialog mProgressBar;

    private CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sign_in,
                null);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
        mSignInPresenter.setView(this);
        loadSession();
    }

    @OnClick(R.id.btn_submit)
    public void goSignIn(){

        mSignInPresenter.initialize(mName.getText().toString(), mPassword.getText().toString());
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
    public void signInSuccess(UserModel userModel) {

        if (userModel.getId() == 0){
            this.showToastMessage("Sign In Failed " );
        }else{
            this.showToastMessage("Sign In success " + userModel.getFullName());
            AndroidApplication.sessionUser = userModel;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            //Bundle b = new Bundle();
            //b.putString("username", username); //Your id
            //intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            getActivity().finish();
        }

    }
    private void loadSession() {
        this.mSignInPresenter.getSession();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @OnClick(R.id.txt_register)
    public void goRegister(){
        Intent intent = new Intent(getActivity(), SignUpActivity.class);
        //Bundle b = new Bundle();
        //b.putString("username", username); //Your id
        //intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        getActivity().finish();
        //((CatalystMvpView)getActivity()).replaceFragment(new SignUpFragment());
    }
    @Override
    public void moveToHome(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        //Bundle b = new Bundle();
        //b.putString("username", username); //Your id
        //intent.putExtras(b); //Put your id to your next Intent
        //For GCM
        Bundle b = getArguments();
        if (b!=null){
            intent.putExtras(b);
        }
        startActivity(intent);
        getActivity().finish();
    }
}
