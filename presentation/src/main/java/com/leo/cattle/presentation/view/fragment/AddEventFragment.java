package com.leo.cattle.presentation.view.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.leo.cattle.domain.Cost;
import com.leo.cattle.domain.Event;
import com.leo.cattle.presentation.AndroidApplication;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.internal.di.components.UserComponent;
import com.leo.cattle.presentation.model.CostModel;
import com.leo.cattle.presentation.model.EventModel;
import com.leo.cattle.presentation.presenter.AddCostPresenter;
import com.leo.cattle.presentation.presenter.AddEventPresenter;
import com.leo.cattle.presentation.view.AddCostMvpView;
import com.leo.cattle.presentation.view.AddEventMvpView;

import java.util.Calendar;

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

/**
 * Created by leo on 3/25/2016.
 */
public class AddEventFragment  extends BaseFragment implements AddEventMvpView {
    @Inject
    AddEventPresenter mAddCattlePresenter;
    ProgressDialog mProgressBar;
    @Bind(R.id.name)
    EditText mName;
    @Bind(R.id.des)
    EditText mDes;
    @Bind(R.id.cost)
    EditText mCost;
    @Bind(R.id.date)
    EditText mDate;
    @Bind(R.id.cattles)
    EditText mCattles;
    @Bind(R.id.add_new)
    Button mSubmit;
    private Observable<CharSequence> nameChangeObservable;
    private Observable<CharSequence> desChangeObservable;
    private Observable<CharSequence> dateChangeObservable;
    private Observable<CharSequence> costChangeObservable;
    private Subscription subscription = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_event,
                null);
        ButterKnife.bind(this, v);
        nameChangeObservable = RxTextView.textChanges(mName).skip(1);
        desChangeObservable = RxTextView.textChanges(mDes).skip(1);
        dateChangeObservable = RxTextView.textChanges(mDate).skip(1);
        costChangeObservable = RxTextView.textChanges(mCost).skip(1);
        _combineLatestEvents();
        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.cattles);
        String[] bloods = getResources().getStringArray(R.array.cattle_bloods);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, bloods);
        textView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
        mAddCattlePresenter.setView(this);
    }
    private void _combineLatestEvents() {
        subscription = Observable.combineLatest(
                nameChangeObservable,
                desChangeObservable,
                dateChangeObservable,
                costChangeObservable,
                new Func4<CharSequence, CharSequence, CharSequence,CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence newname,
                                        CharSequence newDes,
                                        CharSequence newDate,
                                        CharSequence newCost) {
                        boolean nameValid = (!isEmpty(newname));
                        if (!nameValid) {
                            mName.setError("not empty");
                        }
                        boolean emailValid = !isEmpty(newDes);
                        if (!emailValid) {
                            mDes.setError("not empty");
                        }

                        boolean passValid = !isEmpty(newDate);
                        if (!passValid) {
                            mDate.setError("not empty");
                        }

                        boolean rePassValid = !isEmpty(newCost);
                        if (!rePassValid) {
                            mCost.setError("not empty");
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
                            mSubmit.setEnabled(true);
                        } else {
                            mSubmit.setEnabled(false);
                        }
                    }
                });
    }


    @OnClick(R.id.btn_show_date)
    public void showDateTime() {
// Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        android.text.format.DateFormat df = new android.text.format.DateFormat();
                        mDate.setText(df.format("yyyy-MM-dd hh:mm:ss", new java.util.Date(year -1900, monthOfYear, dayOfMonth, 0, 0, 0)));


                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
    @OnClick(R.id.add_new)
    public void onSubMit(){
        Event cattle = new Event();
        cattle.setName(mName.getText().toString());
        cattle.setDesCription(mDes.getText().toString());
        cattle.setCost(Integer.parseInt(mCost.getText().toString()));
        cattle.setDate(mDate.getText().toString());
        cattle.setCattleIds(mCattles.getText().toString());
        cattle.setAuthorId(AndroidApplication.sessionUser.getId());
        this.mAddCattlePresenter.initialize(AndroidApplication.sessionUser.getAccessToken(), cattle);
        Log.e("getBuyDate", cattle.getDate());
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
    public void newEventSuccess(EventModel cattleModel) {
        if (cattleModel.getId() == 0){
            this.showToastMessage("Add event Failed ");
        }else{
            this.showToastMessage("Add event success ");

            getActivity().finish();
        }
    }
}
