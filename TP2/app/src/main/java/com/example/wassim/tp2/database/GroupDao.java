package com.example.wassim.tp2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wassim.tp2.DataStructures.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gamyot
 */
public class GroupDao {
    private Context context;
    DatabaseHelper databaseHelper;

    public GroupDao(Context context){
        context = context;
        databaseHelper = new DatabaseHelper(context);
    }
    //raw queries
    public void rawGatherGroupTable(String groupName){

        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        String query =
                "SELECT User.name, User.photo, Role.role " +
                "FROM Group" +
                "JOIN Role ON Group.groupId = Role.groupId" +
                "JOIN User ON User.userId = Role.groupId" +
                "WHERE Group.name = ?";
        String[] selectionArgs = {groupName};
        Cursor cursor = dataBase.rawQuery(query,selectionArgs);
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String userName = cursor.getString(cursor.getColumnIndex("Group.name"));
                String userRole = cursor.getString(cursor.getColumnIndex("Role.role"));
                byte[] userPhotoByte = cursor.getBlob(cursor.getColumnIndex("User.photo"));
                Bitmap userPhoto = BitmapFactory.decodeByteArray(userPhotoByte, 0, userPhotoByte.length);

                User newUser = new User(userName,userPhoto);
                cursor.moveToNext();
            }
        }
        cursor.close();

    }


    //queries
    public List gatherGroupTable(String groupName){
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        String[] projection = {
                DatabaseContract.GroupTable._ID,
                DatabaseContract.GroupTable.GROUP_NAME_COL1,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.GroupTable.GROUP_NAME_COL1+ " = ?";
        String[] selectionArgs = {groupName};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.GroupTable._ID + " DESC";

        //Do the querry
        Cursor cursor = dataBase.query(
                DatabaseContract.GroupTable.TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseContract.GroupTable._ID));
            itemIds.add(itemId);
        }
        cursor.close();
        return itemIds;


    }


    public long writeInGroupTable(String groupName){
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GroupTable.GROUP_NAME_COL1, groupName);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = dataBase.insert(DatabaseContract.GroupTable.TABLE_NAME, null, values);
        return newRowId;
    }

    public void deleteFromGroupTable(String groupName){
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();
        // Define 'where' part of query.
        String selection = DatabaseContract.GroupTable.GROUP_NAME_COL1 + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { groupName };
        // Issue SQL statement.
        dataBase.delete(DatabaseContract.GroupTable.TABLE_NAME, selection, selectionArgs);

    }

    public void updateGroupTable(String oldGroupName, String newGroupName){
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GroupTable.GROUP_NAME_COL1, newGroupName);

        // Which row to update, based on the title
        String selection = DatabaseContract.GroupTable.GROUP_NAME_COL1 + " LIKE ?";
        String[] selectionArgs = { oldGroupName };

        int count = dataBase.update(
                DatabaseContract.GroupTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }



    //@Override
    protected void onDestroy() {
        databaseHelper.close();
        //super.onDestroy();
    }


}
