package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hainb21127.poly.appfastfood_admin.Activity.Messages;
import hainb21127.poly.appfastfood_admin.DTO.Chat;
import hainb21127.poly.appfastfood_admin.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    List<Chat> mMessa;
    Context context;

    public ChatAdapter(List<Chat> mMessa, Context context) {
        this.mMessa = mMessa;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = mMessa.get(position);
        if (mMessa == null){
            return;
        }
        holder.nameChat.setText(chat.getId_user().getFullname());
        Picasso.get().load(chat.getId_user().getImage()).into(holder.avatarChat);
        Log.d("Chat", "onBindViewHolder: " + chat.getId_user().getFullname());
        Log.d("Chat2", "onBindViewHolder: " + chat.getId_user().getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Messages.class);
                intent.putExtra("IdChat",chat.getId());
                intent.putExtra("IdUserChat",chat.getId_user().getId());
                intent.putExtra("nameUserChat",chat.getId_user().getFullname());
                intent.putExtra("imageUserChat",chat.getId_user().getImage());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMessa != null){
            return mMessa.size();
        }
        return 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarChat;
        TextView nameChat, content,timestamp;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarChat = itemView.findViewById(R.id.imageChat);
            nameChat = itemView.findViewById(R.id.nameChat);
        }
    }
}
