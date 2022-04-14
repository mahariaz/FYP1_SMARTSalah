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

    //wich screen
    String which_screen;


}
