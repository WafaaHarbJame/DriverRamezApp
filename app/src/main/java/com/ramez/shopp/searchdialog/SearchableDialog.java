package com.ramez.shopp.searchdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ajithvgiri.searchdialog.SearchListAdapter;
import com.ajithvgiri.searchdialog.SearchListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajithvgiri on 06/11/17.
 */

public class SearchableDialog {

    private static final String TAG = "SearchableDialog";
    List<com.ajithvgiri.searchdialog.SearchListItem> searchListItems;
    Activity activity;
    String dTitle;
    OnSearchItemSelected onSearchItemSelected;
    AlertDialog alertDialog;
    int position;
    int style;
    com.ajithvgiri.searchdialog.SearchListItem searchListItem = null;

    SearchListAdapter searchListAdapter;
    ListView listView;

    public SearchableDialog(Activity activity, List<com.ajithvgiri.searchdialog.SearchListItem> searchListItems, String dialogTitle) {
        this.searchListItems = searchListItems;
        this.activity = activity;
        this.dTitle = dialogTitle;
    }

    public SearchableDialog(Activity activity, List<com.ajithvgiri.searchdialog.SearchListItem> searchListItems, String dialogTitle, int style) {
        this.searchListItems = searchListItems;
        this.activity = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }


    /***
     *
     * @param searchItemSelected
     * return selected position & item
     */
    public void setOnItemSelected(OnSearchItemSelected searchItemSelected) {
        this.onSearchItemSelected = searchItemSelected;
    }

    /***
     *
     * show the seachable dialog
     */
    public void show() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(com.ajithvgiri.searchdialog.R.layout.search_dialog_layout, null);
        TextView rippleViewClose = (TextView) view.findViewById(com.ajithvgiri.searchdialog.R.id.close);
        TextView title = (TextView) view.findViewById(com.ajithvgiri.searchdialog.R.id.spinerTitle);
        title.setText(dTitle);
        listView = (ListView) view.findViewById(com.ajithvgiri.searchdialog.R.id.list);

        final EditText searchBox = (EditText) view.findViewById(com.ajithvgiri.searchdialog.R.id.searchBox);
        searchListAdapter = new SearchListAdapter(activity, com.ajithvgiri.searchdialog.R.layout.items_view_layout, com.ajithvgiri.searchdialog.R.id.text1, searchListItems);
        listView.setAdapter(searchListAdapter);
        adb.setView(view);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(com.ajithvgiri.searchdialog.R.id.text1);
                for (int j = 0; j < searchListItems.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(searchListItems.get(j).toString())) {
                        position = j;
                        searchListItem = searchListItems.get(position);
                    }
                }
                try {
                    onSearchItemSelected.onClick(position, searchListItem);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                alertDialog.dismiss();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                List<com.ajithvgiri.searchdialog.SearchListItem> filteredValues = new ArrayList<>();
                for (int i = 0; i < searchListItems.size(); i++) {
                    if (searchListItems.get(i) != null) {
                        SearchListItem item = searchListItems.get(i);
                        if (item.getTitle().toLowerCase().trim().contains(searchBox.getText().toString().toLowerCase().trim())) {
                            filteredValues.add(item);
                        }
                    }
                }
                searchListAdapter = new SearchListAdapter(activity, com.ajithvgiri.searchdialog.R.layout.items_view_layout, com.ajithvgiri.searchdialog.R.id.text1, filteredValues);
                listView.setAdapter(searchListAdapter);
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /***
     *
     * clear the list
     */
    public void clear() {
        this.searchListItems.clear();
    }

    /***
     *
     * refresh the adapter (notifyDataSetChanged)
     */
    public void refresh() {
        searchListAdapter.notifyDataSetChanged();
    }


    public SearchListAdapter getAdapter() {
        return searchListAdapter;
    }
}
