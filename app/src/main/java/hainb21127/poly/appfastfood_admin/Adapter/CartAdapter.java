package hainb21127.poly.appfastfood_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hainb21127.poly.appfastfood_admin.DTO.Cart;
import hainb21127.poly.appfastfood_admin.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    private List<Cart> mOrder;
    Context context;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public CartAdapter(List<Cart> mOrder) {
        this.mOrder = mOrder;
    }
    public void stDataOrder(List<Cart> arrorder){
        this.mOrder = arrorder;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = new Cart();
        if (mOrder == null){
            return;
        }
//        Picasso.get().load().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mOrder != null){
            return mOrder.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name,status,date,total,btnComfirm;
        ImageView imageView;
        public CartViewHolder(@NonNull View itemView) {
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
