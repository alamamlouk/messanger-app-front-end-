package com.example.messasingchat.Adapaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Entity.ChatMessage;
import com.example.messasingchat.databinding.ItemContainerRecivedMessageBinding;
import com.example.messasingchat.databinding.ItemContainerSendMessageBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ChatMessage> chatMessageList;
    private final int senderId;
    public static final int VIEW_TYPE_SENT=1;
    public static final int VIEW_TYPE_RECEIVED=2;
    public ChatAdapter(List<ChatMessage> chatMessageList, int senderId) {
        this.chatMessageList = chatMessageList;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_SENT){
            return new SendMessageViewHolder(
                    ItemContainerSendMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
            );
        }else {
            return new ReceivedMessageViewHolder(
                    ItemContainerRecivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TYPE_SENT){
            ((SendMessageViewHolder)holder).setData(chatMessageList.get(position));
        }else {
            ((ReceivedMessageViewHolder)holder).setDate(chatMessageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).getSenderId()==senderId){
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SendMessageViewHolder extends RecyclerView.ViewHolder{
        private  final ItemContainerSendMessageBinding binding;
        SendMessageViewHolder(ItemContainerSendMessageBinding itemContainerSendMessageBinding){
            super(itemContainerSendMessageBinding.getRoot());
            binding=itemContainerSendMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.getTextMessage());
            binding.dateTime.setText(chatMessage.getSendTime());
        }
    }
    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerRecivedMessageBinding binding;
        ReceivedMessageViewHolder(ItemContainerRecivedMessageBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
        void setDate(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.getTextMessage());
            binding.dateTime.setText(chatMessage.getSendTime());
        }
    }
}
