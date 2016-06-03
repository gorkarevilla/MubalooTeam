package com.gorkarevilla.mubalooteam;

import java.util.LinkedList;

/**
 * Team in a Company, inside there are all the members and leaders of the team
 *
 * @Author Gorka Revilla
 */
public class Team {

    //Name of the team
    private String _name;

    //List with Members
    private LinkedList<Member> _memberList;


    /**
     * Constructor, set the Name of the team
     *
     * @param name String the name of the Team
     */
    public Team(String name) {
        set_name(name);
        _memberList = new LinkedList<>();
    }

    /**
     * Add a Member to the team
     *
     * @param m the Member to be added.
     * @return Boolean true if is added correctly
     */
    public boolean addMember(Member m) {
        return _memberList.add(m);
    }

    /**
     * Returns the Team name and the Members
     *
     * @return String with the information of the Team.
     */
    public String toString() {
        return "Team Name: "+get_name()+ " Members: "+get_memberList().toString();
    }



    /*
     * GETTERS AND SETTERS
     */

    /**
     * Returns the name of the team
     *
     * @return String name of the team
     */
    public String get_name() {
        return _name;
    }

    /**
     * Set the name of the team
     *
     * @param _name String the new name
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * Returns the linkedlist of members of the team
     *
     * @return LinkedList of Members of the team
     */
    public LinkedList<Member> get_memberList() {
        return _memberList;
    }

    /**
     * Set the List of members of the team
     *
     * @param _memberList LinkedList with the members of the team
     */
    public void set_memberList(LinkedList<Member> _memberList) {
        this._memberList = _memberList;
    }


}
