package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import hainb21127.poly.appfastfood_admin.Activity.OrderDetail;
import hainb21127.poly.appfastfood_admin.DTO.Order;
import hainb21127.poly.appfastfood_admin.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<Order> mOrder;
    Context context;
    public OrderAdapter(List<Order> mOrder) {
        this.mOrder = mOrder;
    }

    public OrderAdapter(Context context) {
        this.context = context;
    }
    public void setDataOrder(List<Order> arrOrder){
        this.mOrder = arrOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = mOrder.get(position);
        if (mOrder == null){
            return;
        }
        holder.date.setText(order.getDate());
        holder.name.setText(order.getId_user().getName());
        holder.total.setText(order.getTong()+"");
        holder.status.setText(order.getStatus());
        Picasso.get().load(order.getId_user().getImage()).into(holder.imageView);

        if (holder.status.getText().toString().equalsIgnoreCase("Chờ xác nhận")){
            holder.btnComfirm.setVisibility(View.VISIBLE);
        }
        holder.btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OrderDetail.class);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mOrder != null){
            return mOrder.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView name,status,date,total,btnComfirm;
        ImageView imageView;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgOrder);
            name = itemView.findViewById(R.id.orderName);
            status = itemView.findViewById(R.id.orderStatus);
            date = itemView.findViewById(R.id.orDate);
            total = itemView.findViewById(R.id.orderPrice);
            btnComfirm = itemView.findViewById(R.id.btnComfirm);
        }
    }
}
