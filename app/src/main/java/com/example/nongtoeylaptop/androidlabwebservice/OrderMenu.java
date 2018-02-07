package com.example.nongtoeylaptop.androidlabwebservice;

/**
 * Created by Tanapone on 8/2/2561.
 */
import java.io.Serializable;
public class OrderMenu implements Serializable{
    private Menu menu;
    private int amount;

    public OrderMenu(Menu menu, int amount) {
        this.menu = menu;
        this.amount = amount;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
