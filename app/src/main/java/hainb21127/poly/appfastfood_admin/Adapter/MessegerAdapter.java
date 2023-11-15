package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hainb21127.poly.appfastfood_admin.DTO.MessageChat;
import hainb21127.poly.appfastfood_admin.R;

public class MessegerAdapter extends BaseAdapter {
    Context context;
    List<MessageChat> list;

    public MessegerAdapter(Context context, List<MessageChat> arrayList) {
        this.context = context;
        this.list = arrayList;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MessageChat message = list.get(i);
        if (view == null) {
            if(message.getStatus().equals("1")){
                view = mInflater.inflate(R.layout.item_send_mess, null);

                TextView content = view.findViewById(R.id.txtmessend);
                TextView time = view.findViewById(R.id.txttimesend);

                content.setText(message.getContent());
                time.setText(message.getTime());
            }else{
                view = mInflater.inflate(R.layout.item_received, null);

                TextView content = view.findViewById(R.id.txtmessreced);
                TextView time = view.findViewById(R.id.txttimereced);

                content.setText(message.getContent());
                time.setText(message.getTime());
            }
        }
        return view;
    }

}
