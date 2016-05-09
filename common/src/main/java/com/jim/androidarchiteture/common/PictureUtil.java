package com.jim.androidarchiteture.common;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureUtil {

    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        int degree = readPictureDegree(filePath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap newBitmap = BitmapFactory.decodeFile(filePath, options);

        newBitmap = rotatingImageView(degree, newBitmap);
        return newBitmap;
    }

    public static Bitmap getBigBitmap(String filePath) {
        int degree = readPictureDegree(filePath);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 720, 960);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap newBitmap = BitmapFactory.decodeFile(filePath, options);

        newBitmap = rotatingImageView(degree, newBitmap);
        return newBitmap;
    }


    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param in
     * @return
     */
    public static Bitmap getSmallBitmap(InputStream in) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(in, null, options);

    }

    public static boolean saveBitmapToFile(File pStoreFile, Bitmap pBitmap) {
        boolean isSuccess;
        FileOutputStream fOut;

        File parentFile = pStoreFile.getParentFile();
        if (!pStoreFile.exists()) {
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                pStoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            fOut = new FileOutputStream(pStoreFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        isSuccess = pBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fOut);
        if (null != fOut) {
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
        * 旋转图片
        * @param angle
        * @param bitmap
        * @return Bitmap
        */
    public static Bitmap rotatingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

//    // TODO TBC
//    public static File saveBitmapToSdCard(String bitName, Bitmap mBitmap) {
//        File f = new File(AppSetting.IMAGE_ROOT, bitName);
//
//        saveBitmapToFile(f, mBitmap);
//        return f.getAbsoluteFile();
//    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir() {
        File dir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                getAlbumName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String getImagePathFromUri(Activity context, Uri fileUrl) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") == 0) {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(fileUrl,
                        null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!fileName.startsWith("/storage")
                            && !fileName.startsWith("/mnt")) {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0) // file:///开头的uri
            {
                fileName = filePathUri.toString();// 替换file://
                fileName = filePathUri.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName = index == -1 ? fileName : fileName.substring(index);

                if (!fileName.startsWith("/mnt")) {
                    // 加上"/mnt"头
                    fileName += "/mnt";
                }
            }
        }
        return fileName;
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
        return "sheguantong";
    }
}