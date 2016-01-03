package com.thyago.movieboss;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverActivityFragment extends Fragment {

    public class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(Void... params) {
            API api = new API();
            return api.discover(null);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            for (Movie item : movies) {
                System.out.println(item.getTitle() + " - " + item.getRating());
            }
        }
    }

    public DiscoverActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new FetchMovieTask().execute();

        return inflater.inflate(R.layout.fragment_discover, container, false);
    }
}
