package com.example.movieapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Model.User;
import com.example.movieapp.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment  extends Fragment {

    private static final int RESULT_LOAD_IMAGE =1 ;
    ImageView image;
    DatabaseHelper db;
    private static String path;
    private static EditText et_name;
    private static EditText et_password;

    public RegisterFragment() {
    }



    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(
                R.layout.fragment_register, container, false);
        db = new DatabaseHelper(getContext());
        final  List<User> allUsers = db.getAllUsers();

        Button register = view.findViewById(R.id.register);
        et_name = view.findViewById(R.id.Register_Name);
        et_password = view.findViewById(R.id.Register_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_name.getText().toString().isEmpty() || (et_password.toString().isEmpty())) {

                    Toast.makeText(getContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    final String name = et_name.getText().toString();
                    final String password = et_password.getText().toString();
                    User user = new User(checkMaxId(allUsers) + 1, name, password, path);
                    Log.d("user", user.toString());
                    db.createUser(user);
                    LoginFragment loginFragment = new LoginFragment();
                    Fragmentchange(loginFragment);

                }
            }
        });

        Button upload = view.findViewById(R.id.button_upload);
        image = view.findViewById(R.id.imageView);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent,RESULT_LOAD_IMAGE);
            }
        });

        return view;
    }



    public void Fragmentchange (Fragment fragment)
    {

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment);
        fr.addToBackStack(null);
        fr.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
            path = getPath(selectedImage);

        }

    }

    private int checkMaxId(List<User> allUsers)
    {
        int id=0;
        for (User c: allUsers) {
            if(c.getId()>id)
            {
                id=c.getId();
            }
        }
        return id;
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}

