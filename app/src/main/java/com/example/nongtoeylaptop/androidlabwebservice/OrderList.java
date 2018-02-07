package com.example.nongtoeylaptop.androidlabwebservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Intent intent = getIntent();
        ArrayList<OrderMenu> orderMenus = (ArrayList<OrderMenu>) getIntent().getSerializableExtra("orderMenus");
        LinearLayout orderListLinearLayout = (LinearLayout) findViewById(R.id.orderListLinearLayout);

        for(OrderMenu orders : orderMenus){
            TextView textView = new TextView(this);
            String item = "amount : "+orders.getAmount()
                    +" menu : "+orders.getMenu().getMenuName();
            textView.setText(item);
            orderListLinearLayout.addView(textView);
        }
    }
}
