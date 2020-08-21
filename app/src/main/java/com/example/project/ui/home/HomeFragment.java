package com.example.project.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.project.R;
import com.example.project.classes.SingleRequestQueue;
import com.example.project.classes.Token;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    ImageView imageView;
    TextView tx_fn, tx_ln, tx_em;
    String uid;
    JSONObject jsonObject;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setUID();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView2);
        tx_fn = view.findViewById(R.id.firstName);
        tx_ln = view.findViewById(R.id.lastName);
        tx_em = view.findViewById(R.id.p_email);

        imageView.setImageResource(R.drawable.ic_cat);
    }

    public void setUID() {
        String url = "http://10.0.2.2:3000/emp/loggedIn";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("id");
                    Token.setId(id);
                    uid = id;
                    getUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Token.getToken().length() > 0) {
                    Toast.makeText(getContext(), "Success" + response.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                String token = "Bearer " + Token.getToken();
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        SingleRequestQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void getUser() {
        String url = "http://10.0.2.2:3000/emp/getuser/" + uid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonObject = response;
                try {
                    Log.d("TAG7",response.getString("firstName") );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tx_fn.setText(jsonObject.getString("first_name"));
                    tx_ln.setText(jsonObject.getString("last_name"));
                    tx_em.setText(jsonObject.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Token.getToken().length() > 0) {
                    Toast.makeText(getContext(), "Success" + response.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                String token = "Bearer " + Token.getToken();
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        SingleRequestQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}