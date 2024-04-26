package com.example.messasingchat.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Entity.FriendsMessageListEntity;
import com.example.messasingchat.Entity.OnItemListMessageClick;
import com.example.messasingchat.R;

import java.util.List;
public class FriendsMessagesAdapter extends RecyclerView.Adapter<FriendsMessagesAdapter.FriendsMessageViewHolder> {
    private List<FriendsMessageListEntity> friendsMessageListEntities;
    private Context context;
    private OnItemListMessageClick onItemListMessageClick;

    public FriendsMessagesAdapter(List<FriendsMessageListEntity> friendsMessageListEntities, Context context,OnItemListMessageClick listener) {
        this.friendsMessageListEntities = friendsMessageListEntities;
        this.context = context;
        this.onItemListMessageClick=listener;
    }

    @NonNull
    @Override
    public FriendsMessagesAdapter.FriendsMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_message_item_recycle_view, parent, false);
        return new FriendsMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsMessagesAdapter.FriendsMessageViewHolder holder, int position) {
        FriendsMessageListEntity friendsMessageListEntity =this.friendsMessageListEntities.get(position);
        holder.friendName.setText(friendsMessageListEntity.getUserName());
        holder.friendLastMessage.setText(friendsMessageListEntity.getLastMessageSend());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListMessageClick.onItemClick(friendsMessageListEntity.getIdTheTalker(),friendsMessageListEntity.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.friendsMessageListEntities.size();
    }
    public static class FriendsMessageViewHolder extends RecyclerView.ViewHolder {
        TextView friendName,friendLastMessage,lastMessageTimeSend;
        ImageView friendImage;


        public FriendsMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.friendName = itemView.findViewById(R.id.friendName) ;
            this.friendLastMessage = itemView.findViewById(R.id.lastfriendMessage) ;
            this.lastMessageTimeSend = itemView.findViewById(R.id.friendMessageTimeSent) ;
            this.friendImage = itemView.findViewById(R.id.friendImg) ;
        }
    }
}
