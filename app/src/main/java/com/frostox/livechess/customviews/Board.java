package com.frostox.livechess.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by roger on 10/22/2016.
 */
public class Board extends GridView {
    public Board(Context context) {
        super(context);
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        // Do not use the highest two bits of Integer.MAX_VALUE because they are
        // reserved for the MeasureSpec mode
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
        getLayoutParams().height = getMeasuredHeight();
    }


}
