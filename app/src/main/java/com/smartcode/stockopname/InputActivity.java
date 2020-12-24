package com.smartcode.stockopname;


import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.smartcode.stockopname.network.Barang;
import com.smartcode.stockopname.network.SharedPrefManager;
import com.smartcode.stockopname.network.URLs;
import com.smartcode.stockopname.network.User;
import com.smartcode.stockopname.network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class InputActivity extends AppCompatActivity {

    EditText editTextLokasi, editTextKodeBarang, editTextQty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editTextLokasi = (EditText) findViewById(R.id.editTextLokasi);
        editTextKodeBarang = (EditText) findViewById(R.id.editTextKodeBarang);
        editTextQty = (EditText) findViewById(R.id.editTextQty);


        findViewById(R.id.buttonProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(InputActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonSimpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        editTextQty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    saveData();
                }
                return false;
            }
        });


    }

    private void saveData(){
        final String Lokasi = editTextLokasi.getText().toString().trim();
        final String KodeBarang = editTextKodeBarang.getText().toString().trim();
        final String Qty = editTextQty.getText().toString().trim();
        User user = SharedPrefManager.getInstance(this).getUser();

        if (TextUtils.isEmpty(KodeBarang)) {
            editTextKodeBarang.setError("Kode Barang harus di isi");
            editTextKodeBarang.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Qty)) {
            editTextQty.setError("Kode Barang harus di isi");
            editTextQty.requestFocus();
            return;
        }

        editTextLokasi.setEnabled(false);
        editTextKodeBarang.setEnabled(false);
        editTextQty.setEnabled(false);
        findViewById(R.id.buttonSimpan).setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIMPAN, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(response);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        onResume();
                        editTextKodeBarang.setText("");
                        editTextQty.setText("");

                        editTextLokasi.setEnabled(true);
                        editTextKodeBarang.setEnabled(true);
                        editTextQty.setEnabled(true);
                        findViewById(R.id.buttonSimpan).setEnabled(true);
                        editTextKodeBarang.requestFocus();
                        editTextKodeBarang.setFocusable(true);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editTextKodeBarang, InputMethodManager.SHOW_IMPLICIT);
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("barang");

                        //creating a new user object
                        Barang barang = new Barang(
                                userJson.getInt("id"),
                                userJson.getString("lokasi"),
                                userJson.getString("kode_barang"),
                                userJson.getString("jumlah_barang")
                        );


                    } else {
                        // jika terjadi error / error is true

                        onResume();
                        editTextLokasi.setEnabled(true);
                        editTextKodeBarang.setEnabled(true);
                        editTextQty.setEnabled(true);
                        findViewById(R.id.buttonSimpan).setEnabled(true);

                        editTextKodeBarang.requestFocus();
                        editTextKodeBarang.setFocusable(true);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editTextKodeBarang, InputMethodManager.SHOW_IMPLICIT);

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getId()));
                params.put("lokasi", Lokasi);
                params.put("kode_barang", KodeBarang);
                params.put("jumlah_barang", Qty);
                return params;


            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}