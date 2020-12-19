package com.smartcode.stockopname;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartcode.stockopname.network.SharedPrefManager;
import com.smartcode.stockopname.network.User;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewId, textViewEmail, textViewFullName, textViewDealerID;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //jika tidak login
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewFullName = (TextView) findViewById(R.id.textViewFullName);
        textViewDealerID = (TextView) findViewById(R.id.textViewDealerID);
//
//        //memulai mengambil data user
        User user = SharedPrefManager.getInstance(this).getUser();
//
        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewEmail.setText(user.getEmail());
        textViewFullName.setText(user.getFullName());
        textViewDealerID.setText(user.getDealerID());

//        ketika Mulai Stock Opname
        findViewById(R.id.buttonMulai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//      ketika logout
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
