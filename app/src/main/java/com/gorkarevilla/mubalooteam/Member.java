package com.gorkarevilla.mubalooteam;

/**
 * Member of the Company, can be a Leader or the CEO
 *
 * @Author Gorka Revilla
 */
public class Member {

    private int _id;
    private String _firstName;
    private String _lastName;
    private String _role;
    private Boolean _isLeader;
    private String _imageURL;

    /**
     * Constructor of the Member
     *
     * @param id int with the ID of the Member
     * @param firstName String with the First Name of the Member
     * @param lastName String with the Last Name
     * @param role String the Role in the Company of the Member
     * @param isLeader Boolean true if is a Leader
     * @param imageURL String URL of the image of the Member
     *
     */
    public Member (int id, String firstName, String lastName, String role, Boolean isLeader, String imageURL ) {
        set_id(id);
        set_firstName(firstName);
        set_lastName(lastName);
        set_role(role);
        set_isLeader(isLeader);
        set_imageURL(imageURL);
    }

    /**
     * Returns a String with the first name and the last name of the Member
     *
     * @return String
     */
    public String toString() {
        return get_firstName()+" "+get_lastName();
    }




    /*
     * GETTERS AND SETTERS
     */

    /**
     * Returns the ID of the Member
     *
     * @return int ID of the Member
     */
    public int get_id() {
        return _id;
    }

    /**
     * Set the ID of the Member
     *
     * @param _id int of the Member
     */
    public void set_id(int _id) {
        this._id = _id;
    }

    /**
     * Returns the first name of the Member
     *
     * @return String with the First name of the name
     */
    public String get_firstName() {
        return _firstName;
    }

    /**
     * Set the first name of the Member
     *
     * @param _firstName String to set the First name to the Member
     */
    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    /**
     *
     * @return
     */
    public String get_lastName() {
        return _lastName;
    }

    /**
     * Set the last name of the Member
     *
     * @param _lastName String to set the Last name to the Member
     */
    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    /**
     * Returns the Role of the Member
     *
     * @return String with the Role of the Member
     */
    public String get_role() {
        return _role;
    }

    /**
     * Returns if the Member is a Leader of a team
     *
     * @return Boolean true if is a Leader
     */
    public Boolean get_isLeader() {
        return _isLeader;
    }

    /**
     * Set True if the Member is a Leader, False if not
     *
     * @param _isLeader Boolean true if is leader
     */
    public void set_isLeader(Boolean _isLeader) {
        this._isLeader = _isLeader;
    }

    /**
     * Set the role of the Membeer
     *
     * @param _role String with the Role of the Member
     */
    public void set_role(String _role) {
        this._role = _role;
    }

    /**
     * Returns the URL with the image of the Member
     *
     * @return String with the URL
     */
    public String get_imageURL() {
        return _imageURL;
    }

    /**
     * Set the URL of the image of the Member
     *
     * @param _imageURL String with the URL of the image
     */
    public void set_imageURL(String _imageURL) {
        this._imageURL = _imageURL;
    }
}
