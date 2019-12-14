package com.example.movieapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    ;

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(
                R.layout.fragment_home, container, false);
        Bundle bundle = this.getArguments();
        final int id = bundle.getInt("id");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_profile:
                        Bundle bundleprof = new Bundle();
                        bundleprof.putInt("id", id);
                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundleprof);

                        Fragmentchange(profileFragment);
                        break;
                    case R.id.navigation_favorites:

                        break;

                }
                return true;
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
}
