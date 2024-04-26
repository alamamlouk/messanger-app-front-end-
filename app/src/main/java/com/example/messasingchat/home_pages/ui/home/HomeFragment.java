package com.example.messasingchat.home_pages.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Adapaters.FriendsMessagesAdapter;
import com.example.messasingchat.Entity.FriendsMessageListEntity;
import com.example.messasingchat.Entity.OnItemListMessageClick;
import com.example.messasingchat.Services.ChatRoomServices;
import com.example.messasingchat.Services.UserServices.ChatListResponseListener;
import com.example.messasingchat.chatroom.ChatRoomActivity;
import com.example.messasingchat.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FriendsMessagesAdapter friendsMessagesAdapter;
    private ChatRoomServices chatRoomServices;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatRoomServices=new ChatRoomServices(getContext());
        try {
            this.chatRoomServices.getUsersListMessages(new ChatListResponseListener() {
                @Override
                public void onSuccess(List<FriendsMessageListEntity> messageListEntities) {
                    friendsMessagesAdapter = new FriendsMessagesAdapter(messageListEntities, getContext(), new OnItemListMessageClick() {
                        @Override
                        public void onItemClick(int id,int chatRoomId) {
                            Intent intent = new Intent(getContext(), ChatRoomActivity.class);
                            intent.putExtra("theReceiverId",id);
                            intent.putExtra("RoomId",chatRoomId);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(friendsMessagesAdapter);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}