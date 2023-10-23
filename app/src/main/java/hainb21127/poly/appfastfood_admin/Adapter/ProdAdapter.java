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

import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class ProdAdapter extends RecyclerView.Adapter<ProdAdapter.ProdViewHolder>{

    private List<Products> mListProd;
    private Context context;

    public ProdAdapter(List<Products> mListProd, Context context) {
        this.mListProd = mListProd;
    }
    public ProdAdapter( Context context) {
        this.context = context;
    }
    public void setData(List<Products> arrayList) {
        this.mListProd = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ProdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdViewHolder holder, int position) {
        Products prd = mListProd.get(position);
        if (prd == null){
            return;
        }
        holder.tv_name_prd.setText(prd.getTensp());
        holder.tv_price_prd.setText(Utilities.addDots(prd.getGiasp())+" VND" );
        Picasso.get().load(prd.getImage()).into(holder.img_prod);
    }

    @Override
    public int getItemCount() {
        if (mListProd !=null){
            return mListProd.size();
        }
        return 0;
    }

    public class ProdViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_prod;
        private TextView tv_name_prd, tv_price_prd;
        public ProdViewHolder(@NonNull View itemView) {
            super(itemView);
            img_prod = itemView.findViewById(R.id.item_img_prod);
            tv_name_prd = itemView.findViewById(R.id.tv_name_product);
            tv_price_prd = itemView.findViewById(R.id.tv_price_product);
        }
    }
}
