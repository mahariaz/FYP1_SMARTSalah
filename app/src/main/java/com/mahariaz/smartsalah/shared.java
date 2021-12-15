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

    public static String curr_salah;
    public static String curr_rakah;
    public static String curr_status;

    //userbio
    public static String email;
    public static String username;
    public static String gender;
    public static Uri dp_uri;

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }
}
