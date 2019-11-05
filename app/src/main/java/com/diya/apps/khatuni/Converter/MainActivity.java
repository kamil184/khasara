package com.diya.apps.khatuni.Converter;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diya.apps.khatuni.InActivity;
import com.diya.apps.khatuni.Main_Menu;
import com.diya.apps.khatuni.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    private Button btnConvert;
    private EditText editInput;
    private ArrayList<HashMap<String, String>> mylist;
    private Spinner spinInput;
    private Spinner spinOutput;
    private InterstitialAd interstitial;
    private LinearLayout converted_linear, other_units;
//    String[] arrayName;
    private  double[] dimension_value=new double[24];
    TextView convertedValue,txtunit,otherunit;
    LinearLayout linother;
    ImageButton clear;
    String from,to, number;
    int flag=0;
    
    


    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.con_activity_main);
        addListenerOnButton();
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        number = getIntent().getStringExtra("number");

        spinInput.setSelection(GetIndex(spinInput,from));
        spinOutput.setSelection(GetIndex(spinOutput,to));
        editInput.setText(number);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_sp_yes));

        // arrayName.add(13,"Square Feet");
        dimension_value[0]=1.0d;

        // arrayName[9]="Hectares";
        dimension_value[1]=0.0000092d;

        // arrayName[2]="Acres";
        dimension_value[2]=0.00002295d;

        // arrayName.add(14,"Square Meter");
        dimension_value[3]=0.093d;

        // arrayName.add(15,"Square Yards");
        dimension_value[4]=0.1111111112d;

        // arrayName[3]="Bhighas UP";
        dimension_value[5]=0.000037037d;

        // arrayName[3]="Bhighas Western UP";
        dimension_value[6]=0.0001481d;

        // arrayName[3]="Bhighas MP";
        dimension_value[7]=0.00003673d;

        // arrayName[3]="Rajsthan";
        dimension_value[8]=0.0000574d;

        // arrayName[3]="Bhighas West Bengal";
        dimension_value[9]=0.000069444d;

        // arrayName[3]="Bhighas Gujarat";
        dimension_value[10]=0.0000390625d;

        // arrayName[3]="Bhighas Punjab";
        dimension_value[11]=0.00009180d;

        // arrayName[3]="Bhighas Uttarakhand";
        dimension_value[12]=0.00011625d;

        // arrayName[3]="Bhighas Himachal";
        dimension_value[13]=0.0001147500d;

       // arrayName[4]="Biswa";
        dimension_value[14]=0.0000028d;

        // arrayName[5]="Guntha";
        dimension_value[15]=0.0009182d;

        // arrayName.add(10,"Kanal");
        dimension_value[16]=0.0001852d;

        // arrayName[6]="Chataks";
        dimension_value[17]=0.0022222d;

        // arrayName.add(11,"Kottha");
        dimension_value[18]=0.0013888d;

        // arrayName.add(12,"Rood");
        dimension_value[19]=0.0000918d;

        // arrayName[0] ="Aankadam";
        dimension_value[20]=0.0138908d;

        // arrayName[7]="Lagga";
        dimension_value[21]=1.45454545454545d;

        // arrayName[8]="Cents";
        dimension_value[22]=0.002296d;

        // arrayName[9]="Ares";
        dimension_value[23]=0.0009293d;

        clickCalculate(btnConvert);
    }

    public void onDestroy() {
   
        super.onDestroy();
    }

    public void addListenerOnButton() {
        this.editInput = (EditText) findViewById(R.id.edittext);
        this.spinInput = (Spinner) findViewById(R.id.spinner);
        this.spinOutput = (Spinner) findViewById(R.id.tospinner);
        this.btnConvert = (Button) findViewById(R.id.button);
        this.clear = (ImageButton) findViewById(R.id.clear);

//        LinearLayout edit_lin = (LinearLayout)findViewById(R.id.edit_linear);
        converted_linear = (LinearLayout)findViewById(R.id.converted_linear);
        other_units = (LinearLayout)findViewById(R.id.other_units);

//        edit_lin.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha));
        this.convertedValue=(TextView)findViewById(R.id.ConvertedValue);
        this.txtunit=(TextView)findViewById(R.id.unit);
        this.otherunit=(TextView)findViewById(R.id.otherunits);
        this.linother=(LinearLayout)findViewById(R.id.linother);
        this.btnConvert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {

               /* AdRequest adRequest = new AdRequest.Builder().build();
                interstitial.loadAd(adRequest);
                interstitial.setAdListener(new com.google.android.gms.ads.AdListener() {
                    @Override
                    public void onAdClosed() {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        MainActivity.this.mylist = new ArrayList();
                        String strX = MainActivity.this.editInput.getText().toString();
                        if (strX.trim().equals("")) {
                            Toast.makeText(MainActivity.this, "Please input the value", Toast.LENGTH_LONG).show();
                        } else {
                            Double dblInput = Double.valueOf(Double.valueOf(strX).doubleValue());

                            MainActivity.this.mylist = MainActivity.this.doSelection(MainActivity.this.spinInput.getSelectedItemPosition(),MainActivity.this.spinOutput.getSelectedItemPosition(), dblInput);

//                    LoanMainActivity.this.toSelection(String.valueOf(LoanMainActivity.this.spinOutput.getSelectedItem()));


                        }
                        MainActivity.this.putInList(MainActivity.this.mylist);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        MainActivity.this.mylist = new ArrayList();
                        String strX = MainActivity.this.editInput.getText().toString();
                        if (strX.trim().equals("")) {
                            Toast.makeText(MainActivity.this, "Please input the value", Toast.LENGTH_LONG).show();
                        } else {
                            Double dblInput = Double.valueOf(Double.valueOf(strX).doubleValue());

                            MainActivity.this.mylist = MainActivity.this.doSelection(MainActivity.this.spinInput.getSelectedItemPosition(),MainActivity.this.spinOutput.getSelectedItemPosition(), dblInput);

//                    LoanMainActivity.this.toSelection(String.valueOf(LoanMainActivity.this.spinOutput.getSelectedItem()));


                        }
                        MainActivity.this.putInList(MainActivity.this.mylist);
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                    }
                });*/
               clickCalculate(view);
            }
        });

        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editInput.setText("");
            }
        });

        otherunit.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View view) {
               inputMethodManagerHide(view);
                if (flag % 2 == 0){
                    linother.setVisibility(View.VISIBLE);
                    otherunit.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_remove_black_24dp, 0, 0, 0);
                    flag++;
                }

                else if (flag % 2 != 0){
                    linother.setVisibility(View.GONE);
                    otherunit.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_add_black_24dp, 0, 0, 0);
                    flag++;
                }

           }
       });
    }


    private int GetIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equals(myString)){
                index = i;
                i=spinner.getCount();//will stop the loop, kind of break, by making condition false
            }
        }
        return index;
    }

    public ArrayList<HashMap<String, String>> doSelection(int strInput,int strOutput, Double dblInput) {

        NumberFormat format4 = new DecimalFormat("0.0000");
        double dblResult = dimension_value[strOutput]*dblInput/dimension_value[strInput];
        String formatResult = format4.format(dblResult);
        //convertedValue.setText(String.valueOf (dimension_value[strOutput]*dblInput/dimension_value[strInput]));

        convertedValue.setText(formatResult);
        txtunit.setText(spinOutput.getSelectedItem().toString());

        mylist= processArea(strInput,dblInput);

        return this.mylist;

    }

    public void putInList(ArrayList<HashMap<String, String>> mylist) {
        setListAdapter(new SimpleAdapter(this, mylist, R.layout.con_activity_result, new String[]{"result"}, new int[]{R.id.item_result}));
    }

    public ArrayList<HashMap<String, String>> processArea(int strInput, Double dblInput) {
        ArrayList<HashMap<String, String>> mylist = new ArrayList();

        HashMap<String, String> map;
        NumberFormat format2 = new DecimalFormat("0.00");
        for(int i=0; i<23; i++) {
            map=new HashMap();
            //convertedValue.setText(String.format("%.04f", dimension_value[i]*dblInput/dimension_value[strInput]));

            double dblResult = dimension_value[i]*dblInput/dimension_value[strInput];
            String formatResult = format2.format(dblResult);
           //double dblResult = dimension_value[i]*dblInput/dimension_value[strInput];

            map.put("result", spinInput.getItemAtPosition(i).toString()+"   :   " + String.valueOf(formatResult));
            mylist.add(map);
        }

        return mylist;
    }

    /*@Override
    public void onBackPressed() {

        Intent i = new Intent(MainActivity.this, InActivity.class);
        sta
        rtActivity(i);
        finish();
    }*/
    private void clickCalculate(View view){
        inputMethodManagerHide(view);

        MainActivity.this.mylist = new ArrayList();
        String strX = MainActivity.this.editInput.getText().toString();
        if (strX.trim().equals("")) {
            Toast.makeText(MainActivity.this, "Please input the value", Toast.LENGTH_LONG).show();
        } else {
            converted_linear.setVisibility(View.VISIBLE);
            other_units.setVisibility(View.VISIBLE);
            Double dblInput = Double.valueOf(Double.valueOf(strX).doubleValue());

            MainActivity.this.mylist = MainActivity.this.doSelection(MainActivity.this.spinInput.getSelectedItemPosition(),MainActivity.this.spinOutput.getSelectedItemPosition(), dblInput);
//                    LoanMainActivity.this.toSelection(String.valueOf(LoanMainActivity.this.spinOutput.getSelectedItem()));
        }
        MainActivity.this.putInList(MainActivity.this.mylist);
    }

    private void inputMethodManagerHide(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
