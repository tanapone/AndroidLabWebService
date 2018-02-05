package com.example.nongtoeylaptop.androidlabwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nongtoeylaptop.androidlabwebservice.JSONCallServiceAsyncTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CallBackService {

    private Spinner menuNameSpinner;
    private Button orderBtn;
    private TextView menuNameTextView;
    private TextView menuPriceTextView;
    private TextView menuDescriptionTextView;
    private ImageView menuImg;
    private Spinner menuAmountSpinner;
    private ArrayList<Menu> menusArrayList = new ArrayList<Menu>();
    private Integer[] amount = {1,2,3,4,5};
    private ArrayList<String> menuName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuNameSpinner = (Spinner) findViewById(R.id.menuNameSpinner);
        orderBtn = (Button) findViewById(R.id.orderBtn);
        menuNameTextView = (TextView) findViewById(R.id.menuNameTextView);
        menuPriceTextView = (TextView) findViewById(R.id.menuPriceTextView);
        menuDescriptionTextView = (TextView) findViewById(R.id.menuDescription);
        menuAmountSpinner = (Spinner) findViewById(R.id.menuAmountSpinner);
        menuImg = (ImageView) findViewById(R.id.Img);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,amount);
        menuAmountSpinner.setAdapter(arrayAdapter);

        String url_json = "http://www.itsci.mju.ac.th/IT411Lab/GetProductByCategory?cname=%E0%B9%80%E0%B8%A1%E0%B8%99%E0%B8%B9%E0%B8%82%E0%B9%89%E0%B8%B2%E0%B8%A7";
        new JSONCallServiceAsyncTask(this).execute(url_json);

    }

    @Override
    public void onPreCallService() {

    }

    @Override
    public void onCallService() {

    }

    @Override
    public void onRequestCompleteListerner(final ArrayList<Menu> menusArrayList) {
        this.menusArrayList = menusArrayList;
        for(Menu menus : menusArrayList){
            menuName.add(menus.getMenuName());
        }
        ArrayAdapter<String> menuNameAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,menuName);
        menuNameSpinner.setAdapter(menuNameAdapter);

        menuNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(Menu menus : menusArrayList){
                    if(menuNameSpinner.getSelectedItem().toString().equals(menus.getMenuName())){
                        menuNameTextView.setText(menus.getMenuName());
                        menuPriceTextView.setText(menus.getMenuPrice().toString());
                        menuDescriptionTextView.setText(menus.getMenuDiscription());
                        Picasso.with(getApplicationContext())
                                .load(menus.getMenuImg())
                                .error(R.drawable.ic_error_outline_black_24dp)
                                .into(menuImg);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onRequestFailed(String result) {

    }
}
