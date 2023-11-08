package hainb21127.poly.appfastfood_admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hainb21127.poly.appfastfood_admin.R;


public class Profile extends Fragment {

    TextInputEditText ed_edit_pass, ed_edit_passnew, ed_edit_changepass;
    ImageView btn_back;
    Button btn_save;
    String mk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pw, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_edit_pass = view.findViewById(R.id.ed_edit_pass_profile);
        ed_edit_passnew = view.findViewById(R.id.ed_edit_passnew_profile);
        ed_edit_changepass = view.findViewById(R.id.ed_edit_checkpass_profile);
        btn_save = view.findViewById(R.id.btn_edit_save_profile);

//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                String iduser = user.getUid();
//                String pass = ed_edit_pass.getText().toString().trim();
//                String passnew = ed_edit_passnew.getText().toString().trim();
//                String checkpass = ed_edit_changepass.getText().toString().trim();
//                if (pass.isEmpty() ){
//                    ed_edit_pass.setError("không được để trống ");
//                } else if (passnew.isEmpty()) {
//                    ed_edit_passnew.setError("Không được để trống ");
//                } else if (checkpass.isEmpty()) {
//                    ed_edit_changepass.setError("không được để trống");
//                } else if (!passnew.equals(checkpass)) {
//                    ed_edit_changepass.setError("Chưa trùng mật khẩu");
//                } else if (!pass.equals(mk)) {
//                    ed_edit_pass.setError("Mật khẩu không tồn tại");
//                } else {
//                    user.updatePassword(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getContext(), "Đổi mk thành công", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String pass = ed_edit_pass.getText().toString().trim();
                String passnew = ed_edit_passnew.getText().toString().trim();
                String checkpass = ed_edit_changepass.getText().toString().trim();

                if (pass.isEmpty()) {
                    ed_edit_pass.setError("Không được để trống");
                } else if (passnew.isEmpty()) {
                    ed_edit_passnew.setError("Không được để trống");
                } else if (checkpass.isEmpty()) {
                    ed_edit_changepass.setError("Không được để trống");
                } else if (!pass.equals(mk)) {
                    ed_edit_pass.setError("Mật khẩu hiện tại không đúng");
                } else {
                    // Kiểm tra xem user có null hay không trước khi cập nhật mật khẩu
                    if (user != null) {
                        user.updatePassword(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        // Xử lý tại đây nếu user == null (người dùng chưa đăng nhập)
                    }
                }
            }
        });

    }

}