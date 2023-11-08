package hainb21127.poly.appfastfood_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hainb21127.poly.appfastfood_admin.R;

public class ChatBot extends AppCompatActivity {

    ImageView btnBackChat;
    RecyclerView rcvChat;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        btnBackChat = findViewById(R.id.btnPrevChat);
        rcvChat = findViewById(R.id.rcvChatBot);

        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rcvChat.setLayoutManager(manager);
        btnBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}