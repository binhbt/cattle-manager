package com.leo.cattle.presentation.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.view.activity.AddCattleActivity;
import com.leo.cattle.presentation.view.activity.AddCostActivity;
import com.leo.cattle.presentation.view.activity.AddEventActivity;
import com.leo.cattle.presentation.view.activity.AddWeightActivity;
import com.leo.cattle.presentation.view.activity.CattleChartActivity;
import com.leo.cattle.presentation.view.adapter.CattleDetailAdapter;
import com.leo.cattle.presentation.view.dummy.DummyContent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by leo on 3/25/2016.
 */
public class CattleDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private CattleModel cattle;
    @Bind(R.id.item_detail)
    TextView mDes;
    @Bind(R.id.item_weight)
    TextView mWeight;
    @Bind(R.id.list)
    RecyclerView list;
    public CattleDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Gson gson = new Gson();
            cattle = gson.fromJson(getArguments().getString(ARG_ITEM_ID), CattleModel.class);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(cattle.getName());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cattle_detail, container, false);
        ButterKnife.bind(this, rootView);
        loadCattleDetail();
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }
        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) rootView.findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddWeightActivity.class));
            }
        });
        final View actionB = rootView.findViewById(R.id.action_b);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddEventActivity.class));
            }
        });
        final View actionC = rootView.findViewById(R.id.action_c);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CattleChartActivity.class));
            }
        });
        return rootView;
    }
    private void loadCattleDetail(){
        mDes.setText(cattle.getDesCription());
        mWeight.setText(cattle.getWeight()+"kg - "+cattle.getMonthOld()+"month");

        CattleDetailAdapter adapter = new CattleDetailAdapter();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        list.setLayoutManager(manager);
        adapter.setLayoutManager(manager);
        adapter.setCattle(cattle);
        list.setAdapter(adapter);
    }
}
