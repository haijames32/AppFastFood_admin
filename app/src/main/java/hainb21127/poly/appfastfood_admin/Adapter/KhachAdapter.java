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

import hainb21127.poly.appfastfood_admin.DTO.KhachHang;
import hainb21127.poly.appfastfood_admin.R;

public class KhachAdapter extends RecyclerView.Adapter<KhachAdapter.KhachViewHolder>{

    private List<KhachHang> listKhang;
    private Context context;

    public KhachAdapter(List<KhachHang> listKhang) {
        this.listKhang = listKhang;
    }

    public KhachAdapter(Context context) {
        this.context = context;
    }
    public void setDataKhang(List<KhachHang> arrKhang){
        this.listKhang = arrKhang;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new KhachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachViewHolder holder, int position) {
        KhachHang kh = listKhang.get(position);
        if (kh == null){
            return;
        }
        holder.tv_name.setText(kh.getFullname());
        holder.tv_address.setText(kh.getAddress());
        holder.tv_email.setText(kh.getEmail());
        holder.tv_phone.setText("+84 "+kh.getPhonenumber()+"");
    }

    @Override
    public int getItemCount() {
        if (listKhang!= null){
            return listKhang.size();
        }
        return 0;
    }

    public class KhachViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_address, tv_email, tv_phone;
        ImageView ed_info_kh;
        public KhachViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_kh);
            tv_address = itemView.findViewById(R.id.tv_address_kh);
            tv_email = itemView.findViewById(R.id.tv_email_address_kh);
            tv_phone = itemView.findViewById(R.id.tv_phone_kh);
        }
    }
}
