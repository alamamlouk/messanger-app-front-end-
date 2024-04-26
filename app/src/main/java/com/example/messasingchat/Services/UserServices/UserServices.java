package com.example.messasingchat.Services.UserServices;

import android.content.Context;
import android.util.Log;
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
import com.example.messasingchat.Entity.User;
import com.example.messasingchat.Shared.KeyStoreManager;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.home_pages.ui.search.UserResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServices {
    private final Context context;
    String url = BuildConfig.SERVER_URL+"users";

    public UserServices(Context context) {
        this.context = context;
    }
    public void sendFollowRequest(int toFollowId )throws Exception{
        String token=KeyStoreManager.retrieveToken(context);
        String userId= SharedPreferenceManager.getInstance(context).getUserId();
        String newUrl=url+"/"+userId+"/follow/"+toFollowId;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(context, "Request sent", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context,"fail to send Follow Request",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void getUserProfile() throws Exception {
        String token=KeyStoreManager.retrieveToken(context);
        String getUrl=url+"/me";
        StringRequest getRequest = new StringRequest(Request.Method.GET, getUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(getRequest);
   }
    public void getUsersList(final UserResponseListener listener) throws Exception {
        String token=KeyStoreManager.retrieveToken(context);
        String getUrl=url+"/";
        List<User> userList=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userJson = response.getJSONObject(i);
                                User user = new User();
                                user.setUserId(userJson.getInt("id"));
                                user.setFullName(userJson.getString("fullName"));
                                userList.add(user);
                            }
                            userList.removeIf(user -> user.getUserId()==Integer.parseInt(SharedPreferenceManager.getInstance(context).getUserId()));
                            listener.onSuccess(userList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error retrieving users");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public void getPendingRequest(final ResponseListener listener) throws Exception {
        String token=KeyStoreManager.retrieveToken(context);
        String userId=SharedPreferenceManager.getInstance(context).getUserId();
        String getUrl=url+"/pendingRequest/"+userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONArray>() {
                    List<Integer> pendingRequests = new ArrayList<>();
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int requestId = response.getInt(i);
                                pendingRequests.add(requestId);
                            }
                            Log.d("TAG", "onResponse: "+pendingRequests);
                            listener.onSuccess(pendingRequests);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error retrieving users");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
    public void getFriendsId(final ResponseListener listener) throws Exception {
        String token=KeyStoreManager.retrieveToken(context);
        String userId=SharedPreferenceManager.getInstance(context).getUserId();
        String getUrl=url+"/getFriendId/"+userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONArray>() {
                    List<Integer> pendingRequests = new ArrayList<>();

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int requestId = response.getInt(i);
                                pendingRequests.add(requestId);
                            }
                            Log.d("TAG", "onResponse: "+pendingRequests);
                            listener.onSuccess(pendingRequests);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError("Error retrieving users");
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
}
