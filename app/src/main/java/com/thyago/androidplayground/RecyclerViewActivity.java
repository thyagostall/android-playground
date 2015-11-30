package com.thyago.androidplayground;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewActivity extends AppCompatActivity {

    public class RecyclerViewActivityAdapter extends RecyclerView.Adapter<RecyclerViewActivityAdapter.ViewHolder> {
        private String[] mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public CardView mCardView;
            public ViewHolder(CardView v) {
                super(v);
                mCardView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public RecyclerViewActivityAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerViewActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder((CardView) v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            TextView v = (TextView) holder.mCardView.findViewById(R.id.text_item_recycler);
            v.setText(mDataset[position]);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

    protected String[] getDataSet(int number) {
        String[] result = new String[number];
        for (int i = 0; i < number; i++) {
            result[i] = "Item: " + (i + 1);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = getDataSet(5000);

        RecyclerViewActivityAdapter adapter = new RecyclerViewActivityAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(RecyclerViewActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
