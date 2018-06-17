package com.example.android.bikesinventory.data;

import android.provider.BaseColumns;

public class Contract {
    private Contract() {}

    public static final class BikeEntry implements BaseColumns {
        public final static String TABLE_NAME = "bikes";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_PRICE = "price";
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}
