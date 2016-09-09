package com.example.irakli.soplidange.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.irakli.soplidange.R;

public class CTextView extends TextView {

    private int fontTypeNumber = 0;
    private boolean isSelected;

    private Context context;
    public CTextView(Context context) {
        super(context);
        setMyFont();
        this.context = context;
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CtextView, 0, 0);
            try {
                fontTypeNumber = a.getInt(R.styleable.CtextView_font_name, 0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                a.recycle();
            }
            setMyFont();
            this.context = context;
        }
    }

    public void setText(@StringRes Integer textResId) {
        setText(context.getString(textResId));
    }


    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private void setMyFont(){
        if (getFont(fontTypeNumber) != null)
            setTypeface(getFont(fontTypeNumber));
    }

    private Typeface BPG_EXCELSIOR_CAPS, BPG_EXCELSIOR;


    private Typeface getFont(int fontTypeNumber){
        try {
            switch (fontTypeNumber) {
                case 1:
                    BPG_EXCELSIOR_CAPS = Typeface.createFromAsset(getContext().getAssets(), "BPG_GEL_Excelsior_Caps.ttf");
                    return BPG_EXCELSIOR_CAPS;
                case 2:
                    BPG_EXCELSIOR = Typeface.createFromAsset(getContext().getAssets(), "BPG_GEL_Excelsior.ttf");
                    return BPG_EXCELSIOR;
                default:
                    return Typeface.createFromAsset(getContext().getAssets(), "BPG_GEL_Excelsior.ttf");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}