package com.lexinsmart.xushun.fileutils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.EventListener;
import java.util.RandomAccess;
import java.util.logging.Logger;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    String TAG = "main";
    final String FILE_NAME = "/crazyit.bin";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // /data
        Log.d(TAG, "Environment.getDataDirectory: " + Environment.getDataDirectory());

        //   /data/user/0/com.lexinsmart.xushun.fileutils/files
        Log.d(TAG, "getFilesDir: " + getFilesDir());

        ///data/user/0/com.lexinsmart.xushun.fileutils/cache
        Log.d(TAG, "getCacheDir:" + getCacheDir());

        // /storage/emulated/0
        Log.d(TAG, "Environment.getExternalStorageDirectory :   " + Environment.getExternalStorageDirectory());


        //  /storage/emulated/0/dir1
        Log.d(TAG, "Environment.getExternalStoragePublicDirectory: " + Environment.getExternalStoragePublicDirectory("dir1"));


        //  /storage/emulated/0/Android/data/com.lexinsmart.xushun.fileutils/files
        Log.d(TAG, "Context.getExternalFilesDir: " + getExternalFilesDir(null));

        //  /storage/emulated/0/Android/data/com.lexinsmart.xushun.fileutils/files/Music
        Log.d(TAG, "Context.getExternalFilesDir: " + getExternalFilesDir(Environment.DIRECTORY_MUSIC));
        //  /storage/emulated/0/Android/data/com.lexinsmart.xushun.fileutils/files/Pictures
        Log.d(TAG, "Context.getExternalFilesDir: " + getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        // /storage/emulated/0/Android/data/com.lexinsmart.xushun.fileutils/cache
        Log.d(TAG, "getExternalCacheDir(): " + getExternalCacheDir());

        write("ddddd");

        checkExternalStoragePermission(this);

    }

    private void write(String content) {
        try {
            // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获取SD卡的目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir
                        .getCanonicalPath() + FILE_NAME);
                // 以指定文件创建 RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(
                        targetFile, "rw");
                // 将文件记录指针移动到最后
                raf.seek(targetFile.length());
                // 输出文件内容
                raf.write(content.getBytes());
                // 关闭RandomAccessFile
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final int REQUEST_EXTERNAL_PERMISSION_CODE = 666;


    public boolean checkExternalStoragePermission(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }

        int readStoragePermissionState = ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);
        int writeStoragePermissionState = ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);
        boolean externalStoragePermissionGranted = readStoragePermissionState == PackageManager.PERMISSION_GRANTED &&
                writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;
        if (!externalStoragePermissionGranted) {
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_PERMISSION_CODE);
        }

        return externalStoragePermissionGranted;
    }

        @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_EXTERNAL_PERMISSION_CODE) {
//                if (checkExternalStoragePermission(getActivity())) {
//                    // Continue with your action after permission request succeed
//                }
            }
        }
    }


}
