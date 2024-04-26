package com.example.messasingchat.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Entity.User;
import com.example.messasingchat.Services.UserServices.UserServices;
import com.example.messasingchat.databinding.SearchForFriendItemBinding;

import java.util.List;

public class SearchForFriendsRecyclerViewAdapter extends RecyclerView.Adapter<SearchForFriendsRecyclerViewAdapter.ViewHolder> {
    private final List<User> userList;
    private final UserServices userServices;
    private final List<Integer> integerList;


    public SearchForFriendsRecyclerViewAdapter(List<User> userList, Context context,List<Integer> integerList,List<Integer> friendsId) {
        this.userList = userList;
        this.userServices=new UserServices(context);
        this.integerList=integerList;
        containsAny( friendsId);
    }
    public void containsAny(List<Integer> list2) {
        if(!list2.isEmpty()){
            this.userList.removeIf(num -> list2.contains(num.getUserId()));

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SearchForFriendItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItem=userList.get(position);
        if(isInArray(integerList,holder.mItem.getUserId()))
        {
            holder.followButton.setText("Pending");
            holder.followButton.setEnabled(false);
        }
        holder.mUserName.setText(userList.get(position).getFullName());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userServices.sendFollowRequest(holder.mItem.getUserId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                holder.followButton.setText("Pending");
                holder.followButton.setEnabled(false);
            }
        });
    }
    public boolean isInArray(List<Integer> array, int number) {
        for (int j : array) {
            if (j == number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mUserName;
        public final TextView mUserPhoto;
        public final Button followButton;
        public User mItem;

        public ViewHolder(SearchForFriendItemBinding binding) {
            super(binding.getRoot());
            this.mUserName = binding.searchUserName;
            this.mUserPhoto = binding.searchUserPhoto;
            this.followButton = binding.btnSendFriendRequest;

        }
    }
}
