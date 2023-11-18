package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hainb21127.poly.appfastfood_admin.Activity.OrderDetail;
import hainb21127.poly.appfastfood_admin.DTO.Order;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class ThongKeAdapter extends BaseAdapter {

    Context context;
    List<Order> mOrder;

    public ThongKeAdapter(Context context, List<Order> mOrder) {
        this.context = context;
        this.mOrder = mOrder;
    }

    @Override
    public int getCount() {
        if (mOrder != null)
            return mOrder.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mOrder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //quan
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.item_thongke, null);
        }
        Order order = mOrder.get(i);
        TextView status = view.findViewById(R.id.TkeStatus);
        TextView date = view.findViewById(R.id.TkeDate);
        TextView name = view.findViewById(R.id.TkeName);
        TextView total = view.findViewById(R.id.TkeTotal);
        ImageView avater = view.findViewById(R.id.imgTke);

        if (order.getStatus().equalsIgnoreCase("Đã giao hàng")) {
            status.setText(order.getStatus());
            date.setText(order.getDate());
            name.setText(order.getId_user().getFullname());
            total.setText(Utilities.addDots(order.getTong()));
            Picasso.get().load(order.getId_user().getImage()).into(avater);
        } else {
            // Trả về một View trống để ẩn item
            view = new View(context);
            view.setVisibility(view.GONE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.getContext().startActivity(new Intent(view.getContext(),OrderDetail.class));
            }
        });
        return view;
    }
}
