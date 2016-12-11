package cn.s07150818edu.camerademo;


import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private ImageView iamgesview;
    private File file;
    private android.hardware.Camera camera;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mSurfaceview= (SurfaceView) this.findViewById(R.id.surfaceview);
        SurfaceHolder mSurfacehodler=mSurfaceview.getHolder();
        mSurfacehodler.addCallback(this);
        mSurfacehodler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
            camera= Camera.open();
           Camera.Parameters parameters=camera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            camera.release();
            camera=null;

        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    android.hardware.Camera.PictureCallback pictureCallback=new android.hardware.Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
                if (bytes!=null){
                    ssavePicture(bytes);
                }
        }
    };
    public  void  ssavePicture(byte [] bytes){
        try { String imageId=System.currentTimeMillis()+"";
        String pathName=android.os.Environment.getExternalStorageDirectory().getPath()+"/";
        File file=new File(pathName);
        if (!file.exists()){
            file.mkdir();
        }
        pathName+=imageId+"jpeg";
        file=new File(pathName);
        if (!file.exists()){

                file.createNewFile();
        }
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            Toast.makeText(this,"已经保存路径"+pathName,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
