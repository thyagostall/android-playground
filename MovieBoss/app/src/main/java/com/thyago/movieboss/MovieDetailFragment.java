package com.thyago.movieboss;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    public MovieDetailFragment() {
    }

    public void updateView(View view, Movie movie) {
        TextView title = (TextView) view.findViewById(R.id.movie_title);
        TextView originalTitle = (TextView) view.findViewById(R.id.movie_original_title);
        TextView releaseDate = (TextView) view.findViewById(R.id.release_date);
        TextView rating = (TextView) view.findViewById(R.id.rating);
        TextView plot = (TextView) view.findViewById(R.id.plot);

        ImageView poster = (ImageView) view.findViewById(R.id.poster);

        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOriginalTitle());
//        releaseDate.setText(movie.getReleaseDate().toString());
        rating.setText(String.valueOf(movie.getRating()));
        plot.setText(movie.getPlot());

        Picasso.with(getContext()).load(movie.getPosterURL()).into(poster);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Bundle arguments = getArguments();
        Movie movie = (Movie) arguments.get(MovieDetail.EXTRA_MOVIE);

        updateView(rootView, movie);

        return rootView;
    }
}
