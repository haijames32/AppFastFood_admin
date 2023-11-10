package hainb21127.poly.appfastfood_admin.Adapter;

import hainb21127.poly.appfastfood_admin.Activity.ProductsDetail;
import hainb21127.poly.appfastfood_admin.R;

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


import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class CategoryDetailAdapter extends BaseAdapter {
    Context context;
    private List<Products> aProducts;

    public CategoryDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Products> arrayList) {
        this.aProducts = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(aProducts != null)
            return aProducts.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return aProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.category_detail_item, null);
            Products product = aProducts.get(i);

            TextView tv_name = view.findViewById(R.id.tv_name_detailtl_item);
            TextView tv_price = view.findViewById(R.id.tv_price_detailtl_item);
            ImageView img_item = view.findViewById(R.id.img_detailtl_item);

            Picasso.get().load(product.getImage()).into(img_item);
            tv_name.setText(product.getTensp());
            tv_price.setText(Utilities.addDots(product.getGiasp()) + "đ");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ProductsDetail.class);
                    intent.putExtra("idPro", product.getId());
                    intent.putExtra("namePro", product.getTensp());
                    intent.putExtra("pricePro", product.getGiasp());
                    intent.putExtra("imagePro", product.getImage());
                    intent.putExtra("motaPro", product.getMota());
                    view.getContext().startActivity(intent);
                }
            });
        }
        return view;
    }
}
