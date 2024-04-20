package com.example.messasingchat.home_pages.ui.home;

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
import com.example.messasingchat.Services.UserServices.UserServices;
import com.example.messasingchat.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FriendsMessagesAdapter friendsMessagesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FriendsMessageListEntity friendsMessageListEntity =new FriendsMessageListEntity();
        friendsMessageListEntity.setLastMessageSend("hii");
        friendsMessageListEntity.setUserName("ali");
        FriendsMessageListEntity friendsMessageListEntity1 =new FriendsMessageListEntity();
        friendsMessageListEntity1.setLastMessageSend("hiiiii");
        friendsMessageListEntity1.setUserName("ala");
        List<FriendsMessageListEntity>friendListEntities=new ArrayList<>();
        friendListEntities.add(friendsMessageListEntity);
        friendListEntities.add(friendsMessageListEntity1);
        friendsMessagesAdapter = new FriendsMessagesAdapter(friendListEntities,getContext());
        recyclerView.setAdapter(friendsMessagesAdapter);
        UserServices userServices=new UserServices(getContext());
        try {
            userServices.getUserRequest();
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