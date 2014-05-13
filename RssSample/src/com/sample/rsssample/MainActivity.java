package com.sample.rsssample;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

// 下記ページ参照
// http://d.hatena.ne.jp/unagi_brandnew/20100326/1269596483
//  fragment使用、AsyncTaskLoader使用

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
            .add(R.id.container, new ArticleListFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public static class ArticleListFragment extends ListFragment implements LoaderCallbacks<List<Item>> {

        private static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
        public static final int MENU_ITEM_RELOAD = Menu.FIRST;
        private RssListAdapter mAdapter;
        private List<Item> mItems;

        public ArticleListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstance) {
            super.onActivityCreated(savedInstance);

            mAdapter = new RssListAdapter(getActivity());
            setListAdapter(mAdapter);
            getLoaderManager().initLoader(0, null,this).forceLoad();
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Item item = mItems.get(position);
            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra("TITLE", item.getTitle());
            intent.putExtra("DESCRIPTION", item.getDescription());
            startActivity(intent);
        }

        @Override
        public Loader<List<Item>> onCreateLoader(int arg0, Bundle arg1) {
            RssParserTaskLoader rssLoader = new RssParserTaskLoader(getActivity());
            rssLoader.setUrl(RSS_FEED_URL);
            return rssLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
            mAdapter.setData(data);
            mItems = data;
        }

        @Override
        public void onLoaderReset(Loader<List<Item>> arg0) {
            mAdapter.setData(null);
            mItems = null;
        }
    }
}
