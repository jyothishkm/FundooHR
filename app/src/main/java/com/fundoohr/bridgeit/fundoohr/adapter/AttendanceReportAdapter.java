package com.fundoohr.bridgeit.fundoohr.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.model.ExpListChildItemsModel;
import com.fundoohr.bridgeit.fundoohr.model.ExpListGroupItemsModel;
import com.fundoohr.bridgeit.fundoohr.view.fragment.AttendanceRepGenFrag;

import java.util.ArrayList;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The Adapter Of MVVM Design Pattern.
 * 2.To Retrieve The Necessary Model data from view .
 * 3.Here We Can Take The Necessary Data  And set to the  View.
 */

public class AttendanceReportAdapter extends BaseExpandableListAdapter {

    private static final String TAG ="ExpList" ;
    boolean mIsSelectAll;
    boolean mGroupIsselect;
    ArrayList<Boolean> mSelectedParentCheckBoxesState = new ArrayList<>();
    ArrayList<ArrayList<Boolean>> mSelectedChildCheckBoxStates = new ArrayList<>();
    int mCount;
    ArrayList<ArrayList<ExpListChildItemsModel>> mModelArrayList;
    private Context mContext;
    private int mGroupCheckcount;
    private ArrayList<ExpListGroupItemsModel> mListDataGroup;
    private ChildViewHolder mChildViewHolder;
    private GroupViewHolder mGroupViewHolder;
    private String mGroupcompanyName;
    private String mChildEmpName;
    private String mChildLeave;
    private Button mGenarate;

    public AttendanceReportAdapter(Context context,
                                   ArrayList<ExpListGroupItemsModel> listDataGroup,
                                   ArrayList<ArrayList<ExpListChildItemsModel>> modelArrayList,
                                   boolean isSelectAll, Button mGenarate) {

        mContext = context;
        mListDataGroup = listDataGroup;
        this.mIsSelectAll = isSelectAll;
        this.mModelArrayList = modelArrayList;
        this.mGenarate = mGenarate;
        initCheckStates(false);
    }

    //initially all checkbox state false
    private void initCheckStates(boolean defaultState) {
        //group checkbox
        for(int i = 0 ; i < mListDataGroup.size(); i++) {
            mSelectedParentCheckBoxesState.add(i, defaultState);

            //child checkbox
            ArrayList<Boolean> childStates = new ArrayList<>();
            ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(i);
            for (int j = 0; j <modelArrayList1.size() ; j++) {
                childStates.add(defaultState);
                mCount++;
            }
            mSelectedChildCheckBoxStates.add(i, childStates);
            Log.i(TAG, "initCheckStates:ddd "+childStates.size()+"......."+i);
        }
    }

    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }


    @Override
    public ExpListGroupItemsModel getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        mGroupcompanyName = getGroup(groupPosition).getCompanyName();

        if (convertView == null) {
            //infalte the group layout
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);

            // Initialize the GroupViewHolder
            mGroupViewHolder = new AttendanceReportAdapter.GroupViewHolder();

            mGroupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.groupTextView);
            mGroupViewHolder.mGroupCb = (CheckBox) convertView.findViewById(R.id.groupCb) ;



            convertView.setTag(mGroupViewHolder);
        } else {

            mGroupViewHolder = (AttendanceReportAdapter.GroupViewHolder) convertView.getTag();
        }

        mGroupViewHolder.mGroupText.setText(mGroupcompanyName);

        if(mSelectedParentCheckBoxesState.size() <= groupPosition){
            mSelectedParentCheckBoxesState.add(groupPosition, false);
        }else {
            mGroupViewHolder.mGroupCb.setChecked(mSelectedParentCheckBoxesState.get(groupPosition));
        }
        //group checkbox click function
        mGroupViewHolder.mGroupCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = mSelectedParentCheckBoxesState.get(groupPosition);
                if (state){
                    mGroupCheckcount++;
                    mGroupIsselect = false;
                }else {
                    mGroupIsselect = true;
                    mGroupCheckcount--;
                }
                Log.d("TAG", "STATEEEE = " + state);
                //remove the previous state
                mSelectedParentCheckBoxesState.remove(groupPosition);
                //add the current state
                mSelectedParentCheckBoxesState.add(groupPosition, state ? false : true);
                //correspond child checkbox state
                ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(groupPosition);
                //iterate all child checkbox
                for (int i = 0; i < modelArrayList1.size(); i++) {
                    //remove the previous state
                    mSelectedChildCheckBoxStates.get(groupPosition).remove(i);
                    //add the current state
                    mSelectedChildCheckBoxStates.get(groupPosition).add(i, state ? false : true);
                }
                notifyDataSetChanged();

            }
        });

//select all group and child checkbox
        int selectAllCount = 1;
        if (mIsSelectAll && mGroupCheckcount == 0) {
            selectAllCount = 0;
            //iterate all group checkbox and set checked checkbox
            for(int i = 0 ; i < mListDataGroup.size(); i++) {
                mSelectedParentCheckBoxesState.add(i, true);
                ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(i);
                //iterate all child checkbox and set checked checkbox
                for (int j = 0; j <modelArrayList1.size(); j++) {
                    mSelectedChildCheckBoxStates.get(i).remove(j);
                    mSelectedChildCheckBoxStates.get(i).add(j,mIsSelectAll);

                }
            }
        } else if (selectAllCount == 0) {
            //iterate all group checkbox and set unchecked checkbox
            for(int i = 0 ; i < mListDataGroup.size(); i++) {
                mSelectedParentCheckBoxesState.add(i, false);

                ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(i);
                //iterate all group checkbox and set unchecked checkbox
                for (int j = 0; j <modelArrayList1.size(); j++) {
                    mSelectedChildCheckBoxStates.get(i).remove(j);
                    mSelectedChildCheckBoxStates.get(i).add(j, mIsSelectAll);
                }
            }
        }



        //expend and collepse the list
        if (isExpanded) {
            mGroupViewHolder.mGroupText.setTypeface(null, Typeface.BOLD);
            mGroupViewHolder.mGroupText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_black, 0);
        } else {
            mGroupViewHolder.mGroupText.setTypeface(null, Typeface.NORMAL);
            mGroupViewHolder.mGroupText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_black, 0);
        }

        if (mGroupCheckcount < 0){
            mGenarate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callGenReport();
                }
            });
        }
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(groupPosition);
        Log.i("size child", ""+modelArrayList1.size()+"........"+groupPosition);
        return modelArrayList1.size();
    }


    @Override
    public ExpListChildItemsModel getChild(int groupPosition, int childPosition) {
        ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(groupPosition);
        return modelArrayList1.get(childPosition);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;
        //  I passed a text string into an activity holding a getter/setter
        //  which I passed in through "ExpListChildItemsModel".
        //  Here is where I call the getter to get that text
        mChildEmpName = getChild(mGroupPosition, mChildPosition).getEmpName();
        mChildLeave = String.valueOf(getChild(mGroupPosition, mChildPosition).getLeave());
        if (convertView == null) {

            //infalte the child layout
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);

            mChildViewHolder = new AttendanceReportAdapter.ChildViewHolder();

            mChildViewHolder.mChildEmpName = (TextView) convertView
                    .findViewById(R.id.childEmpName);
            mChildViewHolder.mChildLeave = (TextView) convertView
                    .findViewById(R.id.childLeave);

            mChildViewHolder.mChildCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox);

            convertView.setTag(R.layout.child_item, mChildViewHolder);

        } else {

            mChildViewHolder = (AttendanceReportAdapter.ChildViewHolder) convertView
                    .getTag(R.layout.child_item);
        }
        //set the text leave color
        if (mChildLeave.equalsIgnoreCase("0") || mChildLeave.equalsIgnoreCase("1"))
        {
            mChildViewHolder.mChildLeave.setTextColor(Color.GREEN);
        } else {
            mChildViewHolder.mChildLeave.setTextColor(Color.RED);
        }

        mChildViewHolder.mChildEmpName.setText(mChildEmpName);
        mChildViewHolder.mChildLeave.setText(mChildLeave +""+"L");

        if(mSelectedChildCheckBoxStates.size() <= mGroupPosition) {
            ArrayList<Boolean> childState = new ArrayList<>();
            ArrayList<ExpListChildItemsModel> modelArrayList1 = mModelArrayList.get(mGroupPosition);

            for(int i = 0; i < modelArrayList1.size(); i++){
                if(childState.size() > mChildPosition)
                    childState.add(mChildPosition, false);
                else
                    childState.add(false);
            }
            if(mSelectedChildCheckBoxStates.size() > mGroupPosition) {
                mSelectedChildCheckBoxStates.add(mGroupPosition, childState);
            }else
                mSelectedChildCheckBoxStates.add(childState);
        }else{
            mChildViewHolder.mChildCheckBox.setChecked(mSelectedChildCheckBoxStates.get(mGroupPosition).get(mChildPosition));
        }

        //child click  checkbox function here
        mChildViewHolder.mChildCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean state = mSelectedChildCheckBoxStates.get(mGroupPosition).get(mChildPosition);

                //remove the previous state
                mSelectedChildCheckBoxStates.get(mGroupPosition).remove(mChildPosition);
                //add the current state in the list
                mSelectedChildCheckBoxStates.get(mGroupPosition).add(mChildPosition, state ? false : true);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void callGenReport() {
        ((Activity) mContext).getFragmentManager().beginTransaction()
                .replace(R.id.frame_dashboard, new AttendanceRepGenFrag()).commit();
    }

    //group viewholder class
    public final class GroupViewHolder {

        TextView mGroupText;
        CheckBox mGroupCb;
    }

    //child viewholder class
    public final class ChildViewHolder {

        TextView mChildEmpName;
        CheckBox mChildCheckBox;
        TextView mChildLeave;
    }


}
