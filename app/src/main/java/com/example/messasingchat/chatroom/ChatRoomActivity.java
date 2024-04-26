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

import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    private int theReceiverId;
    private int chatRoomId;
    private ChatRoomServices chatRoomServices;
    private ChatAdapter chatAdapter;

    private List<ChatMessage> chatMessagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatRoomServices=new ChatRoomServices(getApplicationContext());
        Intent intent=getIntent();
        theReceiverId=intent.getIntExtra("theReceiverId",0);
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
                            chatMessagesList.add(chatMessage);
                            chatAdapter.notifyDataSetChanged();
                            binding.chatRecycleView.scrollToPosition(chatMessagesList.size() - 1);
                            binding.message.setText("");
                            binding.message.clearFocus();

                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
}