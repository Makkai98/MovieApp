package com.example.movieapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Model.User;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    ImageView image;
    DatabaseHelper db;
    private static String path;
    private static final int RESULT_LOAD_IMAGE =1 ;
    private static User user;
    public ProfileFragment() {
    };



    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(
                R.layout.fragment_profile, container, false);
        Bundle bundle = this.getArguments();
        final int id = bundle.getInt("id");
        db = new DatabaseHelper(getContext());

        user = db.getUser(id);

        final String path = user.getProfileimage();
        Uri pathuri = Uri.parse(path);
        Log.d("path", path);

            image = view.findViewById(R.id.profile_imageView);
            image.setImageURI(pathuri);


        TextView setUsername = view.findViewById(R.id.textView_setusername);
        final TextView setPassword = view.findViewById(R.id.textView_setpassword);
        setUsername.setText(user.getName());
        setPassword.setText(user.getPassword());


        Button changepassword = view.findViewById(R.id.button_updatepassword);
        Button changeprofile = view.findViewById(R.id.button_updatepicture);

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialog = LayoutInflater.from(getContext()).inflate(R.layout.alert_layout, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //builder.setTitle("Set Your Password");
                builder.setView(dialog);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_pw = dialog.findViewById(R.id.new_pw);

                        user.setPassword(et_pw.getText().toString());

                        db.updateUserPassword(user);
                        setPassword.setText(et_pw.getText().toString());
                    }
                });

                AlertDialog d = builder.create();
                d.show();
    }
        });

        changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(GalleryIntent,RESULT_LOAD_IMAGE);

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Bundle bundlehome = new Bundle();
                        bundlehome.putInt("id", id);
                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundlehome);
                        Fragmentchange(homeFragment);
                        break;
                    case R.id.navigation_profile:

                        break;
                    case R.id.navigation_favorites:

                        break;

                }
                return true;
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            image.setImageURI(selectedImage);
            path = selectedImage.toString();
            Log.d("path2", path);
            user.setProfileimage(path);
            db.updateUserPicture(user);


        }

    }

    public void Fragmentchange (Fragment fragment)
    {

        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, fragment);
        fr.addToBackStack(null);
        fr.commit();
    }
}
