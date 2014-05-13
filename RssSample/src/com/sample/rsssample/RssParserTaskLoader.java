package com.sample.rsssample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Xml;

public class RssParserTaskLoader extends AsyncTaskLoader<List<Item>> {
    private String url;

    public RssParserTaskLoader(Context context) {
        super(context);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public List<Item> loadInBackground() {
        List<Item> result = null;
        try {
            // HTTP経由でアクセスし、InputStreamを取得する
            URL url = new URL(this.url);
            InputStream is = url.openConnection().getInputStream();
            result = parseXml(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ここで返した値は、onPostExecuteメソッドの引数として渡される
        return result;
    }

    // XMLをパースする
    public List<Item> parseXml(InputStream is) throws IOException, XmlPullParserException {
        XmlPullParser parser = Xml.newPullParser();
        List<Item> list = new ArrayList<Item>();

        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            Item currentItem = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = null;
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    if (tag.equals("item")) {
                        currentItem = new Item();
                    } else if (currentItem != null) {
                        if (tag.equals("title")) {
                            currentItem.setTitle(parser.nextText());
                        } else if (tag.equals("description")) {
                            currentItem.setDescription(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tag = parser.getName();
                    if (tag.equals("item")) {
                        list.add(currentItem);
                    }
                    break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}