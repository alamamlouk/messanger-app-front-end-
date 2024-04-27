package com.example.messasingchat.Services.AuthServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.messasingchat.Authentication.AuthCallback;
import com.example.messasingchat.BuildConfig;
import com.example.messasingchat.DTO.LoginDTO;
import com.example.messasingchat.DTO.SignUpDTO;
import com.example.messasingchat.Shared.ErrorHandler;
import com.example.messasingchat.Shared.KeyStoreManager;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthServices {
    private final Context context;
    String getUrl= BuildConfig.SERVER_URL+"auth";
    public AuthServices(Context context) {
        this.context = context;
    }


    public void loginRequest(String email, String password, final AuthCallback callback) {
        String postUrl = getUrl + "/login";
        JSONObject requestBody = null;
        Gson gson = new Gson();
        LoginDTO loginDTO = new LoginDTO(email, password);
        String jsonRequestBody = gson.toJson(loginDTO);
        try {
            requestBody = new JSONObject(jsonRequestBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("token");
                            try {
                                KeyStoreManager.storeToken(context, token);
                                SharedPreferenceManager.getInstance(context).saveId(response.getString("userId"));
                                SharedPreferenceManager.getInstance(context).saveEmail(email);

                                callback.onSuccess();
                            } catch (Exception e) {
                                callback.onFailure("Error storing token");
                            }
                        } catch (JSONException e) {
                            callback.onFailure("Error parsing response");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                            callback.onFailure(ErrorHandler.getError(error));
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }
    public void SignUpRequest(String email,String password,String name,final AuthCallback callback){
        String postUrl = getUrl + "/signup";
        JSONObject requestBody = null;
        Gson gson = new Gson();
        SignUpDTO signUpDTO = new SignUpDTO(email, password,name);
        String jsonRequestBody = gson.toJson(signUpDTO);
        try {
            requestBody = new JSONObject(jsonRequestBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.length()>0){
                                callback.onSuccess();
                            }
                        } catch (Exception e) {
                            callback.onFailure("Error creating an Account");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorHandler.getError(error);
                        callback.onFailure("Error in parsing");
                        Log.d("Error", "onErrorResponse: "+error);

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }



}
