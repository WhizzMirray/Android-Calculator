package whizz.me.calculator.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import whizz.me.calculator.R;

public class CEditText extends AppCompatEditText {
    Context context;
    GestureDetector gestureDetector;

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }
    public CEditText(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public CEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init()
    {
        this.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        this.setLongClickable(false);
        gestureDetector = new GestureDetector(context, new GestureListener());

    }

    boolean canPaste()
    {
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Prevents the action bar (top horizontal bar with cut, copy, paste, etc.) from appearing
     * by intercepting the callback that would cause it to be created, and returning false.
     */
    private class ActionModeCallbackInterceptor implements ActionMode.Callback
    {
        private final String TAG = CEditText.class.getSimpleName();

        public boolean onCreateActionMode(ActionMode mode, Menu menu) { return false; }
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) { return false; }
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) { return false; }
        public void onDestroyActionMode(ActionMode mode) {}
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            Log.w("Double Tap", "Tapped at: (" + x + "," + y + ")");

            return true;
        }
    }


}
