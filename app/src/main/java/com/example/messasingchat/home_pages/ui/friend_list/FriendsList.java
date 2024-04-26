package com.example.messasingchat.home_pages.ui.friend_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Adapaters.FriendsListAdapter;
import com.example.messasingchat.Entity.FriendListEntity;
import com.example.messasingchat.Entity.User;
import com.example.messasingchat.Services.FollowServices.FollowServices;
import com.example.messasingchat.databinding.FragmentFriendsListBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends Fragment {

    private FragmentFriendsListBinding binding;

    private FollowServices followServices;
    private List<FriendListEntity> friendListEntities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFriendsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView recyclerView=binding.friendsListRc;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followServices=new FollowServices(getContext());
        try {
            followServices.getFriends(new ListFriendResponse() {
                @Override
                public void onSuccess(List<User> userList) {
                    friendListEntities=new ArrayList<>();
                        for(User user:userList){
                            FriendListEntity f=new FriendListEntity();
                            f.setFriendName(user.getFullName());
                            friendListEntities.add(f);
                        }
                    FriendsListAdapter friendsListAdapter = new FriendsListAdapter(friendListEntities, getContext());
                    recyclerView.setAdapter(friendsListAdapter);
                }

                @Override
                public void onError(String message) {

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