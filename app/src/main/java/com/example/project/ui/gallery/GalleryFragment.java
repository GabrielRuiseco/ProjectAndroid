package com.example.project.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.adapters.GalleryAdapter;
import com.example.project.classes.FileImage;
import com.example.project.classes.SingleRequestQueue;
import com.example.project.classes.Token;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    JSONArray jsonArray;
    ArrayList<FileImage> fileArray = new ArrayList<>();
    RecyclerView recyclerView;
    NavController navController;
    Button btn_refresh;
    String uid;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        btn_refresh = root.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), uid, Toast.LENGTH_LONG).show();
                getFiles(root);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUID();
    }

    public void setUID() {
        String url = "http://10.0.2.2:3000/emp/loggedIn";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String id = response.getString("id");
                    Token.setId(id);
                    uid=id;
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

    private void getFiles(final View v) {
        String url = "http://10.0.2.2:3000/api/index/"+uid;
        Log.d("TAG3", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                Log.d("TAG2", response.toString());
                jsonArray = response;
                Log.d("TAG1", jsonArray.toString());
                Gson gson = new Gson();
                Type fileImageType = new TypeToken<ArrayList<FileImage>>() {
                }.getType();
                fileArray = gson.fromJson(String.valueOf(jsonArray), fileImageType);
                Log.d("TAG", fileArray.toString());

                recyclerView = (RecyclerView) v.findViewById(R.id.recycler1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                GalleryAdapter galleryAdapter = new GalleryAdapter(fileArray, getContext());
                galleryAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("path", fileArray.get(recyclerView.getChildAdapterPosition(view)).getFileName());
                        navController.navigate(R.id.fileMenuFragment, bundle);
                    }
                });
                recyclerView.setAdapter(galleryAdapter);
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
        SingleRequestQueue.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }
}