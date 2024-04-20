package com.example.messasingchat.Services.UserServices;

import android.content.Context;

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

    public void getUserRequest() throws Exception {
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
        String getUrl=url+"/profiles";
        List<User> userList=new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> users = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userJson = response.getJSONObject(i);
                                // Parse user data and create User objects
                                User user = new User();
                                user.setUserId(userJson.getInt("id"));
                                user.setFullName(userJson.getString("fullName"));
                                users.add(user);
                            }
                            // Pass the list of users to the listener
                            listener.onSuccess(users);
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
        );

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }
}
