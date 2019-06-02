package com.example.administrator.familydoctor.toolClass;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2017-09-17.
 */

public class PhotoHelper {

    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
    public static String SAVED_IMAGE_DIR_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + "/AppName/camera/";// 拍照路径
    String cameraPath;

    /**
     * 启动相机
     */
    public static String startCamera(Activity activity, int requestCode) {
        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            // 把文件地址转换成Uri格式
            Uri uri = Uri.fromFile(outFile);
            //LogUtil.d("getAbsolutePath=" + outFile.getAbsolutePath());
            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // 此值在最低质量最小文件尺寸时是0，在最高质量最大文件尺寸时是１
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            activity.startActivityForResult(intent, requestCode);
            return outFile.getAbsolutePath();
        } else {
            Toast.makeText(activity, "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static void openAlbum(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }
}
