package com.ifive.indsmart.Connectivity;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    public void setPreferences(Context context, ResponseResult loginResponse){
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("Token", loginResponse.getMToken ());
        editor.putString("EmployeeId",loginResponse.getId ()+"");
        editor.putString("group_id",loginResponse.getGroupId ()+"");
        editor.putString("user",loginResponse.getFirstName ()+"");
        editor.putString("email",loginResponse.getEmail ()+"");
      //  editor.putString("mobile",loginResponse.getMobileNo ()+"");
     //   editor.putString("emp_type",loginResponse.getEmployeeType ()+"");
        editor.commit();
    }


//    public void setProfileImage(Context context, String profileImage) {
//        SharedPreferences.Editor editor = context.getSharedPreferences("easunmr", Context.MODE_PRIVATE).edit();
//        editor.putString("ProfileImage", profileImage);
//        editor.commit();
//    }

    public String getToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        String token = prefs.getString("Token", null);
        return token;
    }  public String getType(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        String emptype = prefs.getString("emp_type", null);
        return emptype;
    }

    public String getempnum(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modineprofile", Context.MODE_PRIVATE);
        String token = prefs.getString("employee_number", null);
        return token;
    }  public String getfname(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modineprofile", Context.MODE_PRIVATE);
        String token = prefs.getString("first_name", null);
        return token;
    }  public String getleavedate(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modineprofile", Context.MODE_PRIVATE);
        String token = prefs.getString("date_of_leaving", null);
        return token;
    }public String getphone(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modineprofile", Context.MODE_PRIVATE);
        String token = prefs.getString("work_telephone_number", null);
        return token;
    }public String getemail(Context context){
        SharedPreferences prefs = context.getSharedPreferences("modineprofile", Context.MODE_PRIVATE);
        String token = prefs.getString("email_id", null);
        return token;
    }


    public String getLastSync(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        return prefs.getString("lastSync", " - ");
    }

    public boolean getSync(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        return prefs.getBoolean("sync", false);
    }

    public int getStoreType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Muktha", Context.MODE_PRIVATE);
        return prefs.getInt("storeType", 2);
    }


    public void setStartEmployeeWorkTime(String calenderTime,Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("startTime", calenderTime);
        editor.commit();
    }

    public void setStartEmployeeNull(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("startTime", null);
        editor.commit();
    }

    public void setEndEmployeeWorkTime(String calenderTime,Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("endTime", calenderTime);
        editor.commit();
    }

    public void setEndEmployeeNull(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("endTime", null);
        editor.commit();
    }

    public void logoutSession(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getStartTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        return prefs.getString("startTime", null);
    }

    public String getEndTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        return prefs.getString("endTime", null);
    }

    public void setFCMToken(Context context, String refreshedToken) {
        SharedPreferences.Editor editor = context.getSharedPreferences("modine", Context.MODE_PRIVATE).edit();
        editor.putString("refreshedToken", refreshedToken);
        editor.commit();
    }

    public String getFCMToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("modine", Context.MODE_PRIVATE);
        return prefs.getString("refreshedToken", null);
    }
}
