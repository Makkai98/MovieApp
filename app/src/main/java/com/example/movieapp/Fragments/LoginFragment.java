package com.example.movieapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.Database.DatabaseHelper;
import com.example.movieapp.Model.User;
import com.example.movieapp.R;

import java.util.List;

public class LoginFragment extends Fragment {
    DatabaseHelper db;

    public LoginFragment() {
    }

    ;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(
                R.layout.fragment_login, container, false);

        db = new DatabaseHelper(getContext());
        final EditText et_name = view.findViewById(R.id.Login_Name);
        final EditText et_password = view.findViewById(R.id.Login_password);

        Button login_button = view.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (et_name.getText().toString().isEmpty() || (et_password.toString().isEmpty())) {

                    Toast.makeText(getContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                } else {

                    final String username = et_name.getText().toString();
                    final String password = et_password.getText().toString();

                    final List<User> users = db.getAllUsers();
                    Log.d("users", users.toString());
                    int ok = 0;
                    for (User u : users)
                    {
                        if (u.getName().equals(username) && u.getPassword().equals(password))
                        {
                            ok = 1;
                        }
                    }

                    if (ok ==1) {

                        HomeFragment hFragment = new HomeFragment();
                        Fragmentchange(hFragment);
                    }
                    else {
                        Toast.makeText(getContext(), "Not Registered!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        Button button_register = view.findViewById(R.id.register_button);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment rFragment = new RegisterFragment ();
                Fragmentchange(rFragment);
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
}
