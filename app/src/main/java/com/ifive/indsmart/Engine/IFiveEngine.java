package com.ifive.indsmart.Engine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IFiveEngine {

    public static IFiveEngine myInstance = new IFiveEngine();
    public Integer marginSelected = 0;
    public Calendar commonCalender;
    public ArrayList<Integer> selectedProducts = new ArrayList<>();
    DecimalFormat mFormat = new DecimalFormat("00");

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    public void snackbarNoInternet(Context context) {
        Snackbar snackbar = Snackbar
                .make(((Activity)context).findViewById(android.R.id.content),
                        "No Network Connection!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    public static ProgressDialog getProgDialog(Context context) {
        ProgressDialog progDialog = new ProgressDialog(context);
        progDialog.setMessage("Please wait. Loading...");
        progDialog.setIndeterminate(false);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setCancelable(false);
        return progDialog;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public SpannableString getTitleSpan(String s, Context context) {
        Typeface typeface = getCommonTypeFace(context);
        SpannableString titleSpan = new SpannableString(s);
        titleSpan.setSpan(new CustomTypefaceSpan("", typeface), 0,
                titleSpan.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return titleSpan;
    }

    public Typeface getCommonTypeFace(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/helvetica.ttf");
        return font;
    }

    public Typeface getSFATypeFace(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/ethnocentric.ttf");
        font.isBold();
        return font;
    }

    public Typeface getHeadTypeFace(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/helvetica.ttf");
        font.isBold();
        return font;
    }

    public boolean isEmpty(String string) {
        return string.trim().equals("");
    }

    public double convertStringToDouble(String text) {
        double number = 0;
        try {
            number = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.out.println ("ldkjldk"+e);
        }
        return number;
    }

    public int convertDoubleToInt(double text) {
        int number = 0;
        try {
            number = (int) Math.floor(text);
        } catch (NumberFormatException e) {
        }
        return number;
    }

    public int convertStringToInt(String text) {
        int number = 0;
        try {
            number = Integer.parseInt(text);
        } catch (NumberFormatException e) {
        }
        return number;
    }

    public double getScheme(double totalAmount, double schemeSelected) {
        try {
            return (totalAmount * schemeSelected) / 100;
        } catch (Exception e) {

        }
        return 0;
    }

    public Animation getBlinkAnimation() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }

    public String getCalenderTimeNoS2(Calendar myCalendar) {
        DecimalFormat mFormat = new DecimalFormat("00");
        String date = myCalendar.get(Calendar.DAY_OF_MONTH) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MONTH)) + 1))) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.YEAR))))) +
                " " + myCalendar.get(Calendar.HOUR_OF_DAY) +
                ":" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MINUTE)))));
        return date;
    }

    public String getCalenderTimeNoS(Calendar myCalendar) {
        DecimalFormat mFormat = new DecimalFormat("00");
        String date = myCalendar.get(Calendar.YEAR) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MONTH)) + 1))) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.DAY_OF_MONTH))))) +
                " " + myCalendar.get(Calendar.HOUR_OF_DAY) +
                ":" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MINUTE)))));
        return date;
    }

    public String getCalenderTime(Calendar myCalendar) {
        DecimalFormat mFormat = new DecimalFormat("00");
        String date = myCalendar.get(Calendar.YEAR) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MONTH)) + 1))) +
                "-" + (mFormat.format(Double.valueOf((myCalendar.get(Calendar.DAY_OF_MONTH))))) +
                " " + myCalendar.get(Calendar.HOUR_OF_DAY) +
                ":" + myCalendar.get(Calendar.MINUTE) +
                ":" + myCalendar.get(Calendar.SECOND);
        return date;
    }

    public String getWholeCalenderDate(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        return format.format(myCalendar.getTime());
    }

    public String getMonthCalenderDate(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM - yyyy");
        return format.format(myCalendar.getTime());
    }

    public String getSimpleSlashCalenderDate(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(myCalendar.getTime());
    }

    public String getSimpleCalenderDate(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(myCalendar.getTime());
    }

    public String getSimpleCalenderDate2(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(myCalendar.getTime());
    }

    public String getServerCalenderDate(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(myCalendar.getTime());
    }

    public String getServerDateTime(Calendar myCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(myCalendar.getTime());
    }

    public String getCalenderDate(Calendar myCalendar) {
        String date = myCalendar.get(Calendar.YEAR) + "-" +
                (mFormat.format(Double.valueOf((myCalendar.get(Calendar.MONTH)) + 1))) + "-" +
                (mFormat.format(Double.valueOf((myCalendar.get(Calendar.DAY_OF_MONTH)))));
        return date;
    }

    public Calendar formatStringToCalender(String reqDate) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(sdf.parse(reqDate));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return Calendar.getInstance();
    }

    public Calendar formatServerStringToCalender(String reqDate) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(reqDate));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return Calendar.getInstance();
    }

    public String formatDate(String reqDate) {
       if( check (reqDate)){
           Log.d ("sadsas","true");
       }else{
           Log.d ("sadsas","false");
       }

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.format(sdf1.parse(reqDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }
    public Boolean check(String reqDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(reqDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public String formatTime(String reqDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        try {
            String formatString = sdf1.format(sdf.parse(reqDate));
            return (formatString.equals("00:00")) ? "" : formatString;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }

    public String onlyDay(String reqDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE");
        try {
            return sdf1.format(sdf.parse(reqDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }

    public String onlyDate(String reqDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd");
        try {
            return sdf1.format(sdf.parse(reqDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }

    public String formatDate2(String reqDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return sdf1.format(sdf.parse(reqDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }

    public String formatDateTime(String reqDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            return sdf1.format(sdf.parse(reqDate));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("bad pattern");
        }
        return "";
    }

    public String getFormatedTime(int h, int m, Context context) {
        final String OLD_FORMAT = "HH:mm";
        final String NEW_FORMAT = "hh:mm a";
        String oldDateString = h + ":" + m;
        String newDateString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, getCurrentLocale(context));
            Date d = sdf.parse(oldDateString);
            sdf.applyPattern(NEW_FORMAT);
            newDateString = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDateString;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    public String formatAmount(double amount) {
        if (amount == 0.0) {
            return "0";
        } else {
            DecimalFormat formatter = new DecimalFormat("#,##,###");
            String formatAmount = formatter.format(amount);
            return formatAmount;
        }
    }

    public Date getStringToCalendar(String startTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            date = sdf.parse(startTime);
        } catch (ParseException e) {
            return date;
        }
        return date;
    }


}
