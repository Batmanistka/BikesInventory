package com.example.android.bikesinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bikesinventory.data.Contract.BikeEntry;
import com.example.android.bikesinventory.data.DbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mProductNameEditText;

    private EditText mSupplierEditText;

    private EditText mQuantity;

    private EditText mPrice;

    private EditText mSupplierPhoneEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mSupplierEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mQuantity = (EditText) findViewById(R.id.edit_quantity);
        mPrice = (EditText) findViewById(R.id.edit_price);
        mSupplierPhoneEditText = (EditText) findViewById(R.id.edit_supplier_phone);
    }

    private void insertBike ()   {
        String nameString = mProductNameEditText.getText().toString().trim();
        String supplierNameString = mSupplierEditText.getText().toString().trim();
        String quantityString = mQuantity.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String priceString = mPrice.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        String supplierPhoneString = mSupplierPhoneEditText.getText().toString().trim();
        int supplierPhone = Integer.parseInt(supplierPhoneString);

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BikeEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(BikeEntry.COLUMN_SUPPLIER_NAME , supplierNameString);
        values.put(BikeEntry.COLUMN_QUANTITY, quantity);
        values.put(BikeEntry.COLUMN_PRICE, price);
        values.put(BikeEntry.COLUMN_SUPPLIER_PHONE, supplierPhone);

        long newRowId = db.insert(BikeEntry.TABLE_NAME, null, values);

        if (newRowId == -1){
            Toast.makeText(this, "Error with saving bike", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bike saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //save bike to database
                insertBike();
                //exit activity
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
