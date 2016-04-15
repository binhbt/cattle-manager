package com.leo.cattle.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.model.CattleModel;
import com.leo.cattle.presentation.util.DatimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Aidan Follestad (afollestad)
 */
public class CattleDetailAdapter extends SectionedRecyclerViewAdapter<CattleDetailAdapter.MainVH> {
    private CattleModel cattle;

    public CattleModel getCattle() {
        return cattle;
    }

    public void setCattle(CattleModel cattle) {
        this.cattle = cattle;
    }

    @Override
    public int getSectionCount() {
        return 2;
    }

    @Override
    public int getItemCount(int section) {
        if (section  == 0)
            return cattle.getWeights().size();
        return cattle.getEvents().size();
    }

    @Override
    public void onBindHeaderViewHolder(MainVH holderx, int section) {
        MainVH1 holder = (MainVH1)holderx;
        holder.title.setText(String.format(section ==0?"Weights":"Events", section));
    }

    @Override
    public void onBindViewHolder(MainVH holderx, int section, int relativePosition, int absolutePosition) {
        if (!(holderx instanceof  MainVH2)) return;
        MainVH2 holder = (MainVH2)holderx;
        if (section ==0){

            if (cattle.getWeights()!=null)
            holder.title.setText(cattle.getWeights().get(relativePosition).getName());
            holder.des.setText(cattle.getWeights().get(relativePosition).getDesCription());
            holder.weight.setText(cattle.getWeights().get(relativePosition).getWeight() +"kg");
            String dateString = DatimeUtil.fromDate(DatimeUtil.fromString(cattle.getWeights().get(relativePosition).getDate().replace("+0000",""), "yyyy-MM-dd'T'HH:mm:ss"), "yyyy-MM-dd hh:mm:ss");
            holder.date.setText(dateString);
        }else{
            if (cattle.getEvents()!=null)
            holder.title.setText(cattle.getEvents().get(relativePosition).getName());
            holder.des.setText(cattle.getEvents().get(relativePosition).getDesCription());
            holder.weight.setText(cattle.getEvents().get(relativePosition).getCost() +"VND");
            String dateString = DatimeUtil.fromDate(DatimeUtil.fromString(cattle.getEvents().get(relativePosition).getDate().replace("+0000",""), "yyyy-MM-dd'T'HH:mm:ss"), "yyyy-MM-dd hh:mm:ss");
            holder.date.setText(dateString);
        }

    }

    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        if (section == 1)
            return 0; // VIEW_TYPE_HEADER is -2, VIEW_TYPE_ITEM is -1. You can return 0 or greater.
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public MainVH onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.list_item_header;
                View v1 = LayoutInflater.from(parent.getContext())
                        .inflate(layout, parent, false);
                return new MainVH1(v1);
            //break;
            case VIEW_TYPE_ITEM:
                layout = R.layout.list_item_main;
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(layout, parent, false);
                return new MainVH2(v);
        }
        layout = R.layout.list_item_main_bold;
        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new MainVH1(v1);
    }

    public static class MainVH2 extends MainVH {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.des)
        TextView des;
        @Bind(R.id.weight)
        TextView weight;
        @Bind(R.id.date)
        TextView date;
        public MainVH2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public static class MainVH1 extends MainVH {
        @Bind(R.id.title)
        TextView title;
        public MainVH1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public static class MainVH extends RecyclerView.ViewHolder {
        public MainVH(View itemView) {
            super(itemView);
        }
    }
}