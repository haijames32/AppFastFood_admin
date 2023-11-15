package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hainb21127.poly.appfastfood_admin.DTO.MessageChat;
import hainb21127.poly.appfastfood_admin.R;

public class MessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<MessageChat> messageChatList;
    String sendid;
    private static final int TYPE_SEND = 1;
    private static final int TYPE_RECEIVE = 2;

    public MessAdapter( List<MessageChat> messageChatList, String sendid) {
        this.messageChatList = messageChatList;
        this.sendid = sendid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SEND){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_mess,parent,false);
            return new MessViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received,parent,false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_SEND){
            ((MessViewHolder) holder).txtmess.setText(messageChatList.get(position).content);
            ((MessViewHolder) holder).txttime.setText(messageChatList.get(position).time);
        }else {
            ((ReceivedViewHolder) holder).txtmessr.setText(messageChatList.get(position).content);
            ((ReceivedViewHolder) holder).txttimer.setText(messageChatList.get(position).time);
        }

    }

    @Override
    public int getItemCount() {
        return messageChatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (sendid != null && sendid.equals(messageChatList.get(position).sendId)) {
            return TYPE_SEND;
        } else {
            return TYPE_RECEIVE;
        }
//
    }

    public class MessViewHolder extends RecyclerView.ViewHolder {
        TextView txtmess, txttime;

        public MessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmess = itemView.findViewById(R.id.txtmessend);
            txttime = itemView.findViewById(R.id.txttimesend);
        }
    }
    public class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView txtmessr, txttimer;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmessr = itemView.findViewById(R.id.txtmessreced);
            txttimer = itemView.findViewById(R.id.txttimereced);
        }
    }
}
