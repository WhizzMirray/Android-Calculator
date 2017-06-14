package whizz.me.calculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import whizz.me.calculator.views.AdvanceCalculator;
import whizz.me.calculator.views.HistoryFragment;
import whizz.me.calculator.views.SimpleCalculator;


class SwipeAdapter extends FragmentPagerAdapter {
    private SimpleCalculator simpleCalculator;
    private AdvanceCalculator advanceCalculator;
    private HistoryFragment historyFragment;
    SwipeAdapter(FragmentManager fm) {
        super(fm);
        simpleCalculator = SimpleCalculator.newInstance();
        advanceCalculator = AdvanceCalculator.newInstance();
        historyFragment = HistoryFragment.newInstance();

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return simpleCalculator;
            case 1:
                return advanceCalculator;
            case 2:
                return historyFragment;
        }
        return simpleCalculator;
    }

    @Override
    public int getCount() {
        return 3;
    }

    AdvanceCalculator getAdvanceCalculator() {
        return advanceCalculator;
    }

    SimpleCalculator getSimpleCalculator() {
        return simpleCalculator;
    }

    HistoryFragment getHistoryFragment() {
        return historyFragment;
    }

}
