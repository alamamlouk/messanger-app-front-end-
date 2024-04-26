package com.example.messasingchat.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Entity.RequestFriendShip;
import com.example.messasingchat.Services.FollowServices.FollowServices;
import com.example.messasingchat.databinding.FragmentRequestBinding;

import java.util.List;

public class MyrequestsRecyclerViewAdapter extends RecyclerView.Adapter<MyrequestsRecyclerViewAdapter.ViewHolder> {

    private final List<RequestFriendShip> mValues;
    private FollowServices followServices;

    public MyrequestsRecyclerViewAdapter(List<RequestFriendShip> items, Context context) {
        mValues = items;
        followServices=new FollowServices(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRequestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mUserName.setText(mValues.get(position).getUserFullName());
        holder.mUserPhoto.setText(mValues.get(position).getPicture());
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    followServices.acceptFollowRequest(holder.mItem.getRequestFriendShipId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                int itemPosition = holder.getAbsoluteAdapterPosition();
                if (itemPosition != RecyclerView.NO_POSITION) {
                    removeItem(itemPosition);
                }
            }
        });
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int itemPosition = holder.getAbsoluteAdapterPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        removeItem(itemPosition);
                        followServices.rejectFollowRequest(holder.mItem.getRequestFriendShipId());

                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                int itemPosition = holder.getAbsoluteAdapterPosition();
                if (itemPosition != RecyclerView.NO_POSITION) {
                    removeItem(itemPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public void removeItem(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mUserName;
        public final TextView mUserPhoto;
        public final Button acceptButton,rejectButton;
        public RequestFriendShip mItem;

        public ViewHolder(FragmentRequestBinding binding) {
            super(binding.getRoot());
            mUserName = binding.userName;
            mUserPhoto = binding.userPhoto;
            acceptButton=binding.acceptRequest;
            rejectButton=binding.rejectRequest;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUserPhoto.getText() + "'";
        }
    }
}