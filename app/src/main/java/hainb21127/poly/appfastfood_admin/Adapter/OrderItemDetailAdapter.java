package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
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
import hainb21127.poly.appfastfood_admin.DTO.OrderItemDetail;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class OrderItemDetailAdapter extends RecyclerView.Adapter<OrderItemDetailAdapter.OrderDetailViewHolder>{

    List<OrderItemDetail> mDetail;
    Context context;

    public OrderItemDetailAdapter(List<OrderItemDetail> mDetail, Context context) {
        this.mDetail = mDetail;
        this.context = context;
    }
    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderItemDetail detail = mDetail.get(position);
        if (mDetail == null){
            return;
        }
        Picasso.get().load(detail.getId_products().getImage()).into(holder.imageSp);
        holder.nameSp.setText(detail.getId_products().getTensp());
        holder.soLuongSp.setText(detail.getSoluong()+"");
        holder.giaSp.setText(Utilities.addDots(detail.getId_products().getGiasp())+"đ");
        holder.tongTienSp.setText(Utilities.addDots(detail.getTongTien())+"đ");
    }


    @Override
    public int getItemCount() {
        if (mDetail !=null){
            return mDetail.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView nameSp, giaSp,soLuongSp,tongTienSp;
        ImageView imageSp;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameSp = itemView.findViewById(R.id.nameOrder);
            soLuongSp = itemView.findViewById(R.id.soLuogOrder);
            giaSp = itemView.findViewById(R.id.priceOrderSp);
            tongTienSp = itemView.findViewById(R.id.tongTienSp);
            imageSp = itemView.findViewById(R.id.imgProduct_order);
        }
    }
}
