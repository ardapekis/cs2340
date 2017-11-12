package com.ardapekis.cs2340_27.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.ardapekis.cs2340_27.controller.AppActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
//import java.nio.Buffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles all rat reports
 */

public class RatReportManager {
    /** used to generate new keys */
    private int keySeed;

    /** Used to prevent excess loading */
    private boolean loaded;

    /** holds reports in an arraylist, sort by old */
    private List<RatReportItem> reports;

    /** holds reports in a linkedlist/queue, sort by new */
    private List<RatReportItem> reportsQueue;

    /**
     * Private constructor since singleton design
     */
    public RatReportManager() {
        reports = new ArrayList<>(110000);
        reportsQueue = new LinkedList<>();
        loaded = false;
        keySeed = 0;
    }

    /**
     * Goes through each ratreport and calls its saveAsText method and writes it
     * @param printWriter       the PrintWriter writes to the file
     */
    public void saveAsText(PrintWriter printWriter) {
        printWriter.println(reports.size());
        for(RatReportItem s : reports) {
            s.saveAsText(printWriter);
        }
    }

    /**
     * Reloads the data from the text file
     * @param reader        the reader that is reading the rat data file
     */
    void loadFromText(BufferedReader reader) {
//        Loader loader = new Loader(context, adapter, reader);
//        loader.execute();
        System.out.println("Loading Text File");
        reports.clear();
        reportsQueue.clear();
        try {
            String count = reader.readLine();
            for (int i = 0; i < Integer.valueOf(count); i++) {
                String line = reader.readLine();
                RatReportItem s = RatReportItem.parseEntry(line);
                addItem(s);
                addItemToFront(s);

            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Unused AsyncTask, so no fancy loading when loading from persisted data
     */
    private class Loader extends AsyncTask<Void, Integer, Void> {
        private Context context;
        private AppActivity.RatReportItemRecyclerViewAdapter adapter;
        ProgressDialog progressDialog = new ProgressDialog(context);
        private BufferedReader reader;

        public Loader(Context cxt, AppActivity.RatReportItemRecyclerViewAdapter adapter, BufferedReader reader) {
            context = cxt;
            this.adapter = adapter;
            this.reader = reader;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loaded:");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    cancel(true);
                    Log.d("Cancel", "tried to cancel");
                }
            });
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... unused) {
            System.out.println("Loading Text File");
            reports.clear();
            reportsQueue.clear();
            try {
                String line = reader.readLine();
                while (!line.equals("EOF")) {

                    RatReportItem s = RatReportItem.parseEntry(line);
                    reports.add(s);
                    reportsQueue.add(0, s);
                    line = reader.readLine();

                    publishProgress(reports.size());
                }
                reader.close();
            } catch(IOException e){
                e.printStackTrace();
            }
            return (null);
        }

        @Override
        protected void onPostExecute(Void unused) {
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Integer... loaded) {
            progressDialog.setMessage("Loaded: " + loaded[0]);
        }
    }

    /**
     *
     * Setter
     *
     * @param b the boolean b
     */
    public void setLoaded(Boolean b) {
        loaded = b;
    }

    /**
     *
     * Getter
     *
     * @return the loaded boolean
     */
    public boolean getLoaded() {
        return loaded;
    }

    /**
     * Add a new item to the list of rat reports, also updates keySeed
     * @param item      New rat report to add
     */
    public void addItem(RatReportItem item) {
        reports.add(item);
        if (item.getKey() > keySeed) {
            keySeed = item.getKey();
        }
    }

    /**
     * Add a new item to the front of the list, i.e. add to queue, also
     * updates keySeed
     *
     * @param item      New rat report to add
     */
    public void addItemToFront(RatReportItem item) {
        reportsQueue.add(0, item);
        if (item.getKey() > keySeed) {
            keySeed = item.getKey();
        }
    }

    /**
     * Uses keySeed to generate a new unique key
     *
     * @return      new unique key
     */
    public int getNewKey() {
        return ++keySeed;
    }

    /**
     * Gets the list of rat reports
     * @return      The list of rat reports, oldest first
     */
    public List<RatReportItem> getItems() {
        return reports;
    }

    /**
     * Gets the queue of rat reports
     * @return      The queue of rat reports, sorted newest first
     */
    public List<RatReportItem> getItemsQueue() { return reportsQueue; }

    /**
     * Returns an item based on its unique key
     * @param key       The rat report's unique key
     * @param sort      The flag for whether to search the queue or the list
     * @return          The rat report corresponding to the key
     */
    public RatReportItem findItemByKey(int key, String sort) {
        List<RatReportItem> list =  reportsQueue;
        switch (sort) {
            case "new":
                list = reportsQueue;
                break;
            case "old":
                list = reports;
                break;
        }
        for (RatReportItem d : list) {
            if (d.getKey() == key) return d;
        }
        Log.d("MYAPP", "Warning - Failed to find id: " + key);
        return null;
    }

    /**
     * Returns items matching the author name provided.
     * @param author    The author name to match on.
     * @return          The list of rat repots matching.
     */
    public List<RatReportItem> getItemsByAuthor(String author) {
        List<RatReportItem> list = new ArrayList<RatReportItem>();
        for (RatReportItem item : reports) {
            if (item.getAuthor().equals(author)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     *
     * Returns the last RatReportItem in reports
     *
     * @return the last RatReportItem
     */
    public RatReportItem getLastReport() { return reports.get(reports.size() - 1);}
}
