package com.thyago.movieboss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DiscoverActivityFragment extends Fragment {

    private static final String LOG_TAG = DiscoverActivityFragment.class.getSimpleName();
    private MovieAdapter mAdapter;

    public class MovieAdapter extends ArrayAdapter<Movie> implements View.OnClickListener {

        public MovieAdapter(Context context, int resource, List<Movie> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater li = LayoutInflater.from(getContext());
                v = li.inflate(R.layout.movie_item, null);
            }

            Movie movie = getItem(position);
            v.setTag(movie);

            if (movie != null) {
                TextView title = (TextView) v.findViewById(R.id.text_title);
                title.setText(movie.getTitle());

                ImageView image = (ImageView) v.findViewById(R.id.poster);
                Picasso.with(getContext()).load(movie.getPosterURL()).into(image);
            }

            v.setOnClickListener(this);
            return v;
        }

        @Override
        public void onClick(View v) {
            Movie movie = (Movie) v.getTag();

            Intent intent = new Intent(getContext(), MovieDetail.class);
            intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }

    public class FetchMovieTask extends AsyncTask<API.SortingCriterion, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(API.SortingCriterion... params) {
            API api = new API();
            return api.discover(params[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mAdapter.clear();
            mAdapter.addAll(movies);
        }
    }

    public void refresh() {
        Log.d(LOG_TAG, "Refresh");

        FetchMovieTask task = new FetchMovieTask();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sorting = prefs.getString(getString(R.string.pref_sorting_key), getString(R.string.pref_sorting_popularity));

        API.SortingCriterion criterion = API.SortingCriterion.fromString(sorting);
        task.execute(criterion);
    }

    public DiscoverActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        mAdapter = new MovieAdapter(getContext(), R.layout.movie_item, new ArrayList<Movie>());
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }
}
