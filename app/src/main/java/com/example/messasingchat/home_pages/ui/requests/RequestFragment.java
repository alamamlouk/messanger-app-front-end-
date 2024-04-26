package com.example.messasingchat.home_pages.ui.requests;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messasingchat.Adapaters.MyrequestsRecyclerViewAdapter;
import com.example.messasingchat.Entity.RequestFriendShip;
import com.example.messasingchat.R;
import com.example.messasingchat.Services.FollowServices.FollowServices;
import com.example.messasingchat.home_pages.ui.friend_list.FriendsResponseListener;

import java.util.List;


public class RequestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private FollowServices followServices;

    private int mColumnCount = 1;

    private List<RequestFriendShip>userList;
    public RequestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RequestFragment newInstance(int columnCount) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        this.followServices=new FollowServices(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            try {
                this.followServices.getPendingRequests(new FriendsResponseListener() {
                    @Override
                    public void onSuccess(List<RequestFriendShip> users) {
                        userList=users;
                        recyclerView.setAdapter(new MyrequestsRecyclerViewAdapter(userList,context));

                    }

                    @Override
                    public void onError(String message) {
                        Log.d("TAG", "onError: "+message);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return view;
    }
}