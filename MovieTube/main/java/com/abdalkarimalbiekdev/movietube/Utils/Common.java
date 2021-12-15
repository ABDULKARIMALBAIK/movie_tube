package com.abdalkarimalbiekdev.movietube.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import com.abdalkarimalbiekdev.movietube.Model.User;
import com.abdalkarimalbiekdev.movietube.Remote.MovieAPI;
import com.abdalkarimalbiekdev.movietube.Remote.RetrofitClient;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class Common {

    public static User currentUser;
    public static String BASE_URL = ".....................";
    public static String SECRET_KEYWORD = "....................";
    public static String JWT_AUTH_KEY = "....................................................";

    public static String getPathImages(){

        File file = new File(new StringBuilder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .append("/")
                .append("MovieTube").toString());

        if (!file.isDirectory())
            file.mkdir();

        return new StringBuilder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .append("/")
                .append("MovieTube").toString();
    }

    public static File createFile(Bitmap bitmap){

        //Make sure you set permission Read/Write external storage AND set Provider that exists in Manifest

        String file_path = getPathImages();
        File dir = new File(file_path);
//        if(!dir.exists())
//            dir.mkdirs();
        File file = new File(dir.getAbsoluteFile(), new StringBuilder(UUID.randomUUID().toString()).append(".png").toString());
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    public static MovieAPI getAPI() {
        return RetrofitClient.getInstance(BASE_URL).create(MovieAPI.class);
    }

    public static String getToken(){
        return "Bearer " + currentUser.getToken();
    }

    //This method check if device connected to network (not internet)
    public static boolean isConnectionToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //This method check if device connected to internet
    public static boolean isConnectionToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){

                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
