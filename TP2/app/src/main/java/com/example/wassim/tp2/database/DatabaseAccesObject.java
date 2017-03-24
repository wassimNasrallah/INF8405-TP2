package com.example.wassim.tp2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import com.example.wassim.tp2.DataStructures.AnswerToEventEnum;
import com.example.wassim.tp2.DataStructures.ContextHolder;
import com.example.wassim.tp2.DataStructures.Events;
import com.example.wassim.tp2.DataStructures.Group;
import com.example.wassim.tp2.DataStructures.Place;
import com.example.wassim.tp2.DataStructures.User;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gamyot on 2017-03-22.
 */

public class DatabaseAccesObject {
    private Context context;
    private DatabaseHelper databaseHelper;

    public DatabaseAccesObject(Context context) {
        context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    //@Override
    protected void onDestroy() {
        databaseHelper.close();
        //super.onDestroy();
    }

    //get int eventID
    public Integer gatherEventIdFromGroupId(Integer groupId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        Integer enventId = null;
        String userIdListQuery =
                "SELECT Event.eventId From Event WHERE Event.groupId = ?";
        String[] selectionArgs = {groupId.toString()};
        Cursor cursor = dataBase.rawQuery(userIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                enventId = cursor.getInt(cursor.getColumnIndex("Event.eventId"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return enventId;
    }

    public List<Integer> gatherUserIdListFromGroup(String groupName) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        List<Integer> userIdList = new ArrayList<>();
        String userIdListQuery =
                "SELECT User.userId " +
                        "FROM User " +
                        "JOIN Role ON Group.groupId = Role.groupId " +
                        "JOIN User ON User.userId = Role.userId " +
                        "WHERE Group.name = ?";
        String[] selectionArgs = {groupName};
        Cursor cursor = dataBase.rawQuery(userIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer userName = cursor.getInt(cursor.getColumnIndex("User.userId"));
                userIdList.add(userName);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return userIdList;
    }
    public List<User> gatherUserList(String groupName) {
        List<User> userList = new ArrayList<>();
        List<Integer> userIdList = gatherUserIdListFromGroup(groupName);
        for(Integer userId :userIdList){
            userList.add(gatherUser(userId));
        }
        return userList;
    }

    //get the groupID
    public Integer gatherGroupIdFromGroupName(String groupName) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        Integer groupId = null;
        String userIdListQuery = "SELECT Group.groupId FROM Group WHERE Group.name = ?";
        String[] selectionArgs = {groupName};
        Cursor cursor = dataBase.rawQuery(userIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer userName = cursor.getInt(cursor.getColumnIndex("Group.groupId"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return groupId;
    }

    public Integer[] gatherPlaceIdListFromEventId(Integer eventId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        List<Integer> placeIdList = new ArrayList<>();
        String placeIdListQuery =
                "SELECT Place.placeId " +
                        "FROM Group " +
                        "JOIN Role ON Group.groupId = Role.groupId " +
                        "JOIN User ON User.userId = Role.userId " +
                        "WHERE Event.eventId = ?";
        String[] selectionArgs = {eventId.toString()};
        Cursor cursor = dataBase.rawQuery(placeIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer userName = cursor.getInt(cursor.getColumnIndex("Place.placeId"));
                placeIdList.add(userName);
                cursor.moveToNext();
            }
        }
        cursor.close();
        Integer[] result = new Integer[placeIdList.size()];
        return placeIdList.toArray(result);
    }


    public Events gatherEvent(Integer eventId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        String eventName = null;
        Integer placeId = null;
        Place eventPlace = null;
        Date startDate = null;
        Date endDate = null;
        String description = null;
        String placeIdListQuery =
                "SELECT Event.placeId, Event.name, Even.startTime, Event.endTime, Event.description " +
                        "FROM Group " +
                        "JOIN Role ON Group.groupId = Role.groupId " +
                        "JOIN User ON User.userId = Role.userId " +
                        "WHERE Event.eventId = ?";
        String[] selectionArgs = {eventId.toString()};
        Cursor cursor = dataBase.rawQuery(placeIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                placeId = cursor.getInt(cursor.getColumnIndex("Event.placeId"));
                eventName = cursor.getString(cursor.getColumnIndex("Event.name"));
                String startDateString = cursor.getString(cursor.getColumnIndex("Event.startTime"));
                String endDateString = cursor.getString(cursor.getColumnIndex("Event.endTime"));
                description = cursor.getString(cursor.getColumnIndex("Event.description"));
                startDate = extractDate(startDateString);
                endDate = extractDate(endDateString);
                cursor.moveToNext();
            }
        }
        cursor.close();
        if (isNotNull(placeId)) {
            eventPlace = gatherPlace(placeId);
        }
        HashMap<Integer, AnswerToEventEnum> participationAnswerMap = gatherEventParticipationFromEventId(eventId);
        //TODO check for null parameters
        return new Events(eventName, eventPlace, startDate, endDate, participationAnswerMap, description);
    }


    public User gatherUser(Integer userId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        String userName = null;
        Bitmap photo = null;
        String placeIdListQuery =
                "SELECT User.name, User.photo " +
                        "FROM User " +
                        "WHERE User.userId = ?";
        String[] selectionArgs = {userId.toString()};
        Cursor cursor = dataBase.rawQuery(placeIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                userName = cursor.getString(cursor.getColumnIndex("User.name"));
                byte[] photoByte = cursor.getBlob(cursor.getColumnIndex("User.photo"));
                photo = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return new User(userName, photo);
    }


    public HashMap<Integer, AnswerToEventEnum> gatherEventParticipationFromEventId(Integer eventId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        HashMap<Integer, AnswerToEventEnum> scoreMap = new HashMap<>();
        String placeScoreListQuery =
                "SELECT EventParticipation.userId, EventParticipation.answer " +
                        "FROM Event " +
                        "JOIN EventParticipation ON Event.eventId = EventParticipation.eventId " +
                        "WHERE Event.eventId = ?";
        String[] selectionArgs = {eventId.toString()};

        Cursor cursor = dataBase.rawQuery(placeScoreListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer userId = cursor.getInt(cursor.getColumnIndex("EventParticipation.userId"));
                String answer = cursor.getString(cursor.getColumnIndex("EventParticipation.answer"));
                scoreMap.put(userId, AnswerToEventEnum.resolveAnswer(answer));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return scoreMap;
    }

    public Place gatherPlace(Integer placeId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        HashMap<Integer, Integer> scoreMap = gatherScoreMapFromPlaceId(placeId);
        String placeName = "";
        Location placeLocation = null;
        Bitmap image = null;
        String placeIdListQuery =
                "SELECT Place.photo, Place.name, Pace.latitude, Place.longitude " +
                        "FROM Place " +
                        "JOIN PlaceScore ON Place.placeId = PlaceScore.scoreId " +
                        "WHERE Place.placeId = ?";
        String[] selectionArgs = {placeId.toString()};
        Cursor cursor = dataBase.rawQuery(placeIdListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                byte[] placePhotoByte = cursor.getBlob(cursor.getColumnIndex("Place.photo"));
                image = BitmapFactory.decodeByteArray(placePhotoByte, 0, placePhotoByte.length);
                placeName = cursor.getString(cursor.getColumnIndex("Place.name"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("Place.longitude"));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("Place.latitude"));
                placeLocation = new Location(placeName);
                placeLocation.setLatitude(latitude);
                placeLocation.setLongitude(longitude);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return new Place(image, placeLocation, scoreMap);
    }


    public HashMap<Integer, Integer> gatherScoreMapFromPlaceId(Integer placeId) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        HashMap<Integer, Integer> scoreMap = new HashMap<>();
        String placeScoreListQuery =
                "SELECT PlaceScore.userId, PlaceScore.score " +
                        "FROM Place " +
                        "JOIN PlaceScore ON Place.placeId = PlaceScore.scoreId " +
                        "WHERE Place.placeId = ?";
        String[] selectionArgs = {placeId.toString()};
        Cursor cursor = dataBase.rawQuery(placeScoreListQuery, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer userId = cursor.getInt(cursor.getColumnIndex("PlaceScore.userId"));
                Integer score = cursor.getInt(cursor.getColumnIndex("PlaceScore.score"));
                scoreMap.put(userId, score);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return scoreMap;
    }

    public int updateLocation(Integer userId, Location newLocation) {
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();

        // Which row to update, based on the title
        String selection = DatabaseContract.UserTable.USER_ID+ " LIKE ?";
        String[] selectionArgs = { userId.toString() };

        // New value for one column
        ContentValues values = new ContentValues();
        Double latitude = newLocation.getLatitude();
        Double longitude = newLocation.getLongitude();
        values.put(DatabaseContract.UserTable.LATITUDE_COL3, latitude);
        values.put(DatabaseContract.UserTable.LONGITUDE_COL4, longitude);

        int count = dataBase.update(
                DatabaseContract.GroupTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;

    }

    public static Group gatherGroup(String groupName){
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        //gather data from database
        List<User> userList = dao.gatherUserList(groupName);
        Integer groupId = dao.gatherGroupIdFromGroupName(groupName);
        Integer eventId = null;
        Integer[] placeIdList = null;
        if(isNotNull(groupId)) {
            eventId = dao.gatherEventIdFromGroupId(groupId);
        }else{
            return null;
        }
        if (isNotNull(eventId)) {
            placeIdList = dao.gatherPlaceIdListFromEventId(eventId);
        }

        Group group = new Group(groupName,groupId ,userList,placeIdList ,eventId);
        return group;
    }


    public long persistGroup(Group group){
        long newGroupId = insertGroup(group.getName());
        long admin = gatherUserId(group.getOrganisaterName());
        insertRole(newGroupId,admin,"admin");
        return newGroupId;
    }



    private void insertRole(long groupId, long userId, String roleName) {
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RoleTable.GROUP_REFERENCE_COL2, groupId);
        values.put(DatabaseContract.RoleTable.USER_REFERENCE_COL1, userId);
        values.put(DatabaseContract.RoleTable.ROLE_COL3, roleName);
        // Insert the new row, returning the primary key value of the new row
        dataBase.insert(DatabaseContract.RoleTable.TABLE_NAME, null, values);
    }

    private int gatherUserId(String userName) {
        SQLiteDatabase dataBase = databaseHelper.getReadableDatabase();
        int userId = -1;
        String query ="SELECT User.userId " +
                        "FROM User " +
                        "WHERE User.name = ?";
        String[] selectionArgs = {userName.toString()};
        Cursor cursor = dataBase.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                userId =  cursor.getInt(cursor.getColumnIndex("User.userId"));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return userId;
    }


    public long insertGroup(String groupName){
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GroupTable.GROUP_NAME_COL1, groupName);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = dataBase.insert(DatabaseContract.GroupTable.TABLE_NAME, null, values);
        return newRowId;
    }

    public long insertPlace(Place place) {
        SQLiteDatabase dataBase = databaseHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        byte[] photoByte = getBytes(place.getLocationImage());
        values.put(DatabaseContract.PlaceTable.PHOTO_COL2, photoByte);
        values.put(DatabaseContract.PlaceTable.LATITUDE_COL3, place.getLocationLat());
        values.put(DatabaseContract.PlaceTable.LONGITUDE_COL4, place.getLocationLon());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = dataBase.insert(DatabaseContract.PlaceTable.TABLE_NAME, null, values);
        return newRowId;
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    private static boolean isNotNull(Object o) {
        return o != null;
    }

    private Date extractDate(String rawDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date resultingDate;
        try {
            resultingDate = originalFormat.parse(rawDate);
            return resultingDate;
        } catch (ParseException e) {
            System.out.println("Error gathering the event date form database" + e.getMessage());
            return null;
        }


    }


    public void persistScore(Integer userId, Object o) {
    }
}
