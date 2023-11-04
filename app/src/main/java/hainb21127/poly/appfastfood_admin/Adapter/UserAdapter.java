package hainb21127.poly.appfastfood_admin.Adapter;

import android.app.AlertDialog;
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

import hainb21127.poly.appfastfood_admin.Activity.UpdateNV;
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
        holder.editMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateNV.class);
                intent.putExtra("nvId",user.getId());
                intent.putExtra("avatar", user.getImage());
                intent.putExtra("nvName",user.getName());
                intent.putExtra("nvEmail",user.getEmail());
                intent.putExtra("phoneNumber",user.getPhone());
                intent.putExtra("roles",user.getLevel());
                intent.putExtra("passwd",user.getPasswd());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListUser != null){
            return mListUser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView img_member, editMember, deleteMember;
        TextView tv_name, tv_email, tv_roles, tv_phone;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img_member = itemView.findViewById(R.id.img_item_member);
            tv_name = itemView.findViewById(R.id.tv_name_item_member);
            tv_email = itemView.findViewById(R.id.tv_gmail_item_member);
            tv_roles = itemView.findViewById(R.id.tv_cv_item_member);
            tv_phone = itemView.findViewById(R.id.tv_phone_item_member);
            editMember = itemView.findViewById(R.id.edit_item_member);
            deleteMember = itemView.findViewById(R.id.delete_item_member);
        }
    }
}
