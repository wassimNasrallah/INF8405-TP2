package com.example.wassim.tp2.database;


import android.content.Context;


/**
 * Created by gamyot on 2017-03-22.
 */

public class DatabaseTest {

    public static void groupDaoTest1(Context context){
        GroupDao groupDao = new GroupDao(context);
        groupDao.writeInGroupTable("newDatabase");
        groupDao.writeInGroupTable("newDatabase");
        System.out.println(groupDao.gatherGroupTable("newDatabase").toString());
    }
}
