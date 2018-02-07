package com.example.nongtoeylaptop.androidlabwebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nongtoeylaptop.androidlabwebservice.JSONCallServiceAsyncTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements CallBackService {

    private Spinner menuNameSpinner;
    private Button orderBtn;
    private Button sentOrderBtn;
    private TextView menuNameTextView;
    private TextView menuPriceTextView;
    private TextView menuDescriptionTextView;
    private ImageView menuImg;
    private Spinner menuAmountSpinner;
    private ArrayList<Menu> menusArrayList = new ArrayList<Menu>();
    private LinearLayout menusLinearLayout;
    private Integer[] amount = {1,2,3,4,5};
    private String[] categories = {"เมนูข้าว","ชุดสุดคุ้ม","ชุดอิ่มเดี่ยว"};
    private ArrayList<String> menuName = new ArrayList<String>();
    private ArrayList<OrderMenu> orderMenus = new ArrayList<OrderMenu>();
    private String urlEncoding = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuNameSpinner = (Spinner) findViewById(R.id.menuNameSpinner);
        menusLinearLayout = (LinearLayout) findViewById(R.id.menusLinearLayout);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        menuNameSpinner.setAdapter(categoriesAdapter);
        menuNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String url_json = "http://www.itsci.mju.ac.th/IT411Lab/GetProductByCategory?cname=";
                try {
                    urlEncoding = URLEncoder.encode(menuNameSpinner.getSelectedItem().toString(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url_json +=urlEncoding;
                new JSONCallServiceAsyncTask(MainActivity.this).execute(url_json);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
        menusLinearLayout.removeAllViews();
        for(int k=0; k< menusArrayList.size();k++) {
            final int MenuAmount =0;
            View menuLayouts = getLayoutInflater().inflate(R.layout.menu_layout,null);
            menuNameTextView = (TextView) menuLayouts.findViewById(R.id.menuNameTextView);
            menuNameTextView.setText(menusArrayList.get(k).getMenuName().toString());
            menuPriceTextView = (TextView) menuLayouts.findViewById(R.id.menuPriceTextView);
            menuPriceTextView.setText(menusArrayList.get(k).getMenuPrice().toString()+" บาท");
            menuDescriptionTextView = (TextView) menuLayouts.findViewById(R.id.menuDescription);
            menuDescriptionTextView.setText(menusArrayList.get(k).getMenuDiscription().toString());
            menuAmountSpinner = (Spinner) menuLayouts.findViewById(R.id.menuAmountSpinner);
            ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, amount);
            menuAmountSpinner.setAdapter(arrayAdapter);

            menuImg = (ImageView) menuLayouts.findViewById(R.id.Img);
            orderBtn = (Button) menuLayouts.findViewById(R.id.orderBtn);
            final int finalK = k;
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("ID : "+ orderBtn.getId());
                    System.out.println("Name "+menusArrayList.get(finalK).getMenuName());
                    Menu menu = menusArrayList.get(finalK);
//                    if(orderMenus.size()<1) {
//                        orderMenus.add(new OrderMenu(menu, Integer.parseInt(menuAmountSpinner.getSelectedItem().toString())));
//                    }else{
//                        for(int i = 0;i<orderMenus.size();i++){
//                            boolean found = false;
//                            if (orderMenus.get(i).getMenu().getMenuID().equals(menusArrayList.get(finalK1).getMenuID())) {
//                                int newAmount = orderMenus.get(i).getAmount() + MenuAmount;
//                                orderMenus.get(i).setAmount(newAmount);
//                                found = true;
//                            }
//                            if(found == false){
//                                orderMenus.add(new OrderMenu(menu, MenuAmount));
//                            }
//                            System.out.println("Pass");
//                        }
//                    }
                    orderMenus.add(new OrderMenu(menu, Integer.parseInt(menuAmountSpinner.getSelectedItem().toString())));
                    Toast.makeText(MainActivity.this,"เพื่มสำเร็จ",Toast.LENGTH_SHORT).show();
                    System.out.println("================================================");
                    System.out.println("Size : "+ orderMenus.size());
                    System.out.println("================================================");
                    System.out.println("amount : "+ MenuAmount);
                }
            });
            sentOrderBtn = (Button) findViewById(R.id.sentOrderBtn);
            sentOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(orderMenus.size()<1){
                        Toast.makeText(MainActivity.this,"กรุณาเลือกเมนูอย่างน้อย 1 รายการ",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(MainActivity.this,OrderList.class);
                        intent.putExtra("orderMenus",orderMenus);
                        startActivity(intent);
                    }
                }
            });
            Picasso.with(getApplicationContext()).load(menusArrayList.get(k).getMenuImg()).into(menuImg);


            menusLinearLayout.addView(menuLayouts);
        }

    }

    @Override
    public void onRequestFailed(String result) {

    }
}
