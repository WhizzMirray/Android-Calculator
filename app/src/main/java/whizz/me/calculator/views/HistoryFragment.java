package whizz.me.calculator.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import whizz.me.calculator.R;
import whizz.me.calculator.utils.FragmentXML;


public class HistoryFragment extends Fragment{

    private HashMap<String,String> viewHistory;

    private View.OnClickListener listener;
    public HistoryFragment() {
        viewHistory = new HashMap<String,String>();
    }

    private LayoutInflater inflater;
    private ViewGroup container;
    Bundle bundle;

    private LinearLayout buttonsContainer;
    public static HistoryFragment newInstance() {
        return new HistoryFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        this.bundle = savedInstanceState;
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        buttonsContainer = (LinearLayout) view.findViewById(R.id.history_container);
        Log.d("HistoryFragment","View Inflated");
        buttonsContainer.post(new Runnable() {
            @Override
            public void run() {
                for(String r: viewHistory.keySet()){
                    buttonsContainer.addView(createListing(r,viewHistory.get(r)));
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearHistory(false);
    }

    public void pushHistory(final String expression, final String result){
        if(viewHistory.get(expression) != null) return;
        viewHistory.put(expression,result);
        buttonsContainer.post(new Runnable() {//Adding the view on the UI thread
            @Override
            public void run() {
                buttonsContainer.addView(createListing(expression,result));
                Log.d("HistoryFragment","Pushed to History");
            }
        });

        //getActivity().getSupportFragmentManager().beginTransaction().replace(this.getView().getId(),newInstance()).commit();
    }

    private LinearLayout createListing(String expression,String result){
        final LinearLayout historyButtons = (LinearLayout) inflater.inflate(R.layout.history_buttons,container,false);
        historyButtons.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        Button exp = (Button) historyButtons.findViewById(R.id.history_exp);
        Button res = (Button) historyButtons.findViewById(R.id.history_res);

        exp.setText(expression);
        exp.setOnClickListener(listener);

        res.setText(result);
        res.setOnClickListener(listener);

        return historyButtons;
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void clearHistory(final boolean clear){
        buttonsContainer.post(new Runnable() {
            @Override
            public void run() {
                if(clear)
                    viewHistory.clear();
                if(buttonsContainer != null){
                    buttonsContainer.removeAllViews();
                    //buttonsContainer.removeAllViewsInLayout();
                }
            }
        });
    }
}
