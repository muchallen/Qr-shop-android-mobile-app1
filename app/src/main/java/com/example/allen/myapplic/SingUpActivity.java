package com.example.allen.myapplic;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.allen.myapplic.model.User;
import com.google.firebase.database.*;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SingUpActivity extends AppCompatActivity {

    MaterialEditText editPhone, editUsername,editPassword;
    Button btnsignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

    editUsername=(MaterialEditText) findViewById(R.id.editusername);
    editPassword=(MaterialEditText) findViewById(R.id.editpassword);
    editPhone=(MaterialEditText)findViewById(R.id.editphone);

    btnsignUp=findViewById(R.id.btnsignup);

    //init Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SingUpActivity.this);
                mDialog.setMessage("please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if already user phone
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SingUpActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            mDialog.dismiss();
                            User user = new User(editUsername.getText().toString(),editPassword.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);

                            Toast.makeText(SingUpActivity.this, "you have successfully signed up", Toast.LENGTH_SHORT).show();
                            finish();

                         }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });





    }
}
