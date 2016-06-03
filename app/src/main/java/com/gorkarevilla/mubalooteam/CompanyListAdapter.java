package com.gorkarevilla.mubalooteam;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * ListAdapter for the Expandable List View that shows all the Teams and Members in the Company
 *
 * @Author Gorka Revilla
 */
public class CompanyListAdapter extends BaseExpandableListAdapter {

    private Company theCompany;
    private Context theContext;

    /**
     * Constructor of the ListAdapter
     * @param context the Context of the view
     * @param company Company with all the info about the Company
     */
    public CompanyListAdapter(Context context, Company company) {

        theContext=context;
        theCompany=company;

    }


    @Override
    public int getGroupCount() {
        return theCompany.get_Teams().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return theCompany.get_Teams().get(groupPosition).get_memberList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return theCompany.get_Teams().get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return theCompany.get_Teams().get(groupPosition).get_memberList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = ((Team)getGroup(groupPosition)).get_name();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.theContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_team, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListTeam);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Member member = (Member) getChild(groupPosition, childPosition);
        String childText = member.toString();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.theContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_member, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListMember);
        txtListChild.setText(childText);

        if(member.get_isLeader()) {
            //Change the color of the view of the leader
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                convertView.setBackgroundColor(theContext.getColor(R.color.colorExpLeadBack));
                txtListChild.setTextColor(theContext.getColor(R.color.colorExpLeadText));
            }else {
                convertView.setBackgroundColor(theContext.getResources().getColor(R.color.colorExpLeadBack));
                txtListChild.setTextColor(theContext.getResources().getColor(R.color.colorExpLeadText));
            }
        }else {

            //Change the color of the view of the leader
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                convertView.setBackgroundColor(theContext.getColor(R.color.colorExpMemberBack));
                txtListChild.setTextColor(theContext.getColor(R.color.colorExpMemberText));
            }else {
                convertView.setBackgroundColor(theContext.getResources().getColor(R.color.colorExpMemberBack));
                txtListChild.setTextColor(theContext.getResources().getColor(R.color.colorExpMemberText));
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
