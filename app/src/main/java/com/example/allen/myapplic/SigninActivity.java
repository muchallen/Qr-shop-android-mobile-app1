package com.example.allen.myapplic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.allen.myapplic.model.User;
import com.google.firebase.database.*;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SigninActivity extends AppCompatActivity {

    EditText editPhone, editPassword;
    Button btnsignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editPhone=(MaterialEditText)findViewById(R.id.editphone);
        editPassword=(MaterialEditText)findViewById(R.id.editpassword);
        btnsignin=findViewById(R.id.btnsignin);

        //init Firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SigninActivity.this);
                mDialog.setMessage("please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in the database
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                mDialog.dismiss();
                                Toast.makeText(SigninActivity.this, "sign in successfully !", Toast.LENGTH_SHORT).show();
                                Intent shopping =new Intent(SigninActivity.this,shoppingActivity.class);
                                startActivity(shopping);


                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SigninActivity.this, "Sign in failed !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SigninActivity.this, "User does not exist in the Database", Toast.LENGTH_SHORT).show();

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
