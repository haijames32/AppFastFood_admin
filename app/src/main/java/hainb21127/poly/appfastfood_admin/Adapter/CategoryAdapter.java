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

import hainb21127.poly.appfastfood_admin.DTO.Category;
import hainb21127.poly.appfastfood_admin.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<Category> mCatrgory;

    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public CategoryAdapter(List<Category> mCatrgory) {
        this.mCatrgory = mCatrgory;
    }
    public void setDataCatogory(List<Category> arrayCate){
        this.mCatrgory = arrayCate;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mCatrgory.get(position);
        if (mCatrgory ==null){
            return;
        }
        Picasso.get().load(category.getImageCat()).into(holder.img_lsp);
        holder.tv_name_lsp.setText(category.getNameCat());
    }

    @Override
    public int getItemCount() {
        if (mCatrgory != null){
            return mCatrgory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_lsp, edit_lsp, delete_lsp;
        private TextView tv_name_lsp;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            img_lsp = itemView.findViewById(R.id.img_cat_item);
            tv_name_lsp = itemView.findViewById(R.id.name_cat_item);
        }
    }
}
