package com.example.messasingchat.home_pages.ui.friend_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Adapaters.FriendsListAdapter;
import com.example.messasingchat.Entity.FriendListEntity;
import com.example.messasingchat.Services.FollowServices.FollowServices;
import com.example.messasingchat.databinding.FragmentFriendsListBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends Fragment {

    private FragmentFriendsListBinding binding;

    private FollowServices followServices;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFriendsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView=binding.friendsListRc;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followServices=new FollowServices(getContext());
//        followServices.getAllFollowedFriends();
        FriendListEntity friendListEntity=new FriendListEntity();
        friendListEntity.setFriendName("ala");
        FriendListEntity friendListEntity1=new FriendListEntity();
        friendListEntity1.setFriendName("ala");
        List<FriendListEntity> friendListEntities=new ArrayList<>();
        friendListEntities.add(friendListEntity);
        friendListEntities.add(friendListEntity1);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(friendListEntities, getContext());
        recyclerView.setAdapter(friendsListAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}