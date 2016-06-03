package com.gorkarevilla.mubalooteam;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

/**
 * The Activity.
 *
 * APP that shows a list with all the Members and Teams in the Company Mubaloo. It can be loaded from
 * a JSON or from a SQLite where is saved the information of the JSON.
 *
 * @Author Gorka Revilla
 */
public class MubalooTeam extends AppCompatActivity {

    //Tags
    private static final String TAG_ABOUTME = "AboutMe" ;
    private static final String TAG_MEMBER = "Member" ;


    //Model
    private Company theCompany;

    //SQLite DB
    private MubalooSQLite MubalooSQL;

    //ExpandableLV
    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        View parentLayout = findViewById(R.id.Coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Test the connection
        isConnected();

        FloatingActionButton isOnline = (FloatingActionButton) findViewById(R.id.isOnline);
        //Show the message of connection
        isOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    Snackbar.make(view, R.string.isOnline, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, R.string.isOffline, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                //Load from webservice or from SQLite)
                if(loadCompanyData()) {
                    populateUI();
                }else {
                    Snackbar.make(view, R.string.unableGetData, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        //Create SQLite for load or save data
        MubalooSQL = new MubalooSQLite(this);

        //Load from webservice or from SQLite
        if(loadCompanyData()) {
            populateUI();
        }else {
            Snackbar.make(parentLayout, R.string.unableGetData, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    /**
     * Procedure to load all the data in the UI from the Structure
     *
     */
    private void populateUI() {

        //Load CEO
        ImageView ceoimg = (ImageView) findViewById(R.id.ceoImg);
        TextView ceoname = (TextView) findViewById(R.id.ceoName);
        View ceoview = (View) findViewById(R.id.ceoView);
        ceoview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MemberDialog dialogMember = new MemberDialog();

                Member m = theCompany.get_ceo();

                // Supply Parameters as an argument.
                Bundle args = new Bundle();
                args.putString("name",m.toString());
                args.putString("role", m.get_role());
                args.putString("img", m.get_imageURL());
                args.putBoolean("isLeader", m.get_isLeader());
                dialogMember.setArguments(args);

                dialogMember.show(getFragmentManager(), TAG_MEMBER);
            }
        });

        Member ceo = theCompany.get_ceo();

        //Load img and Modify in the ImageView
        new DownloadImageTask(ceoimg, this).execute(ceo.get_imageURL());
        ceoname.setText("CEO: " + ceo.get_firstName() + " " + ceo.get_lastName());

        //Create the Expandable List View
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        listAdapter = new CompanyListAdapter(this,theCompany);

        expListView.setAdapter(listAdapter);
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MemberDialog dialogMember = new MemberDialog();

                Member m = theCompany.get_Teams().get(groupPosition).get_memberList().get(childPosition);

                // Supply Parameters as an argument.
                Bundle args = new Bundle();
                args.putString("name",m.toString());
                args.putString("role", m.get_role());
                args.putString("img", m.get_imageURL());
                args.putBoolean("isLeader", m.get_isLeader());
                dialogMember.setArguments(args);

                dialogMember.show(getFragmentManager(), TAG_MEMBER);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the team; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mubaloo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.aboutme) {

            //Show the AboutMe
            AboutMe dialogAboutMe = new AboutMe();
            dialogAboutMe.show(getFragmentManager(), TAG_ABOUTME);
            return true;
        }
        else if (id == R.id.action_exit) {

            //Close the APP
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * Checks Network Connection
     *
     * @return Boolean true if the Network Connection is active
     */
    private boolean isConnected() {

        // Added permisions in the Manifest.xml
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Change the Button img
            FloatingActionButton isOnlineButton = (FloatingActionButton) findViewById(R.id.isOnline);
            isOnlineButton.setImageResource(android.R.drawable.presence_online);
           // isOnlineButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{R.color.colorOnline}));
            return true;
        }

        else{
            //Change the Button img
            FloatingActionButton isOnlineButton = (FloatingActionButton) findViewById(R.id.isOnline);
            isOnlineButton.setImageResource(android.R.drawable.presence_offline);
           // isOnlineButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{R.color.colorOffline}));
            return false;
        }

    }

    /**
     * Load the Data of the Company from the JSON if it is connected to the Network or from the SQLite
     *
     * @return Boolean true if goes right the data loading
     */
    private boolean loadCompanyData() {

        final String urlJSON = "http://developers.mub.lu/resources/team.json";

        theCompany = new Company("Mubaloo");

        //Data in String
        String data = null;

        if(isConnected()) {

            //Load data from the webserver
            RestService service = new RestService();
            service.execute(urlJSON);

            try {
                data = service.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //Create Objects from data.
            return loadJSON(data);

        }else {
            //Load data from the SQLite
            return loadSQLite();
        }

    }

    /**
     * Load the String data and create the Company Structure
     *
     * @param data String with the URL of the JSON
     */
    private boolean loadJSON(String data) {

        try {
            if(data != null) {

                //Delete data from SQLite to Update with the last information
                MubalooSQL.removeAll();

                JSONArray jTeamArray = new JSONArray(data);

                //Load the CEO, position 0 of array
                JSONObject jCEO = jTeamArray.getJSONObject(0);

                //the CEO
                int ceoid = jCEO.getInt("id");
                String ceofname = jCEO.getString("firstName");
                String ceolname = jCEO.getString("lastName");
                String ceorole = jCEO.getString("role");
                String ceopimage = jCEO.getString("profileImageURL");

                Member ceo = new Member(ceoid,ceofname,ceolname,ceorole,false,ceopimage);

                theCompany.set_ceo(ceo);

                //Add CEO to the SQLite
                MubalooSQL.addMember(ceoid,ceofname,ceolname,ceorole,true,ceopimage,"CEO");

                //Teams start in position 1
                for (int i=1;i<jTeamArray.length();++i) {

                    JSONObject jTeam = jTeamArray.getJSONObject(i);

                    String tname = jTeam.getString("teamName");
                    Team aTeam = new Team(tname);

                    JSONArray jMemberArray = jTeam.getJSONArray("members");

                    //Read all the Members in the Team
                    for (int j=0;j<jMemberArray.length();++j) {

                        JSONObject jMember = jMemberArray.getJSONObject(j);

                        int mid = jMember.getInt("id");
                        String mfname = jMember.getString("firstName");
                        String mlname = jMember.getString("lastName");
                        String mrole = jMember.getString("role");
                        String mpimage = jMember.getString("profileImageURL");
                        Boolean teamlead = false;
                        if (jMember.has("teamLead")) teamlead = jMember.getBoolean("teamLead");

                        //Insert in the SQLite
                        MubalooSQL.addMember(mid, mfname, mlname, mrole, teamlead, mpimage, tname);

                        //Insert in the Structure
                        Member aMember = new Member(mid,mfname,mlname,mrole,teamlead,mpimage);
                        aTeam.addMember(aMember);

                    }

                    //Insert in the Structure
                    theCompany.addTeam(aTeam);

                }

             return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Read the data in the SQlite
     *
     * @return Boolean true if loads from SQLite correctly
     */
    private boolean loadSQLite() {

        //Check if is data inside the table
        if(MubalooSQL.hasData()) {

            //GET CEO
            theCompany.set_ceo(MubalooSQL.getCEO());

            //Create Teams
            theCompany.set_Teams(MubalooSQL.getTeams());

            //Insert Members in each team
            ListIterator<Team> iterator = theCompany.get_Teams().listIterator();
            while(iterator.hasNext()) {
                Team t = iterator.next();
                t.set_memberList(MubalooSQL.getMembers(t.get_name()));
                iterator.set(t);
            }

            return true;
        }

        return false;

    }



}
