package com.example.nongtoeylaptop.androidlabwebservice;

import java.util.ArrayList;

/**
 * Created by NongToey Laptop on 2/3/2018.
 */

public interface CallBackService {
    void onPreCallService();
    void onCallService();
    void onRequestCompleteListerner(ArrayList<Menu> menusArrayList);
    void onRequestFailed(String result);
}
