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
import com.squareup.picasso.Picasso;

import hainb21127.poly.appfastfood_admin.R;


public class Profile extends Fragment {

    TextInputEditText ed_edit_pass, ed_edit_passnew, ed_edit_changepass;
    ImageView profileAvatar;
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
        profileAvatar = view.findViewById(R.id.avatar_repass);
        btn_save = view.findViewById(R.id.btn_edit_save_profile);

        getHide();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String iduser = user.getUid();
                String pass = ed_edit_pass.getText().toString().trim();
                String passnew = ed_edit_passnew.getText().toString().trim();
                String checkpass = ed_edit_changepass.getText().toString().trim();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference reference1 = firebaseDatabase.getReference("managers").child(iduser);
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mk = snapshot.child("passwd").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (pass.isEmpty() ){
                    ed_edit_pass.setError("không được để trống ");
                } else if (passnew.isEmpty()) {
                    ed_edit_passnew.setError("Không được để trống ");
                } else if (checkpass.isEmpty()) {
                    ed_edit_changepass.setError("không được để trống");
                } else if (!passnew.equals(checkpass)) {
                    ed_edit_changepass.setError("Chưa trùng mật khẩu");
                } else if (!pass.equals(mk)) {
                    ed_edit_pass.setError("Mật khẩu không tồn tại");
                } else {
                    user.updatePassword(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference reference = firebaseDatabase.getReference("managers").child(iduser);
                                DatabaseReference reference1 = reference.child("passwd");

                                reference1.setValue(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                Toast.makeText(getContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    private void getHide(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("managers").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String avater = snapshot.child("image").getValue(String.class);
                Picasso.get().load(avater).into(profileAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}