package com.tcl.mie.jnidemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("lame");// the native method
    }
    private EditText et_wav;
    private EditText et_mp3;
    private ProgressDialog pd;
    private Button btn_convert;

    /**
     * wav to mp3
     *
     * @param wav
     * @param mp3
     */
    public native void convertmp3(String wav, String mp3);

    /**
     * get lame version
     *
     * @return
     */
    public native String getLameVersion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();
        et_wav = (EditText) this.findViewById(R.id.et_wav);
        et_mp3 = (EditText) this.findViewById(R.id.et_mp3);
        btn_convert = (Button) this.findViewById(R.id.convert);
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert(v);
            }
        });
        pd = new ProgressDialog(this);
    }


    /**
     * wav to mp3
     */
    public void convert(View view) {
        final String mp3Path = et_mp3.getText().toString().trim();
        final String wavPath = et_wav.getText().toString().trim();
        File file = new File(wavPath);
        int size = (int) file.length();
        System.out.println("file size " + size);
        if ("".equals(mp3Path) || "".equals(wavPath)) {
            Toast.makeText(MainActivity.this, "file can't be null", Toast.LENGTH_SHORT).show();
            return;
        }
        pd.setMessage("converting....");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(size);
        pd.setCancelable(false);
        pd.show();

        new Thread() {

            @Override
            public void run() {
                convertmp3(wavPath, mp3Path);
                pd.dismiss();
            }

        }.start();



    }

    /**
     *
     *
     * @param progress
     */
    public void setConvertProgress(int progress) {
        pd.setProgress(progress);
    }

    /**
     *
     */
    public void getVersion(View view) {
        Toast.makeText(MainActivity.this, getLameVersion(), Toast.LENGTH_SHORT).show();
    }



    private final static int REQUEST_NEED_PERMISSION_CODE = 1000;

    @TargetApi(Build.VERSION_CODES.M)
    private boolean requestPermissions() {
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int perm = ContextCompat.checkSelfPermission(this, permission);
        if (perm == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            return false;
        }

        requestPermissions(new String[] {permission}, REQUEST_NEED_PERMISSION_CODE);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_NEED_PERMISSION_CODE && grantResults.length > 0) {
            int perm = grantResults[0];
            if (perm == PackageManager.PERMISSION_GRANTED) {}
        }
    }
}
