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

import hainb21127.poly.appfastfood_admin.DTO.User;
import hainb21127.poly.appfastfood_admin.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{


    private List<User> mListUser;

    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public UserAdapter(List<User> mListUser) {
        this.mListUser = mListUser;
    }

    public void setDataUser(List<User> arrayUser){
        this.mListUser = arrayUser;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if (user == null){
            return;
        }
        holder.tv_name.setText(user.getName());
        holder.tv_email.setText(user.getEmail());
        holder.tv_phone.setText(user.getPhone());
        holder.tv_roles.setText(user.getLevel()+"");
        Picasso.get().load(user.getImage()).into(holder.img_member);
    }

    @Override
    public int getItemCount() {
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView img_member, edit_item_member, delete_item_member;
        TextView tv_name, tv_email, tv_roles, tv_phone;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img_member = itemView.findViewById(R.id.img_item_member);
            tv_name = itemView.findViewById(R.id.tv_name_item_member);
            tv_email = itemView.findViewById(R.id.tv_gmail_item_member);
            tv_roles = itemView.findViewById(R.id.tv_cv_item_member);
            tv_phone = itemView.findViewById(R.id.tv_phone_item_member);
            edit_item_member = itemView.findViewById(R.id.edit_item_member);
            delete_item_member = itemView.findViewById(R.id.delete_item_member);
        }
    }
}
