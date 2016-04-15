package com.leo.cattle.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leo.cattle.presentation.R;
import com.leo.cattle.presentation.model.CattleModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by leo on 4/1/2016.
 */
public class CattleAdapter extends RecyclerView.Adapter<CattleAdapter.UserViewHolder> {

    public interface OnItemClickListener {
        void onUserItemClicked(CattleModel userModel);
    }

    private List<CattleModel> usersCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public CattleAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usersCollection = Collections.emptyList();
    }

    @Override public int getItemCount() {
        return (this.usersCollection != null) ? this.usersCollection.size() : 0;
    }

    @Override public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.item_cattle, parent, false);
        return new UserViewHolder(view);
    }

    @Override public void onBindViewHolder(UserViewHolder holder, final int position) {
        final CattleModel cattleModel = this.usersCollection.get(position);
        holder.textViewTitle.setText(cattleModel.getName());
        holder.des.setText(cattleModel.getDesCription());
        holder.weight.setText((float)(cattleModel.getWeight()/10) +"kg - "+cattleModel.getMonthOld()+"month");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (CattleAdapter.this.onItemClickListener != null) {
                    CattleAdapter.this.onItemClickListener.onUserItemClicked(cattleModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setUsersCollection(Collection<CattleModel> usersCollection) {
        this.validateUsersCollection(usersCollection);
        this.usersCollection = (List<CattleModel>) usersCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateUsersCollection(Collection<CattleModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.title)
        TextView textViewTitle;
        @Bind(R.id.des)
        TextView des;
        @Bind(R.id.weight)
        TextView weight;
        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
