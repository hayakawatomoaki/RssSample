package com.sample.rsssample;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TextView mTitle;
        private TextView mDescr;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item_detail,
                    container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstance) {
            super.onActivityCreated(savedInstance);
            Intent intent = getActivity().getIntent();
            String title = intent.getStringExtra("TITLE");
            mTitle = (TextView) getActivity().findViewById(R.id.item_detail_title);
            mTitle.setText(title);
            String descr = intent.getStringExtra("DESCRIPTION");
            mDescr = (TextView) getActivity().findViewById(R.id.item_detail_descr);
            mDescr.setText(descr);
        }
    }
}
