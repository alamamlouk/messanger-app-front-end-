package com.example.messasingchat.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.messasingchat.BuildConfig;
import com.example.messasingchat.Entity.ChatMessage;
import com.example.messasingchat.Entity.FriendsMessageListEntity;
import com.example.messasingchat.Entity.SendMessageListener;
import com.example.messasingchat.Services.UserServices.ChatListResponseListener;
import com.example.messasingchat.Shared.KeyStoreManager;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.chatroom.MessageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomServices {
    private final Context context;
    private final String url= BuildConfig.SERVER_URL+"api/messages";

    public ChatRoomServices(Context context) {
        this.context = context;
    }
    public void getUsersListMessages(ChatListResponseListener listener)throws Exception {
        String token= KeyStoreManager.retrieveToken(context);
        String userId= SharedPreferenceManager.getInstance(context).getUserId();
        String newUrl=url+"/getChatRooms/"+userId;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, newUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        List<FriendsMessageListEntity> userList=new ArrayList<>();
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userJson = jsonArray.getJSONObject(i);
                                FriendsMessageListEntity user = new FriendsMessageListEntity();
                                user.setId(userJson.getInt("id"));
                                user.setUserName(userJson.getString("userName"));
                                user.setTimeSend(userJson.getString("date"));
                                user.setLastMessageSend(userJson.getString("content"));
                                user.setIdTheTalker(userJson.getInt("idTheTalker"));
                                userList.add(user);
                            }
                            listener.onSuccess(userList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onFailure("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("TAG", "onErrorResponse: "+volleyError.getMessage());
                    }
                }){
            @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + token);
            return headers;}

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
    public void getChatRoomMessages(MessageListener listener,int chatRoomId)throws Exception {
        String token= KeyStoreManager.retrieveToken(context);
        String newUrl=url+"/chat-room/"+chatRoomId+"/messages";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, newUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        List<ChatMessage> chatMessages=new ArrayList<>();
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userJson = jsonArray.getJSONObject(i);
                                ChatMessage chatMessage=new ChatMessage();
                                chatMessage.setTextMessage(userJson.getString("message"));
                                chatMessage.setSenderId(userJson.getInt("senderId"));
                                chatMessage.setReceiverId(userJson.getInt("receiverId"));
                                chatMessage.setSendTime(userJson.getString("timeSent"));
                                chatMessages.add(chatMessage);
                            }
                            listener.onSuccess(chatMessages);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("TAG", "onErrorResponse: "+volleyError.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;}

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
    public void sendMessage(int chatRoomId, int receiverId, String content, SendMessageListener sendMessageListener)throws Exception {
        String token= KeyStoreManager.retrieveToken(context);
        String newUrl=url+"/send";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("chatRoomId", chatRoomId);
            jsonBody.put("message", content);
            jsonBody.put("senderId",  Integer.parseInt(SharedPreferenceManager.getInstance(context).getUserId()));
            jsonBody.put("receiverId", receiverId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, newUrl,jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
              ChatMessage chatMessage=new ChatMessage();
              try {
                  chatMessage.setSendTime(jsonObject.getString("createdAt"));
                  chatMessage.setTextMessage(jsonObject.getString("content"));
                  chatMessage.setSenderId(Integer.parseInt(SharedPreferenceManager.getInstance(context).getUserId()));
                  chatMessage.setReceiverId(receiverId);
                  sendMessageListener.onSuccess(chatMessage);

              }catch (Exception e)
              {
                  e.printStackTrace();
              }
                          }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error", "onErrorResponse: "+volleyError);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;}


        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

}
