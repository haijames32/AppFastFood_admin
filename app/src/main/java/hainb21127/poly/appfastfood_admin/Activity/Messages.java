package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import hainb21127.poly.appfastfood_admin.Adapter.MessAdapter;
import hainb21127.poly.appfastfood_admin.Adapter.MessegerAdapter;
import hainb21127.poly.appfastfood_admin.DTO.MessageChat;
import hainb21127.poly.appfastfood_admin.R;

public class Messages extends AppCompatActivity {
    private ListView rcvChat;
//    ListView rcvChat;
    MessegerAdapter messAdapter;
    private List<MessageChat> mChat;
//    MessegerAdapter adapter;
    TextView chatName;
    Context context;
    ImageView btnBack, avatarChat, btnSend;
    private String currentUserId;
    EditText edChat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        rcvChat = findViewById(R.id.rcv_chatMess);
        edChat = findViewById(R.id.edtinputtext);
//        rcvChat.setLayoutManager(new LinearLayoutManager(this));
        mChat = new ArrayList<>();
        FirebaseUser curUSer = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = curUSer.getUid();
//        adapter = new TestAdapter(mChat,getApplicationContext(),currentUserId);
        messAdapter = new MessegerAdapter(getApplicationContext(),mChat);
//        adapter = new MessegerAdapter(getApplicationContext(),mChat);
        chatName = findViewById(R.id.nameChatMess);
        btnBack = findViewById(R.id.btn_back_chat);
        avatarChat = findViewById(R.id.avaterChatMess);
        btnSend = findViewById(R.id.sendChat);
        Intent intent = getIntent();
        String IdChat = intent.getStringExtra("IdChat");
        String IdUser = intent.getStringExtra("IdUserChat");
        String textName = intent.getStringExtra("nameUserChat");
        String imagChat = intent.getStringExtra("imageUserChat");

        chatName.setText(textName);
        Picasso.get().load(imagChat).into(avatarChat);
        getChatData(IdChat);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = edChat.getText().toString().trim(); // Lấy nội dung tin nhắn từ EditText hoặc TextView tương ứng;


                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference messageRef = database1.getReference("messages").push();


                MessageChat chat = new MessageChat();
                chat.setContent(messageText);
                chat.setTime(getCurrentTime());
                chat.setStatus("1");
//                chat.setSendId(IdChat);
//                chat.setReceivedid(IdUser);

                messageRef.setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference reference = messageRef.child("id_chat");
                            DatabaseReference reference1 = reference.child(IdChat);
                            DatabaseReference reference2 = reference1.child("id_user");
                            DatabaseReference reference3 = reference2.child(IdUser);
                            DatabaseReference reference4 = reference3.child("fullname");
                            reference4.setValue(textName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        edChat.setText("");
                                        Log.d("TAG", "onComplete: " + task.toString());
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    private void getChatData(String idChat) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatabaseReference reference1 = dataSnapshot.child("id_chat").getRef();

                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                String Id = dataSnapshot1.getKey();
                                if (Id.equalsIgnoreCase(idChat)) {
                                    MessageChat chat = new MessageChat();
                                    chat.setId(Id);
                                    chat.setContent(dataSnapshot.child("content").getValue(String.class));
                                    chat.setStatus(dataSnapshot.child("status").getValue(String.class));
                                    chat.setTime(dataSnapshot.child("time").getValue(String.class));

                                    mChat.add(chat);
                                    Log.d("ZZZZ", "onDataChange: " + dataSnapshot.child("content").getValue(String.class));
                                    Log.d("ZZZZ", "onDataChange: " + dataSnapshot.child("time").getValue(String.class));
                                    Log.d("ZZZZ", "onDataChange: " + Id);

                                }

                            }

                            rcvChat.setAdapter(messAdapter);
                            messAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }
}