package whizz.me.calculator;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import junit.framework.Assert;

import whizz.me.calculator.calculator.Evaluator;
import whizz.me.calculator.calculator.Parser;
import whizz.me.calculator.utils.Clipboard;
import whizz.me.calculator.utils.PasteUtils;
import whizz.me.calculator.views.AdvanceCalculator;
import whizz.me.calculator.views.CEditText;
import whizz.me.calculator.views.HistoryFragment;
import whizz.me.calculator.views.SimpleCalculator;


public class MainActivity extends FragmentActivity {
    private ViewPager viewpager;
    private CEditText result;
    private Button removeLeft;
    private Clipboard clipboard;
    private SimpleCalculator simpleCalculator;
    private AdvanceCalculator advanceCalculator;
    private HistoryFragment historyFragment;
    private MenuItem calculatorPageSwitch;
    private MenuItem historyPageSwitch;
    private boolean error;
    private boolean delete;
    private String ans;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ans = "0";
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Quivira.otf");
        clipboard = new Clipboard((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE));

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        result = (CEditText) findViewById(R.id.result);
        removeLeft = (Button) findViewById(R.id.remove_left);


        final SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager());

        simpleCalculator = swipeAdapter.getSimpleCalculator();
        advanceCalculator = swipeAdapter.getAdvanceCalculator();
        historyFragment = swipeAdapter.getHistoryFragment();

        viewpager.setAdapter(swipeAdapter);
        viewpager.addOnPageChangeListener(pageListener);


        historyFragment.onCreateView(getLayoutInflater(),null,null);
        historyFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = ((Button) view).getText();
                result.setText(text);

            }
        });


        result.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {// Removes the soft keyboard popup when the view is focused or touched

                view.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
        });

        removeLeft.setTypeface(custom_font);
        removeLeft.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                result.setText("");
                return true;
            }
        });

        registerForContextMenu(result);
    }

    private ViewPager.OnPageChangeListener pageListener = new ViewPager.SimpleOnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            if(calculatorPageSwitch == null || historyFragment == null) return;
                switch (position){
                    case 0:
                        calculatorPageSwitch.setTitle("Advanced Calculator");
                        historyPageSwitch.setTitle("History");
                        break;
                    case 1:
                        calculatorPageSwitch.setTitle("Simple Calculator");
                        historyPageSwitch.setTitle("History");
                        break;
                    case 2:
                        historyPageSwitch.setTitle("Clear History");
                        break;
                }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        calculatorPageSwitch = menu.findItem(R.id.switch_view);
        historyPageSwitch = menu.findItem(R.id.history_menu);
        pageListener.onPageSelected(viewpager.getCurrentItem());
        Log.d("onCreateOptionsMenu","Menu Inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.switch_view :
                if(viewpager.getCurrentItem() == 0)
                    viewpager.setCurrentItem(1, true);
                else
                    viewpager.setCurrentItem(0, true);
                return true;
            case R.id.history_menu :
                if(viewpager.getCurrentItem()==2)
                    historyFragment.clearHistory(true);
                else
                    viewpager.setCurrentItem(2,true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_custom_context_menu,menu);

        MenuItem paste = menu.findItem(R.id.paste);

        Assert.assertTrue(paste != null);

        paste.setEnabled(clipboard.canClip());//Set past enabled if there is something to paste and it contains only numbers

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String resultText = result.getText().toString();
        switch (item.getItemId()){
            case R.id.copy:
                Log.d("D","Copy");
                clipboard.copy(resultText);
                return true;
            case R.id.paste:
                Log.d("D","Paste");
                String paste = clipboard.paste(result.getSelectionStart(),resultText);
                if(paste != null)
                    result.setText(paste);
                return true;
            case R.id.cut:
                Log.d("D","Cut");
                clipboard.copy(resultText);
                result.setText("");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void flipDelete(){

        if(delete && !error){
            removeLeft.setText(R.string.erase);
            delete = false;
        }
        else{
            removeLeft.setText("C");
            delete = true;
        }
    }

    public void deleteLeft(View v){
        if(error || delete){
            result.setText("");
            result.setSelection(0);
            error = false;
            flipDelete();
        }
        else {
            int i = result.getSelectionStart();
            result.setText(PasteUtils.removeBefore(result.getText().toString(), i));
            if (i != 0)
                result.setSelection(i - 1);
        }
    }
    public void simple_press(View v){
        String c = simpleCalculator.onClick(v);
        insertToResult(c);
    }

    public void evaluate(View v){
        if(error) return;
        String s = result.getText().toString();
        if(s.isEmpty() || s.equals(ans)) return; //if empty or equals same result as previous
        try {
            String r = ""+Evaluator.evaluate(s);//TODO Convert scientific notation to number
            if(r.equals("NaN"))
                error = true;
            r = ans = Parser.toInt(r);
            result.setText(String.format("%s",r));
            result.setSelection(r.length());

            historyFragment.pushHistory(s,String.format("%s",r));

        } catch (Exception e) {
            if(e instanceof Evaluator.EvaluatorException)
                result.setText(e.getMessage());
            else {
                result.setText("Syntax Error");
                e.printStackTrace();
            }
            error = true;
        }finally {
            flipDelete();
        }
    }

    public void advanced_press(View v){
        String c = advanceCalculator.onClick(v);
        if(c.equals("inv")){
            advanceCalculator.setInv(!advanceCalculator.isInv());
        }
        else if(c.equals("deg")){
            advanceCalculator.setDeg(!advanceCalculator.isDeg());
            Evaluator.setDeg(advanceCalculator.isDeg());
        }
        else if(c.equals("ans")){
            insertToResult(ans);
        }
        else
            insertToResult(c);
    }
    public void showHistory(View v){

    }
    private void insertToResult(String c){
        if(error){
            result.setText("");
            result.setSelection(0);
            error = false;
        }
        if(delete)
            flipDelete();
        int i = result.getSelectionStart();
        result.setText(PasteUtils.pastInto(result.getText().toString(), c, i));
        if(i < result.getText().length())
            result.setSelection(i+c.length());
    }
}
