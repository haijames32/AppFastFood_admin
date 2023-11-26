package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.ChatAdapter;
import hainb21127.poly.appfastfood_admin.DTO.Chat;
import hainb21127.poly.appfastfood_admin.DTO.KhachHang;
import hainb21127.poly.appfastfood_admin.R;

public class ChatBot extends AppCompatActivity {

    ImageView btnBackChat;
    RecyclerView rcvChat;
    Context context;
    List<Chat> mChat;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        btnBackChat = findViewById(R.id.btnPrevChat);
        rcvChat = findViewById(R.id.rcvChatBot);
        context = this;
        mChat = new ArrayList<>();
        chatAdapter = new ChatAdapter(mChat, context);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcvChat.setLayoutManager(manager);

        getDataMessage();
        btnBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getDataMessage() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("chats");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang kh = new KhachHang();
                    DatabaseReference reference1 = dataSnapshot.child("id_user").getRef();
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot3 : snapshot.getChildren()) {
                                kh.setId(dataSnapshot3.getKey());
                                kh.setFullname(dataSnapshot3.child("fullname").getValue(String.class));
                                kh.setImage(dataSnapshot3.child("image").getValue(String.class));

                                Chat chat = new Chat();
                                chat.setId(dataSnapshot.getKey());
                                chat.setId_user(kh);
                                mChat.add(chat);
                            }
                            rcvChat.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                Log.d("TAG", "onDataChange: " + mChat.size());
                Toast.makeText(ChatBot.this, "Load danh sách thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}