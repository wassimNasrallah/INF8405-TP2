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
    public String getName(){return name;}
    private String organisaterName;
    public String getOrganisaterName(){return organisaterName;}
    private List<User>users;
    public List<User> getUserList(){return users;}
    private Place[] locations;
    public Place[]getLocations(){return locations;}
    private Events event;
    public Events getEvent(){return event;}
    private int groupId;
    private static Group m_group;
    public static Group getGroup(){return m_group;}

    public static boolean getOrCreateGroup(String groupName){
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        Group foundGroup = DatabaseAccesObject.gatherGroup(groupName);
        if(foundGroup != null){
            m_group = foundGroup;
            return true;
        }else{
            m_group = new Group(groupName);
            return false;
        }
    }

    private Group(String name){
        this.name = name;
        this.event = null;
        this.organisaterName = User.getUser().getUserName();
        this.users = new ArrayList<>();
        users.add(User.getUser());

        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        this.groupId =  dao.persistGroup(this);
    }


    public Group(String name,int groupID, List<User> users, Integer[] locationID, int eventId){
        DatabaseAccesObject dao = new DatabaseAccesObject(ContextHolder.getMainContext());
        if(eventId!=-1){
            this.event =  dao.gatherEvent(eventId);
        }
        if(locationID!=null) {
            ArrayList<Place> places= new ArrayList<>();
            for (int i : locationID) {
                if (i != -1) {
                    places.add(dao.gatherPlace(i));
                }
            }
            this.locations = places.toArray(locations);
        }
        this.name = name;
        this.users = users;
        this.groupId = groupID;
    }

    public void update(){
        Settings.getInstance().update();
        updateGroup(DatabaseAccesObject.gatherGroup(name));

    }

    private static void updateGroup(Group outdatedGroup){
        Group updatedGroup = DatabaseAccesObject.gatherGroup(outdatedGroup.name);
        outdatedGroup.organisaterName = updatedGroup.organisaterName;
        outdatedGroup.locations = updatedGroup.locations;
        outdatedGroup.event = updatedGroup.event;
        outdatedGroup.users = updatedGroup.users;
    }



    public void createEvent(int locationNumber, String name, String description, Date start, Date end){
        event = new Events(name,locations[locationNumber],start,end, users,description);
        //TODO Send event to users
    }
    public void recordUserAnswerForLocation(List<Integer> notes) {
        for (int i = 0; i < notes.size(); i++) {
            locations[i].addScore(User.getUser().getUserId(), notes.get(i));
        }
    }
    public void recordUserAnswerForEvent(Integer userId, AnswerToEventEnum answer){
        event.setUserAnswer(userId,answer);
    }
    public boolean isAdmin(){
        return organisaterName==User.getUser().getUserName();
    }
}
