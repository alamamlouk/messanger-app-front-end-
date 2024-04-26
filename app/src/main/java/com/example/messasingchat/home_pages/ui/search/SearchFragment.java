package com.example.messasingchat.home_pages.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Adapaters.SearchForFriendsRecyclerViewAdapter;
import com.example.messasingchat.Entity.User;
import com.example.messasingchat.Services.UserServices.ResponseListener;
import com.example.messasingchat.Services.UserServices.UserServices;
import com.example.messasingchat.databinding.FragmentSearchUsersBinding;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchUsersBinding binding;
    private UserServices userServices;

    private List<User> userList;
    private List<Integer>integerList;
    private List<Integer> friendsId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userServices=new UserServices(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView=binding.SearchForFiendRcList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            userServices.getPendingRequest(new ResponseListener() {
                @Override
                public void onSuccess(List<Integer> list) {
                    integerList=list;
                }

                @Override
                public void onError(String message) {

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            userServices.getFriendsId(new ResponseListener() {
                @Override
                public void onSuccess(List<Integer> list) {
                    friendsId=list;
                }

                @Override
                public void onError(String message) {

                }
            });

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        try {
            this.userServices.getUsersList(new UserResponseListener() {
                @Override
                public void onSuccess(List<User> users) {
                    userList=users;
                    recyclerView.setAdapter(new SearchForFriendsRecyclerViewAdapter(userList,getContext(),integerList,friendsId));
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return root;
    }

}