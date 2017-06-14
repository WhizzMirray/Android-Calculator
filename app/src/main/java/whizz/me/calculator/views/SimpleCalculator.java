package whizz.me.calculator.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import whizz.me.calculator.R;
import whizz.me.calculator.utils.FragmentXML;

public class SimpleCalculator extends Fragment implements FragmentXML{

    public SimpleCalculator(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_simple_calculator, container, false);

    }

    public static SimpleCalculator newInstance(){
        return new SimpleCalculator();
    }

    @Override
    public String onClick(View v) {
        switch (v.getId()){
            case R.id.num_0 :
                return "0";

            case R.id.num_1 :
                return "1";

            case R.id.num_2 :
                return "2";

            case R.id.num_3 :
                return "3";

            case R.id.num_4 :
                return "4";

            case R.id.num_5 :
                return "5";

            case R.id.num_6 :
                return "6";

            case R.id.num_7 :
                return "7";

            case R.id.num_8 :
                return "8";

            case R.id.num_9 :
                return "9";

            case R.id.op_div :
                return "/";

            case R.id.op_plus :
                return "+";

            case R.id.op_mul :
                return "*";

            case R.id.op_minus :
                return "-";

            case R.id.op_float :
                return ".";

            case R.id.op_percent:
                return "%";
            default:
                return "";
        }
    }
}
