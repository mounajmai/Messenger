package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.messenger.model.ChatMessageModel;
import com.example.messenger.model.ChatroomModel;
import com.example.messenger.model.UserModel;
import com.example.messenger.utils.AndroidUtil;
import com.example.messenger.utils.firebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
    UserModel otherUser;
    String chatroomId;
    ChatroomModel chatroomModel;

    EditText messaageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get UserModel
        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId = firebaseUtil.getChatroomId(firebaseUtil.currentUserId(),otherUser.getUserId());
        messaageInput = findViewById(R.id.chat_message_input);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        backBtn = findViewById(R.id.back_btn);
        otherUsername = findViewById(R.id.other_username);
        recyclerView =findViewById(R.id.chat_recycle_view);

        backBtn.setOnClickListener((v->{
            onBackPressed();

        }));
        otherUsername.setText(otherUser.getUsername());

        sendMessageBtn.setOnClickListener((v->{
            String message = messaageInput.getText().toString().trim();
            if(message.isEmpty())
                return;
            sendMessageToUser(message);
        }));
        getOrCreateChatroomModel();
    }

    void sendMessageToUser(String message) {
        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(firebaseUtil.currentUserId());
        firebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);



        ChatMessageModel chatMessageModel= new ChatMessageModel(message,firebaseUtil.currentUserId(),Timestamp.now());
        firebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            messaageInput.setText("");
                        }

                    }
                });

    }

    void getOrCreateChatroomModel(){
        firebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatroomModel = task.getResult().toObject(ChatroomModel.class);
                if(chatroomModel==null){
                    //first time out
                    chatroomModel = new ChatroomModel(
                            chatroomId,
                            Arrays.asList(firebaseUtil.currentUserId(),otherUser.getUserId()),
                            Timestamp.now(),
                            ""
                    );
                    firebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);
                }

            }
        });
    }

}