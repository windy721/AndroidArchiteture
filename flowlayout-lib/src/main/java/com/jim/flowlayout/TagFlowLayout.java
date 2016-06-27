package com.jim.flowlayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jim.flowlayout.adapter.TagAdapter;
import com.jim.flowlayout.listener.OnDataChangedListener;

/**
 * Created by JimGong on 2016/6/27.
 */
public class TagFlowLayout extends FlowLayout implements OnDataChangedListener {

    TagAdapter mAdapter;

    public TagFlowLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        setClickable(true);
    }

    public TagFlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public void setAdapter(TagAdapter adapter)  {
        mAdapter = adapter;
        if (null != mAdapter) {
            mAdapter.setOnDataChangedListener(this);
            setupChildViews();
        }
    }

    private void setupChildViews() {
        removeAllViews();
        int childCount = mAdapter.getCount();

        TagView tagViewContainer = null;
        for (int i=0; i<childCount; i++) {
            View childView = mAdapter.getView(this, i, mAdapter.getItem(i));
            childView.setDuplicateParentStateEnabled(true);

            tagViewContainer = new TagView(getContext());
            LayoutParams layoutParams = childView.getLayoutParams();
            if (null != layoutParams) {
                tagViewContainer.setLayoutParams(layoutParams);
            }
            tagViewContainer.addView(childView);

            addView(tagViewContainer);
        }
    }

    @Override
    public void onChanged() {
        setupChildViews();
    }

    MotionEvent mMotionEvent;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (null == mMotionEvent)
            return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        TagView child = findChild(x, y);
        if (null != child) {
            int position = findPosByView(child);
            doSelect(child, position);
        }
        return true;
    }

    private void doSelect(TagView child, int position) {
        child.toggle();
    }

    private int findPosByView(View child)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private TagView findChild(int x, int y)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            TagView v = (TagView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y))
            {
                return v;
            }
        }
        return null;
    }
}

