package com.example.nongtoeylaptop.androidlabwebservice;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by NongToey Laptop on 2/3/2018.
 */

public class JSONCallServiceAsyncTask extends AsyncTask<String,String,String> {
    CallBackService callBackService;

    public JSONCallServiceAsyncTask(CallBackService callBackService) {
        this.callBackService = callBackService;
    }


    @Override
    protected String doInBackground(String[] params) {
        Log.i("tag","Call Service");
        if(callBackService!=null){
            callBackService.onCallService();
        }
        return downloadContent(params[0]);
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(callBackService!=null){
            ArrayList<Menu> menus = onParserContentToModel(result);
            callBackService.onRequestCompleteListerner(menus);
        }
    }
    private String downloadContent(String myUrl){
        InputStream is = null;
        try {
            URL url = new URL(myUrl);
            Log.e("url", myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("test", "The response is : " + response);
            Log.e("response", "" + response);
            is = conn.getInputStream();
            Log.e("is", is.toString());
            String result = convertInputStreamToString(is);
            return result;
        }catch(Exception e) {
            Log.e("error exp", e.getMessage());
            return "error";
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String convertInputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine())!=null){
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }

    public ArrayList<Menu> onParserContentToModel(String dataJSon) {
        Log.e("data json", dataJSon);
        ArrayList<Menu> foodsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(dataJSon);
            JSONArray jsonArray = jsonObject.optJSONArray("menu");
            if (jsonArray != null) {
                for (int i = 0; i<jsonArray.length(); i++) {
                    Menu menu = new Menu();
                    JSONObject json = jsonArray.optJSONObject(i);
                    menu.setMenuID(json.getString("menu_id"));
                    menu.setMenuName(json.getString("menu_name"));
                    menu.setMenuPrice(json.getDouble("price"));
                    menu.setMenuDiscription(json.getString("description"));
                    menu.setMenuImg("http://www.itsci.mju.ac.th/IT411Lab/images/"+json.getString("menu_id")+".png");

                    foodsList.add(menu);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodsList;
    }
}
