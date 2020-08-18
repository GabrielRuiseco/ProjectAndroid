package com.example.project.ui.filemenu;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.project.R;

import java.io.InputStream;
import java.net.URL;

public class FileMenuFragment extends Fragment {
    private String path;
    private ImageView imageView;
    private Button btn_open, btn_key, btn_dismiss, btn_create, btn_send;
    private FileMenuViewModel mViewModel;
    private EditText name;

    public static FileMenuFragment newInstance() {
        return new FileMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.file_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FileMenuViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            this.path = getArguments().getString("path");
        }
        imageView = view.findViewById(R.id.imgvm);
        btn_open = view.findViewById(R.id.btn_o);
        btn_key = view.findViewById(R.id.btn_k);
        btn_dismiss = view.findViewById(R.id.btn_d);
        btn_create = view.findViewById(R.id.btn_create);
        btn_send = view.findViewById(R.id.btn_sendImg);
        name = view.findViewById(R.id.editTextTextPersonName);

        btn_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_create.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);

                btn_create.setEnabled(true);
                name.setEnabled(true);
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_send.setVisibility(View.INVISIBLE);
                btn_send.setEnabled(true);
            }
        });

        btn_create.setEnabled(false);
        btn_send.setEnabled(false);
        name.setEnabled(false);

        btn_create.setVisibility(View.INVISIBLE);
        btn_send.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);

        String url = "http://127.0.0.1:3000/api/downloadit/" + path;
        imageView.setImageDrawable(LoadImageFromWebOperations(url));
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}