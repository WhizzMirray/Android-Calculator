package whizz.me.calculator.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import whizz.me.calculator.R;
import whizz.me.calculator.utils.FragmentXML;

public class AdvanceCalculator extends Fragment implements FragmentXML{

    private boolean inv;
    private boolean deg;
    private Button sin,cos,tan;
    private Button binv,bdeg;
    private AdvanceCalculator instance;
    public AdvanceCalculator() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advance_calculator, container, false);
        sin = (Button) view.findViewById(R.id.op_sin);
        cos = (Button) view.findViewById(R.id.op_cos);
        tan = (Button) view.findViewById(R.id.op_tan);
        binv = (Button) view.findViewById(R.id.op_inv);
        bdeg = (Button) view.findViewById(R.id.op_deg);
        return view;
    }

    public static AdvanceCalculator newInstance(){
        return new AdvanceCalculator().initInstance();
    }

    private AdvanceCalculator initInstance(){
        return (instance = this);
    }
    private void refreshView(){
        Log.d("Advanced Calculator","Refreshed the view !");
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(instance)
                .attach(instance)
                .commit();
    }
    @Override
    public String onClick(View v) {
        switch (v.getId()) {
            case R.id.op_sin:
                return inv ? "arcsin(" : "sin(";
            case R.id.op_cos:
                return inv ? "arccos(" : "cos(";
                
            case R.id.op_tan:
                return inv ? "arctan(" : "tan(";
                
            case R.id.op_ln:
                return "ln(";
                
            case R.id.op_log:
                return "log(";
                
            case R.id.op_fact:
                return "!";
                
            case R.id.op_sroot:
                return "√";
                
            case R.id.op_power:
                return "^";
                
            case R.id.num_pi:
                return "π";
                
            case R.id.op_lpar:
                return "(";
                
            case R.id.op_rpar:
                return ")";
                
            case R.id.num_e:
                return "e";
                
            case R.id.op_inv:
                return "inv";
                
            case R.id.op_exp:
                return "E";
                
            case R.id.op_deg:
                return "deg";

            case R.id.num_ans:
                return "ans";
            default:
                return "";
        }
    }

    public boolean isInv() {
        return inv;
    }

    public void setInv(boolean inv) {
        if(cos == null || sin == null || tan == null || binv == null) refreshView();

        if(inv){
            cos.setText(R.string.arccos);
            sin.setText(R.string.arcsin);
            tan.setText(R.string.arctan);
            binv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.invPressed));
        }
        else{
            cos.setText(R.string.cos);
            sin.setText(R.string.sin);
            tan.setText(R.string.tan);
            binv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorOp));
        }
        this.inv = inv;
    }


    public boolean isDeg() {
        return deg;
    }

    public void setDeg(boolean deg) {
        if(bdeg == null) refreshView();
        if(deg){
            bdeg.setText(R.string.rad);
            bdeg.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.invPressed));
        }
        else{
            bdeg.setText(R.string.deg);
            bdeg.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorOp));
        }
        this.deg = deg;
    }
}
