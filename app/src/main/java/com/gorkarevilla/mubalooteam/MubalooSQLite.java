package com.gorkarevilla.mubalooteam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.LineNumberReader;
import java.util.LinkedList;

/**
 * Definition of the SQLite DB.
 *
 * Structure:
 *
 * Is going to be only one table (Member). This table is going to have all
 * the info about the members and in which team they are (can be null for the CEO).
 *
 * Each SQL posible has his own procedure
 *
 * @Author Gorka Revilla
 */
public class MubalooSQLite {


    //DB Metainfo
    public static final String MEMBER_TABLE_NAME = "Member";
    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";
    public static final String BOOLEAN_TYPE = "integer"; //Booleans en SQLite are Integers 0/1

    //Member Table
    public static class ColumnMember{
        public static final String ID = BaseColumns._ID;
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String ROLE = "role";
        public static final String TEAMLEADER = "teamleader";
        public static final String PROFILEIMG = "profileimg";
        public static final String TEAMNAME = "teamname";
    }


    //Member Table Creation Script
    public static final String CREATE_MEMBER_SCRIPT =
            "create table "+MEMBER_TABLE_NAME+"(" +
                    ColumnMember.ID+" "+INT_TYPE+"primary key," +
                    ColumnMember.FIRSTNAME+" "+STRING_TYPE+" not null," +
                    ColumnMember.LASTNAME+" "+STRING_TYPE+" not null," +
                    ColumnMember.ROLE+" "+STRING_TYPE+"," +
                    ColumnMember.TEAMLEADER+" "+BOOLEAN_TYPE+" not null," +
                    ColumnMember.PROFILEIMG+" "+STRING_TYPE+","+
                    ColumnMember.TEAMNAME+" "+STRING_TYPE+")";

    //Count number of entries in the table MEMBER
    public static final String COUNT_MEMBERS =
            "select count (*) from "+MEMBER_TABLE_NAME;

    //Select to get the CEO
    public static final String SELECT_CEO =
            "select "+
                    ColumnMember.ID+", "+
                    ColumnMember.FIRSTNAME+", "+
                    ColumnMember.LASTNAME+", "+
                    ColumnMember.PROFILEIMG+" "+
                    "from "+
                    MEMBER_TABLE_NAME +
                    " where "+
                    ColumnMember.ROLE+"=\"CEO\"";


    //Select to get all the Teams
    public static final String SELECT_TEAMS =
            "select "+
                    ColumnMember.TEAMNAME+" "+
                    "from "+
                    MEMBER_TABLE_NAME +
                    " where "+
                    ColumnMember.TEAMNAME+
                    " <> \"CEO\"" +
                    " group by "+
                    ColumnMember.TEAMNAME;

    //Select to get all the members of a team, , in the final of the string should by attach the name of the team
    public static final String SELECT_MEMBERS =
            "select "+
                    ColumnMember.ID+", "+
                    ColumnMember.FIRSTNAME+", "+
                    ColumnMember.LASTNAME+", "+
                    ColumnMember.ROLE+", "+
                    ColumnMember.TEAMLEADER+", "+
                    ColumnMember.PROFILEIMG+" "+
                    "from "+
                    MEMBER_TABLE_NAME +
                    " where "+
                    ColumnMember.TEAMNAME+" = ";

    //SQLite DB
    private SQLiteDatabase database;

    /**
     * Constructor, Initalice all the Elements and opens the DB
     *
     * @param context
     */
    public MubalooSQLite(Context context) {
        //DB instance
        MubalooReaderDbHelper openHelper = new MubalooReaderDbHelper(context);
        database = openHelper.getWritableDatabase();
    }


    /**
     * Return is the table Members has data
     *
     * @return Boolean true if the DB has any Member
     */
    public boolean hasData() {
        Cursor cs = database.rawQuery(COUNT_MEMBERS,null);
        cs.moveToFirst();

        //true if >0
        return (cs.getInt(0)>0);
    }

    /**
     * Remove all users and groups from database.
     *
     */
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        database.delete(MEMBER_TABLE_NAME, null, null);
    }

    /**
     * Add a Member to the SQLite
     *
     * @param id int Id of the Member
     * @param firstname String First name of the Member
     * @param lastname String Last name of the Member
     * @param role String role of the Member
     * @param teamleader Boolean true if is a Leader
     * @param profileimg String URL with the image of the Member
     * @param teamname String name of the Team
     *
     * @return true if the Member has been inserted correctly
     *
     */
    public boolean addMember(int id,String firstname,String lastname,String role,Boolean teamleader,String profileimg,String teamname) {

        //Change from Boolean to int to insert in the SQLite
         int leader=0;
        if(teamleader) leader = 1;

        //Insert
        ContentValues values = new ContentValues();
        values.put(ColumnMember.ID,id);
        values.put(ColumnMember.FIRSTNAME,firstname);
        values.put(ColumnMember.LASTNAME,lastname);
        values.put(ColumnMember.ROLE,role);
        values.put(ColumnMember.TEAMLEADER,teamleader);
        values.put(ColumnMember.PROFILEIMG,profileimg);
        values.put(ColumnMember.TEAMNAME,teamname);


        //Returns the number of inserts, should be >0
        return (database.insert(MEMBER_TABLE_NAME,null,values) > 0);
    }

    /**
     * Get the Member CEO
     *
     * @return Member with the CEO
     */
    public Member getCEO() {

        Cursor cs = database.rawQuery(SELECT_CEO,null);

        if(cs.moveToFirst()) {

            int id = cs.getInt(0);
            String fname = cs.getString(1);
            String lname = cs.getString(2);
            String img = cs.getString(3);

            return  new Member(id,fname,lname,"CEO",false,img);

        }

        cs.close();

        return null;
    }

    /**
     * Create and Returns a LinkedList with the Teams in the DB
     *
     * @return
     */
    public LinkedList<Team> getTeams() {

        Cursor cs = database.rawQuery(SELECT_TEAMS,null);

        LinkedList<Team> teams = new LinkedList<>();

        while(cs.moveToNext()) {

            Team t = new Team(cs.getString(0));

            teams.add(t);

        }

        cs.close();

        return teams;

    }

    /**
     * Returns a LinkedList with the Members in a Team
     *
     * @param team String of the name of a team
     *
     * @return the LinkedList of Members
     */
    public LinkedList<Member> getMembers(String team) {

        Cursor cs = database.rawQuery(SELECT_MEMBERS+"\""+team+"\"",null);

        LinkedList<Member> members = new LinkedList<>();


        while(cs.moveToNext()) {

            int id = cs.getInt(0);
            String fname = cs.getString(1);
            String lname = cs.getString(2);
            String role = cs.getString(3);
            Boolean isleader = (cs.getInt(4)==1); //1=Leader
            String img = cs.getString(5);
            Member m = new Member(id,fname,lname,role,isleader,img);

            members.add(m);

        }

        cs.close();


        return members;
    }


}
