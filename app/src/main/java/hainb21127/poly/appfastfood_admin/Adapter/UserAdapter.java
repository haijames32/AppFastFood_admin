package hainb21127.poly.appfastfood_admin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

//        if (holder.tv_roles.getText().toString().equals("1")){
//            holder.deleteMember.setVisibility(View.VISIBLE);
//            holder.editMember.setVisibility(View.VISIBLE);
//        }
        holder.deleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa người này không?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference DBref = database.getReference("managers").child(user.getId());
                        DBref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(view.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
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
