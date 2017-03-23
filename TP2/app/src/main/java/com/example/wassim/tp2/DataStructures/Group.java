package com.example.wassim.tp2.DataStructures;


import com.example.wassim.tp2.database.DatabaseAccesObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Louis-Philippe on 3/16/2017.
 */

public class Group {
    private String name;
    private String organisaterName;
    private List<Integer>users;
    private Place[] locations;
    private Events event;
    private int groupId;
    private static Group m_group;
    public static Group getGroup(){return m_group;}

    public static boolean getOrCreateGroup(String name){
        if(false){//TODO seek for database if the group exist
            //TODO fill the m_group with a new group according to database
            m_group = getGroupFromDatabase(name);
            m_group.users.add(User.getUser().getUserId());
            //TODO add current user to group and database
            return true;
        }else{
            m_group = new Group(name);
            return false;
        }
    }

    private Group(String name){
        this.name = name;
        this.event = null;
        this.organisaterName = User.getUser().getUserName();
        this.users = new ArrayList<>();
        users.add(User.getUser().getUserId());//TODO maybe use uniqueID

        //TODO add group to database
        //TODO fill groupID with the database fixedID
    }

    private static Group getGroupFromDatabase(String groupName){
        DatabaseAccesObject dao = new DatabaseAccesObject(activity);
        //gather data from database
        Integer groupId = dao.gatherGroupIdFromGroupName(groupName);
        Integer eventId = dao.gatherEventIdFromGroupId(groupId);
        List<Integer> userIdList = dao.gatherUserIdListFromGroup(groupName);
        Integer[] placeIdList = dao.gatherPlaceIdListFromEventId(eventId);
        //create the group
        Group group = new Group(name,-1 ,null,null,-1);
        return group;
    }
    private Group(String name,int groupID, List<Integer> users, Integer[] locationID, int eventId){
        if(eventId!=-1){
            DatabaseAccesObject dao = new DatabaseAccesObject(activity);
            event =  dao.gatherEventFromEventId(eventId);
        }
        if(locationID!=null) {
            for (int i : locationID) {
                if (i != -1) {
                    //TODO getLocation(i)
                }
            }
        }
        this.name = name;
        this.users = users;
        this.groupId = groupID;
    }

    public void updateGroup(){
        for(Place p : locations){
            p.update();
        }
        if(event!=null){
            event.update();
        }
        //TODO pull info from database
    }

    public void createEvent(int locationNumber, String name, String description, Date start, Date end){
        event = new Events(name,locations[locationNumber],start,end, users,description);
        //TODO Send event to users
    }
    public void recordUserAnswerForLocation(String user, List<Integer> notes){
        if(users.contains(user)){
            for(int i =0;i<notes.size();i++){
                locations[i].addScore(user,notes.get(i));
            }
        }
    }
    public void recordUserAnswerForEvent(Integer userId, AnswerToEventEnum answer){
        event.setUserAnswer(userId,answer);
    }
    public boolean isAdmin(){
        return organisaterName==User.getUser().getUserName();
    }
}
