package com.mahariaz.smartsalah;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class shared {
    // color pallette
    ArrayList<String> mycolor = new ArrayList<String>();
    List<String> color = Arrays.asList("#89CFF0","#BF94E4","#D19FE8","#E2A76F","#536872","#E4717A");
    public static int fajarweek1=0,zuhrweek1=0,asrweek1=0,mgbweek1=0,ishaweek1=0;
    public shared(Context context) {

        this.context = context;
        mycolor.addAll(color);
    }


    private Context context;

    public static String curr_salah="Fajr";
    public static String curr_rakah="1";

    //userbio
    public static String email="maha7@gmail.com";
    public static String username="maha12";
    public static String gender="female";
    public static String age="12";
    public static String height="4.10";
    public static Uri  selectedImage;
    public static Boolean isZuhrSunnah4Prayed=false;
    public static Boolean isZuhrFarz4Prayed=false;
    public static Boolean isZuhrSunnah2Prayed=false;
    public static Boolean isZuhrNafl2Prayed=false;



    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }



}

