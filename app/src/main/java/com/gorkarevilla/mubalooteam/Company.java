package com.gorkarevilla.mubalooteam;

import java.util.LinkedList;

/**
 * Object with all the information about the Company
 *
 * @Author Gorka Revilla
 */
public class Company {

    private String _name;
    private Member _ceo;

    private LinkedList<Team> _Teams;

    /**
     * Constructor of the Company
     *
     * @param theName The Name of the Company
     *
     */
    public Company ( String theName ) {

        set_name(theName);

        _Teams = new LinkedList<Team>();
    }

    /**
     * Returns the Company information in a String
     *
     * @return the the Company in a String
     */
    public String toString() {
        return "Company Name: "+get_name()+ " CEO: "+get_ceo().toString()+ " Teams : "+get_Teams().toString();
    }


    /**
     * Add a Team into the Company
     *
     * @param t The Team to add to the Company
     * @return true if the team has been added correctly, false if not.
     */
    public boolean addTeam(Team t) {
        return _Teams.add(t);
    }




    /*
     *  GETTERS AND SETTERS
     */

    /**
     * Returns the name of the Company
     *
     * @return Name of the Company
     */
    public String get_name() {
        return _name;
    }

    /**
     * Set the name of the Company
     *
     */
    public void set_name(String _name) {
        this._name = _name;
    }

    /**
     * Returns the LinkedList of Teams in the Company
     *
     * @return LinkedList of Teams
     *
     */
    public LinkedList<Team> get_Teams() {
        return _Teams;
    }

    /**
     * Define the List of Teams of the Company
     *
     * @param _Teams LinkedList of teams in the Company
     */
    public void set_Teams(LinkedList<Team> _Teams) {
        this._Teams = _Teams;
    }

    /**
     * Returns the Member CEO of the Company
     *
     * @return Member with the info of the CEO of the Company
     */
    public Member get_ceo() {
        return _ceo;
    }

    /**
     * Define the CEO of the Company
     *
     * @param _ceo Member CEO of the Company
     */
    public void set_ceo(Member _ceo) {
        this._ceo = _ceo;
    }

}
