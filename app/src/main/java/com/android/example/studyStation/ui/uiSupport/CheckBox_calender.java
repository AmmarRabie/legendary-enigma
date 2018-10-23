package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.android.example.studyStation.R;


/**
 * Created by AmmarRabie on 23/08/2017.
 */

public class CheckBox_calender extends AppCompatCheckBox {
    CalenderChBoxType mType = CalenderChBoxType.FREE;

    public static final int STATE_DISABLED = 0;
    public static final int STATE_FREE = 1;
    public static final int STATE_BOOKED = 2;

    public CheckBox_calender(Context context) {
        super(context);

//        buttonDrawable = getResources().getDrawable(R.drawable.caleder_check_box);
        /*try {
            setButtonDrawable(android.R.color.transparent);
        } catch (Exception e) {
            // DO NOTHING
        }*/

        updateType();
    }

    public CheckBox_calender(Context context, AttributeSet attrs) {
        super(context, attrs);

//        buttonDrawable = getResources().getDrawable(R.drawable.caleder_check_box);
        /*try {
            setButtonDrawable(android.R.color.transparent);
        } catch (Exception e) {
            // DO NOTHING
        }*/

        // retrieved values correspond to the positions of the attributes
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CheckBox_calender);
        int count = typedArray.getIndexCount();
        try {
            for (int i = 0; i < count; ++i) {
                int attr = typedArray.getIndex(i);
                // the attr corresponds to the title attribute
                if (attr == R.styleable.CheckBox_calender_type) {
                    // set the type from the layout
                    String type = typedArray.getString(attr);
                    if (type.equals("free"))
                    {
                        mType = CalenderChBoxType.FREE;
                    }
                    else if (type.equals("booked"))
                    {
                        mType = CalenderChBoxType.BOOKED;
                    }
                    /*else if (type.equals("waiting"))
                    {
                        mType = CalenderChBoxType.WAITING;

                    }*/
                    else if (type.equals("disabled"))
                    {
                        mType = CalenderChBoxType.DISABLED;

                    }
                    updateType();
                }
            }
        }

        // the recycle() will be executed obligatorily
        finally {
            // for reuse
            typedArray.recycle();
        }

    }

    public CheckBox_calender(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        buttonDrawable = getResources().getDrawable(R.drawable.caleder_check_box);
        /*try {
            setButtonDrawable(android.R.color.transparent);
        } catch (Exception e) {
            // DO NOTHING
        }*/

        updateType();
    }

    public void setType(CalenderChBoxType type) {
        mType = type;
        updateType();
    }

    public void setType(int type) {
        switch (type)
        {
            case 0:
                mType = CalenderChBoxType.DISABLED;
                break;
            case 1:
                mType = CalenderChBoxType.FREE;
                break;
            case 2:
                mType = CalenderChBoxType.BOOKED;
        }
        updateType();
    }


    private void updateType() {
        if (mType == CalenderChBoxType.FREE) {
            setChecked(false);
            setEnabled(true);
        } /*else if (mType == CalenderChBoxType.WAITING) {
            setChecked(true);
            setEnabled(true);
        }*/ else if (mType == CalenderChBoxType.DISABLED) {
            setEnabled(false);
            setChecked(false);
        }
        else if (mType == CalenderChBoxType.BOOKED)
        {
//            buttonDrawable = getResources().getDrawable(android.R.drawable.ic_menu_edit);
            setChecked(true);
            setEnabled(false);
        }
    }



    public CalenderChBoxType getmType() {
        return mType;
    }

    public enum CalenderChBoxType {
        FREE,
//        WAITING,
        DISABLED,
        BOOKED
    }
}
