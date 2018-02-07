package com.example.nongtoeylaptop.androidlabwebservice;

/**
 * Created by NongToey Laptop on 2/3/2018.
 */
import java.io.Serializable;
public class Menu implements Serializable{
    private String menuID;
    private String menuName;
    private Double menuPrice;
    private String menuDiscription;
    private String menuImg;


    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuDiscription() {
        return menuDiscription;
    }

    public void setMenuDiscription(String menuDiscription) {
        this.menuDiscription = menuDiscription;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }
}
