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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.Adapter.MoviesAdapter;
import com.example.movieapp.Adapter.MoviesRepository;
import com.example.movieapp.MainActivity;
import com.example.movieapp.Model.Movie;
import com.example.movieapp.OnGetMoviesCallBack;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    private MoviesRepository moviesRepository;
    public HomeFragment() {
    };

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


        moviesRepository = MoviesRepository.getInstance();

        moviesList = view.findViewById(R.id.movies_list);
        moviesList.setLayoutManager(new LinearLayoutManager(getContext()));

        moviesRepository.getMovies(new OnGetMoviesCallBack() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(movies);
                moviesList.setAdapter(adapter);
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
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
