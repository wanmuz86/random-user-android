package com.itrainasia.randomuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView nameTextView, emailTextView, phoneTextView, addressTextView;
    ImageView userImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonDld);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        addressTextView = findViewById(R.id.addressTextView);
        userImageView = findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adding Post Parameters
                // Change the request from method GET to POST
                // Add params as the third parameter in StringRequest

/*
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("city", "london");
                    postparams.put("timestamp", "1500134255");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://www.randomuser.me/api";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.d("response",response);
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONObject firstJSON = jsonResponse.getJSONArray("results").
                                            getJSONObject(0);
                                    JSONObject nameJSON = firstJSON.getJSONObject("name");
                                    String name = nameJSON.getString("title") + " "
                                            + nameJSON.getString("first") + " "
                                            + nameJSON.getString("last");
                                    nameTextView.setText(name);

                                    String email = firstJSON.getString("email");
                                    emailTextView.setText(email);

                                    String cell = firstJSON.getString("cell");
                                    phoneTextView.setText(cell);

                                    JSONObject locationJSON = firstJSON.getJSONObject("location");
                                    String address = locationJSON.getJSONObject("street").getString("name") +
                                            " " + locationJSON.getString("city") + " "
                                            + locationJSON.getString("state") + " " +locationJSON.getString("country");
                                    addressTextView.setText(address);

                                    String imageURL = firstJSON.getJSONObject("picture").getString("large");
                                    Glide.with(MainActivity.this).load(imageURL).into(userImageView);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.d("error",error.toString());
                    }
                }
                );
                //Part to add Header
                /*{
                    /**
                     * Passing some request headers*
                     */
                   /* @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json");
                        headers.put("apiKey", "xxxxxxxxxxxxxxx");
                        return headers;
                    }

                };*/

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }

        });
    }
}
