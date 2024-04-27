package com.example.messasingchat.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messasingchat.Adapaters.ChatAdapter;
import com.example.messasingchat.Entity.ChatMessage;
import com.example.messasingchat.Entity.SendMessageListener;
import com.example.messasingchat.Services.ChatRoomServices;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.databinding.ActivityChatRoomBinding;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatRoomActivity extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    private int theReceiverId;
    private int chatRoomId;
    private ChatRoomServices chatRoomServices;
    private ChatAdapter chatAdapter;

    private List<ChatMessage> chatMessagesList;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatRoomServices=new ChatRoomServices(getApplicationContext());
        Intent intent=getIntent();
        theReceiverId=intent.getIntExtra("theReceiverId",0);
        try {
            socket = IO.socket("http://192.168.100.16:3000");
            socket.connect();



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        chatRoomId=intent.getIntExtra("RoomId",0);

        try {
            chatRoomServices.getChatRoomMessages(new MessageListener() {
                @Override
                public void onSuccess(List<ChatMessage> chatMessages) {
                    chatMessagesList=chatMessages;
                    chatAdapter=new ChatAdapter(chatMessagesList, Integer.parseInt(SharedPreferenceManager.getInstance(getApplicationContext()).getUserId()));
                    binding.chatRecycleView.setAdapter(chatAdapter);
                }
            },chatRoomId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=binding.message.getText().toString();
                try {
                    chatRoomServices.sendMessage(chatRoomId, theReceiverId, content, new SendMessageListener() {
                        @Override
                        public void onSuccess(ChatMessage chatMessage) {
                            binding.chatRecycleView.scrollToPosition(chatMessagesList.size() - 1);
                            binding.message.setText("");
                            binding.message.clearFocus();
                            Gson gson = new Gson();
                            String jsonData = gson.toJson(chatMessage);
                            socket.emit("message", jsonData);


                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length > 0) {
                    String jsonData = args[0].toString();
                    handleReceivedData(jsonData);
                }
            }
        });

    }

    private void handleReceivedData(String jsonData) {
        Gson gson = new Gson();
        ChatMessage dataObject = gson.fromJson(jsonData, ChatMessage.class);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!chatMessagesList.contains(dataObject)) {
                    chatMessagesList.add(dataObject);
                    chatAdapter.notifyDataSetChanged();
                }

                binding.chatRecycleView.scrollToPosition(chatMessagesList.size() - 1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}