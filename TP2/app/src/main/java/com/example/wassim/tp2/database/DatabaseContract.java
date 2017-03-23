package com.example.wassim.tp2.database;

import android.provider.BaseColumns;

/**
 * Created by gamyot on 2017-03-22.
 *
 * Class that represents the contract for the Database Schema:
 * A contract class is a container for constants that define names for URIs,
 * tables, and columns. The contract class allows you to use the same constants
 * across all the other classes in the same package. This lets you change
 * a column name in one place and have it propagate throughout your code.
 *
 * Source: https://developer.android.com/training/basics/data-storage/databases.html
 *         http://stackoverflow.com/questions/17451931/how-to-use-a-contract-class-in-android

 */

public final class DatabaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "Tp2Database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String BLOB_TYPE          = " BLOB";
    private static final String INTEGER_TYPE       = " INTEGER";
    private static final String DATE_TYPE          = " DATETIME";
    private static final String FOREIGN_KEY        = " FOREIGN KEY (";
    private static final String PRIMARY_KEY        = " PRIMARY KEY";
    private static final String REFERENCES         = ") REFERENCES";
    private static final String NOT_NULL           = " NOT NULL";
    private static final String COMMA_SEP          = ",";

    /**
     * To prevent someone from accidentally instantiating the contract class,
     * make the constructor private.
     */
    private DatabaseContract(){}

     /* Inner class that defines the table contents */
    public static class GroupTable implements BaseColumns {
        public static final String TABLE_NAME = "GroupActivity";
        public static final String GROUP_NAME_COL1 = "name";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE+ PRIMARY_KEY + COMMA_SEP +
                GROUP_NAME_COL1 + TEXT_TYPE +" )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String NAME_COL1 = "name";
        public static final String PHOTO_COL2 = "photo";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + " PRIMARY KEY," +
                NAME_COL1 + TEXT_TYPE + COMMA_SEP +
                PHOTO_COL2 + BLOB_TYPE +" )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class RoleTable implements BaseColumns {
        public static final String TABLE_NAME = "Role";
        public static final String USER_REFERENCE_COL1 = "userId";
        public static final String GROUP_REFERENCE_COL2 = "groupId";
        public static final String ROLE_COL3 = "role";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                USER_REFERENCE_COL1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                GROUP_REFERENCE_COL2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ROLE_COL3 + TEXT_TYPE +
                FOREIGN_KEY + USER_REFERENCE_COL1 + REFERENCES + UserTable._ID+
                FOREIGN_KEY + GROUP_REFERENCE_COL2 + REFERENCES + GroupTable._ID +
                PRIMARY_KEY + "(" + USER_REFERENCE_COL1 + COMMA_SEP + GROUP_REFERENCE_COL2 + ")" +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class PlaceScoreTable implements BaseColumns {
        public static final String TABLE_NAME = "PlaceScrore";
        public static final String USER_REFERENCE_COL1 = "userId";
        public static final String PLACE_REFERENCE_COL2 = "placeId";
        public static final String SCORE_COL3 = "score";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                USER_REFERENCE_COL1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                PLACE_REFERENCE_COL2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                SCORE_COL3 + INTEGER_TYPE +
                FOREIGN_KEY + USER_REFERENCE_COL1 + REFERENCES + UserTable._ID+
                FOREIGN_KEY + PLACE_REFERENCE_COL2 + REFERENCES + PlaceTable._ID +
                PRIMARY_KEY + "(" + USER_REFERENCE_COL1 + COMMA_SEP + PLACE_REFERENCE_COL2 + ")" +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

   public static class PlaceTable implements BaseColumns {
       public static final String TABLE_NAME = "Place";
       public static final String NAME_COL1 = "name";
       public static final String PHOTO_NAME_COL2 = "photo";

       public static final String CREATE_TABLE = "CREATE TABLE " +
               TABLE_NAME + " (" +
               _ID + INTEGER_TYPE +" PRIMARY KEY"+ COMMA_SEP +
               NAME_COL1 + TEXT_TYPE + COMMA_SEP +
               PHOTO_NAME_COL2 + BLOB_TYPE +
               " )";
       public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

   }

    public static class EventTable implements BaseColumns {
        public static final String TABLE_NAME = "Event";
        public static final String GROUP_REFERENCE_COL1 = "groupId";
        public static final String PLACE_REFERENCE_COL2 = "placeId";
        public static final String NAME_COL3 = "name";
        public static final String START_TIME_COL4 = "startTime";
        public static final String END_TIME_COL5 = "endTime";
        public static final String DESCRIPTION_COL6 = "description";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE+ PRIMARY_KEY + COMMA_SEP +
                GROUP_REFERENCE_COL1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                PLACE_REFERENCE_COL2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                NAME_COL3 + TEXT_TYPE + COMMA_SEP +
                START_TIME_COL4 + DATE_TYPE + COMMA_SEP +
                END_TIME_COL5 + DATE_TYPE + COMMA_SEP +
                DESCRIPTION_COL6 + TEXT_TYPE +
                FOREIGN_KEY + GROUP_REFERENCE_COL1 + REFERENCES + GroupTable._ID +
                FOREIGN_KEY + PLACE_REFERENCE_COL2 + REFERENCES + PlaceTable._ID +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class EventParticipationTable implements BaseColumns {
        public static final String TABLE_NAME = "EventParticipation";
        public static final String USER_REFERENCE_COL1 = "userId";
        public static final String PLACE_REFERENCE_COL2 = "placeId";
        public static final String SCORE_COL3 = "answer";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                USER_REFERENCE_COL1 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                PLACE_REFERENCE_COL2 + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                SCORE_COL3 + INTEGER_TYPE +
                FOREIGN_KEY + USER_REFERENCE_COL1 + REFERENCES + UserTable._ID+
                FOREIGN_KEY + PLACE_REFERENCE_COL2 + REFERENCES + PlaceTable._ID +
                PRIMARY_KEY + "(" + USER_REFERENCE_COL1 + COMMA_SEP + PLACE_REFERENCE_COL2 + ")" +
                " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
