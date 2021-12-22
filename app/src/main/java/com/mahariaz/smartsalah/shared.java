package com.mahariaz.smartsalah;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

public class shared {
    public shared(Context context) {
        this.context = context;
    }


    private Context context;

    public static String curr_salah="Fajr";
    public static String curr_rakah="1";
    public static String curr_status;

    //userbio
    public static String email="maha7@gmail.com";
    public static String username="maha12";
    public static String gender="female";
    public static String age="12";
    public static String height="4.10";
    public static Uri  selectedImage;

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }
}
