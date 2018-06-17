package com.example.android.bikesinventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bikesinventory.data.Contract.BikeEntry;
import com.example.android.bikesinventory.data.DbHelper;

public class CatalogActivity extends AppCompatActivity {

    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new DbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BikeEntry._ID,
                BikeEntry.COLUMN_PRODUCT_NAME,
                BikeEntry.COLUMN_SUPPLIER_NAME,
                BikeEntry.COLUMN_QUANTITY,
                BikeEntry.COLUMN_SUPPLIER_PHONE,
                BikeEntry.COLUMN_PRICE
        };

        Cursor cursor = db.query(
                BikeEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_bike);

        try {
            displayView.setText("The bikes table contains " + cursor.getCount() + " bikes.\n\n");
            displayView.append(BikeEntry._ID + " - " +
                    BikeEntry.COLUMN_PRODUCT_NAME + " _ " +
                    BikeEntry.COLUMN_SUPPLIER_NAME + " _ " +
                    BikeEntry.COLUMN_QUANTITY + " _ " +
                    BikeEntry.COLUMN_SUPPLIER_PHONE + " _ " +
                    BikeEntry.COLUMN_PRICE + "\n"
            );

            int idColumnIndex = cursor.getColumnIndex(BikeEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(BikeEntry.COLUMN_PRODUCT_NAME);
            int suppplierNameColumnIndex = cursor.getColumnIndex(BikeEntry.COLUMN_SUPPLIER_NAME);
            int quantityColumnIndex = cursor.getColumnIndex(BikeEntry.COLUMN_QUANTITY);
            int suppplierPhoneColumnIndex = cursor.getColumnIndex(BikeEntry.COLUMN_SUPPLIER_PHONE);
            int priceColumnIndex = cursor.getColumnIndex(BikeEntry.COLUMN_PRICE);

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(productNameColumnIndex);
                String currentSupplierName = cursor.getString(suppplierNameColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                int currentPhone = cursor.getInt(suppplierPhoneColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);

                displayView.append(("\n" + currentID + " - " +
                        currentName + " _ " +
                        currentSupplierName + " _ " +
                        currentQuantity + " _ " +
                        currentPhone + " _ " +
                        currentPrice));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBike () {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BikeEntry.COLUMN_PRODUCT_NAME, "Cannondale");
        values.put(BikeEntry.COLUMN_SUPPLIER_NAME, "AmiBike");
        values.put(BikeEntry.COLUMN_QUANTITY, 0);
        values.put(BikeEntry.COLUMN_SUPPLIER_PHONE, 0);
        values.put(BikeEntry.COLUMN_PRICE, 0);

        long newRowid = db.insert(BikeEntry.TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New row ID" + newRowid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert data" menu option
            case R.id.action_save:
                insertBike();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all bikes" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}