package com.thyago.movieboss;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by thyago on 1/3/16.
 */
public class API {
    public static enum SortingCriterion {
        POPULARITY("popularity.desc"),
        RELEASE_DATE("release_date.desc");

        private String key;

        SortingCriterion(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public static SortingCriterion fromString(String s) {
            if (s == null) {
                return null;
            }

            for (SortingCriterion criterion : SortingCriterion.values()) {
                if (s.equalsIgnoreCase(criterion.key)) {
                    return criterion;
                }
            }
            return null;
        }
    }

    private static String LOG_TAG = API.class.getSimpleName();
    private static String API_KEY = "6f126bdc69ae948a7cd288dee773f891";

    private String mPosterURLPrefix;

    public List<Movie> discover(SortingCriterion sortingCriterion) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = getDiscoverUrl(sortingCriterion);
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection == null) {
                return null;
            }
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            return jsonToMovieList(buffer.toString());
        } catch (IOException | JSONException e) {
            Log.d(LOG_TAG, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        List<Movie> result = new ArrayList<>();
        return result;
    }

    private List<Movie> jsonToMovieList(String json) throws JSONException {
        final String FIELD_TITLE = "title";
        final String FIELD_ORIGINAL_TITLE = "original_title";
        final String FIELD_RELEASE_DATE = "release_date";
        final String FIELD_POSTER_PATH = "poster_path";
        final String FIELD_RATING = "vote_average";
        final String FIELD_PLOT = "overview";

        final String INTERNAL_RESULT = "results";

        JSONObject apiResult = new JSONObject(json);
        JSONArray results = apiResult.getJSONArray(INTERNAL_RESULT);

        List<Movie> result = new ArrayList<>(results.length());
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonItem = results.getJSONObject(i);

            Movie item = new Movie();
            item.setTitle(jsonItem.getString(FIELD_TITLE));
            item.setOriginalTitle(jsonItem.getString(FIELD_ORIGINAL_TITLE));
            item.setPlot(jsonItem.getString(FIELD_PLOT));
            item.setReleaseDate(parseDate(jsonItem.getString(FIELD_RELEASE_DATE)));
            item.setRating(jsonItem.getDouble(FIELD_RATING));

            String url = mPosterURLPrefix + jsonItem.getString(FIELD_POSTER_PATH);
            item.setPosterURL(url);

            result.add(item);
        }
        return result;
    }

    private URL getDiscoverUrl(SortingCriterion sortingCriterion) {
        // TODO: Include the sorting criterion into the URL
        Uri.Builder builder = new Uri.Builder();
        Uri rawUri = builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sort_by", sortingCriterion.getKey())
                .appendQueryParameter("api_key", API_KEY)
                .build();

        mPosterURLPrefix = "http://image.tmdb.org/t/p/w185/";
        try {
            return new URL(rawUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error on creating the URL", e);
            return null;
        }
    }

    private static Date parseDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return format.parse(input);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Error on parsing API date");
            return null;
        }
    }
}
