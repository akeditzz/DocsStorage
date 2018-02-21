package com.example.akshaymanagooli.docsstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isDir = true;
    File[] files ;

    RecyclerView recyclerView;
    DocumentListAdapter documentListAdapter;
    ArrayList<File> finallist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        isStoragePermissionGranted(1);



    }

    public boolean isStoragePermissionGranted(int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Scrolling", "Permission is granted");
                switch (i) {
                    case 0:
//                        getPic();
                        break;
                    case 1:
                        directory();
                        break;
                    case 2:
//                        openCamera();
                        break;
                }


                return true;
            } else {

                Log.v("Scrolling", "Permission is revoked");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, i);
                return false;
            }


        } else {
            Log.v("Scrolling", "Permission is granted");
            switch (i) {
                case 0:
//                    getPic();
                    break;
                case 1:
                    directory();
                    break;
                case 2:
//                    openCamera();
                    break;
            }


            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Scrolling", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            switch (requestCode) {
                case 0:
//                    getPic();
                    break;
                case 1:
                    directory();
                    break;
                case 2:
//                    openCamera();
                    break;
            }


        } else {

        }
    }


    private void directory(){

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() ;
        Log.d("Files", "Path: " + path);
        File directory = new File(path);

//        files = file.listFiles();
        ArrayList<File> inFiles ;
        inFiles = getListFiles(directory);


        for (int i = 0; i < inFiles.size(); i++) {

            try {
                String filenameArray[] = inFiles.get(i).getName().split("\\.");
                String extension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(inFiles.get(i).getAbsolutePath()));


                if (extension.equalsIgnoreCase("application/pdf") || extension.equalsIgnoreCase("application/msword") || extension.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    finallist.add(inFiles.get(i));
                    Log.d("FilesDirectory", "FileName:" + inFiles.get(i).getName()+" FilePath: "+inFiles.get(i).getAbsolutePath() +"Filetype: "+ MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(inFiles.get(i).getAbsolutePath())));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        documentListAdapter = new DocumentListAdapter(finallist,this);
        recyclerView.setAdapter(documentListAdapter);


    }
    ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

}
