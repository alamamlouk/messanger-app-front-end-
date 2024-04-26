package com.example.messasingchat.Services.FollowServices;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.messasingchat.BuildConfig;
import com.example.messasingchat.Entity.RequestFriendShip;
import com.example.messasingchat.Entity.User;
import com.example.messasingchat.Shared.KeyStoreManager;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.home_pages.ui.friend_list.FriendsResponseListener;
import com.example.messasingchat.home_pages.ui.friend_list.ListFriendResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowServices {
    private final Context context;
    String url= BuildConfig.SERVER_URL+"api/follows/";


    public FollowServices(Context context) {
        this.context = context;
    }
    public void acceptFollowRequest(String followId)throws Exception{
        String token= KeyStoreManager.retrieveToken(context);
        StringRequest request=new StringRequest(Request.Method.PUT, url  + followId + "/accept", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
    public void rejectFollowRequest(String followId)throws Exception{
        String token= KeyStoreManager.retrieveToken(context);
        StringRequest request=new StringRequest(Request.Method.DELETE, url  + followId + "/reject", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    public void getPendingRequests(FriendsResponseListener friendsResponseListener)throws Exception{
        String token= KeyStoreManager.retrieveToken(context);
        String userId= SharedPreferenceManager.getInstance(context).getUserId();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url+"users/"+userId+"/pending-followers",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<RequestFriendShip> users = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userJson = response.getJSONObject(i);
                                RequestFriendShip requestFriendShip=new RequestFriendShip();
                                requestFriendShip.setRequestFriendShipId(userJson.getString("followId"));
                                requestFriendShip.setUserFullName(userJson.getString("fullName"));

                                users.add(requestFriendShip);
                            }
                            friendsResponseListener.onSuccess(users);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            friendsResponseListener.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        friendsResponseListener.onError("Error retrieving users");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
    public void getFriends(ListFriendResponse friendsResponseListener)throws Exception{
        String token= KeyStoreManager.retrieveToken(context);
        String userId= SharedPreferenceManager.getInstance(context).getUserId();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url+userId+"/friends",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> users = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                    JSONObject userJson = response.getJSONObject(i);
                                User requestFriendShip=new User();
                                requestFriendShip.setUserId(userJson.getInt("friendId"));
                                requestFriendShip.setFullName(userJson.getString("friendFullName"));
                                users.add(requestFriendShip);
                            }
                            friendsResponseListener.onSuccess(users);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            friendsResponseListener.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        friendsResponseListener.onError("Error retrieving users");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }



}
