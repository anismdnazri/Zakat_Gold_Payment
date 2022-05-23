package com.example.zakatgoldpayment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText goldWght, goldVlue;
    Button calcBTN, resetBTN;
    TextView tvOutput1, tvOutput2, tvOutput3;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float gweight;
    float gvalue;


    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    private Menu menu;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        goldWght = (EditText) findViewById(R.id.goldWeight);
        goldVlue = (EditText) findViewById(R.id.goldValue);
        calcBTN = (Button) findViewById(R.id.CalcBTN);
        tvOutput1 = (TextView) findViewById(R.id.totalGold);
        tvOutput2 = (TextView) findViewById(R.id.zakatPaymnt);
        tvOutput3 = (TextView) findViewById(R.id.totalZakat);
        resetBTN = (Button) findViewById(R.id.ResetBTN);

        calcBTN.setOnClickListener(this);
        resetBTN.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("value", 0.0F);

        goldWght.setText(""+gweight);
        goldVlue.setText(""+gvalue);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.aboutus:
                //Toast.makeText(this,"About Zakat Emas",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.CalcBTN:
                    calc();
                    break;

                case R.id.ResetBTN:
                    goldWght.setText("");
                    goldVlue.setText("");
                    tvOutput1.setText("");
                    tvOutput2.setText("");
                    tvOutput3.setText("");

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter number to proceed the calculation", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(goldWght.getText().toString());
        float gvalue = Float.parseFloat(goldVlue.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double totGvalue;
        double uruf;
        double Zakatpayable;
        double totZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gweight);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();


        if (stat.equals("Keep")){
            totGvalue= gweight * gvalue;
            uruf= gweight - 85;

            if(uruf>=0.0) {
                Zakatpayable = uruf * gvalue;
                totZakat = Zakatpayable * 0.025;
            }

            else{
                Zakatpayable = 0.0;
                totZakat = Zakatpayable * 0.025;

            }

            tvOutput1.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(Zakatpayable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));
        }

        else{
            totGvalue= gweight * gvalue;
            uruf= gweight - 200;

            if(uruf>=0.0) {
                Zakatpayable = uruf * gvalue;
                totZakat = Zakatpayable * 0.025;
            }

            else{
                Zakatpayable = 0.0;
                totZakat = Zakatpayable * 0.025;

            }

            tvOutput1.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(Zakatpayable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));

        }

    }


}