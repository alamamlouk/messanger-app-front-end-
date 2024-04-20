package com.example.messasingchat.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Entity.FriendListEntity;
import com.example.messasingchat.R;

import org.w3c.dom.Text;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsListViewHolder> {
    private List<FriendListEntity> friendListEntities;
    private Context context;

    public FriendsListAdapter(List<FriendListEntity> friendListEntities, Context context) {
        this.friendListEntities = friendListEntities;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item_list,parent,false);
        return new FriendsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListViewHolder holder, int position) {
        FriendListEntity friendListEntity=this.friendListEntities.get(position);
        holder.friendName.setText(friendListEntity.getFriendName());
    }

    @Override
    public int getItemCount() {
        return this.friendListEntities.size();
    }

    public static class FriendsListViewHolder extends RecyclerView.ViewHolder
    {
        TextView friendName;
        ImageView friendImage;

        public FriendsListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.friendName = itemView.findViewById(R.id.FriendName);
            this.friendImage = itemView.findViewById(R.id.userImage);
        }
    }
}
