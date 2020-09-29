package com.ws.support.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 图片工具类
 *
 * @author Johnny.xu
 *         date 2017/7/5
 */
public class BitmapUtil {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    /**
     *截图
     *@author ws
     *2020/7/8 15:52
     */
    public static Bitmap getBitmapOfView(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(),
                    View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                    v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    /**
     * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
     * @param path 图片路径
     * @param viewWidth 目标宽度
     * @param viewHeight 目标高度
     * @return Bitmap 目标图片
     */
    private static Bitmap getThumbBitmapForFile(String path, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);

        //设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
     * @param options 图片属性
     * @param viewWidth  目标宽度
     * @param viewHeight 目标高度
     */
    private static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight){
        int inSampleSize = 1;
        if(viewWidth == 0 || viewHeight == 0){
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        //假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
        if(bitmapWidth > viewWidth || bitmapHeight > viewWidth){
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewWidth);

            //为了保证图片不缩放变形，我们取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        return inSampleSize;
    }

    public static boolean saveImage(Bitmap bitmap) {
        boolean isOk = false;
        String fname = MyFileUtils.getImgDir() + "/" + UUIDGenerator.getUUID() + ".png";
        if (bitmap != null) {
            System.out.println("bitmap got!");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                isOk = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {}
                }
            }
        } else {
            System.out.println("bitmap is NULL!");
        }
        return isOk;
    }

    public static boolean saveImage(String fileName, Bitmap bitmap) {
        boolean isOk = false;
        String fname = MyFileUtils.getImgDir() + "/" + fileName;
        if (bitmap != null) {
            System.out.println("bitmap got!");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                isOk = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {}
                }
            }
        } else {
            System.out.println("bitmap is NULL!");
        }
        return isOk;
    }

    public static String saveImage70(Context context, String filepath) {
        Bitmap bitmap = scaleImage(filepath, 640, 480);
        // 为了防止重复 这里用uuid作为名字
        String uuid = UUIDGenerator.getUUID();
        String picPath = MyFileUtils.getImgDir() + "/" + uuid + ".jpg";
        java.io.File file = new File(picPath);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outStream)) {
                outStream.flush();
                outStream.close();
            }
            ContentResolver cr = context.getContentResolver();
            MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
            updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
            bitmap.recycle();
            return picPath;
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return "";
    }

    public static String saveImage(Context context, String filepath) {
        Bitmap bitmap = scaleImage(filepath, 720, 1280);
        // 为了防止重复 这里用uuid作为名字
        String uuid = UUIDGenerator.getUUID();
        String picPath = MyFileUtils.getImgDir() + "/" + uuid + ".jpg";
        java.io.File file = new File(picPath);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)) {
                outStream.flush();
                outStream.close();
            }
            ContentResolver cr = context.getContentResolver();
            MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
            updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
            bitmap.recycle();
            return picPath;
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return "";
    }

    public static String saveImage_480_720(Context context, String filepath) {
        Bitmap bitmap = scaleImage(filepath, 480, 720);
        // 为了防止重复 这里用uuid作为名字
        String uuid = UUIDGenerator.getUUID();
        String picPath = MyFileUtils.getImgDir() + "/" + uuid + ".jpg";
        java.io.File file = new File(picPath);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream)) {
                outStream.flush();
                outStream.close();
            }
            ContentResolver cr = context.getContentResolver();
//			MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
//			updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
            bitmap.recycle();
//			updateGalleryNew(context, picPath);
            return picPath;
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return "";
    }

    public static String saveImage_1280_1920(Context context, String filepath) {
        Bitmap bitmap = scaleImage(filepath, 1280, 1920);
        // 为了防止重复 这里用uuid作为名字
        String uuid = UUIDGenerator.getUUID();
        String picPath = MyFileUtils.getImgDir() + "/" + uuid + ".jpg";
        java.io.File file = new File(picPath);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream)) {
                outStream.flush();
                outStream.close();
            }
            ContentResolver cr = context.getContentResolver();
//			MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
//			updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
            bitmap.recycle();
//			updateGalleryNew(context, picPath);
            return picPath;
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return "";
    }

    /**
     * 不改变图片大小压缩图片 默认压缩到1024kb
     * @param context 上下文
     * @param filepath 图片地址
     */
    public static String saveImageBySize(Context context, String filepath) {
        return saveImageBySize(context, filepath, 1024);
    }

    /**
     * 不改变图片大小压缩图片
     * @param context 上下文
     * @param filepath 图片地址
     * @param size 图片大小
     */
    public static String saveImageBySize(Context context, String filepath, int size) {
        Bitmap bitmap = scaleImage(filepath, 1280, 1920);
        // 为了防止重复 这里用uuid作为名字
        String uuid = UUIDGenerator.getUUID();
        String picPath = MyFileUtils.getImgDir() + "/" + uuid + ".jpg";
        java.io.File file = new File(picPath);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream)) {
                outStream.flush();
                outStream.close();
            }
            ContentResolver cr = context.getContentResolver();
//			MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
//			updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
            bitmap.recycle();
//			updateGalleryNew(context, picPath);
            return picPath;
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return "";
    }
    private static MediaScannerConnection msc;

    public synchronized static void updateGalleryNew(Context context, final String imagePath) {
        try {
            final String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, "", "");
            if (StringUtils.isNotEmptyWithNull(path)) {
                msc	= new MediaScannerConnection(context, new MediaScannerConnection.MediaScannerConnectionClient() {

                    public void onMediaScannerConnected() {
                        if (msc != null) msc.scanFile(path, "image/jpeg");
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        if (msc != null) msc.disconnect();
                    }
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveImage(byte[] data) {
        boolean isOk = false;
        String fileName = MyFileUtils.getImgDir() + "/" + UUIDGenerator.getUUID() + ".png";
        if (data != null) {
            System.out.println("bitmap got!");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName);
                fos.write(data);
                isOk = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {}
                }
            }
        } else {
            System.out.println("bitmap is NULL!");
        }
        return isOk;
    }

    public static Bitmap scaleImage(String imagePath, int requestWidth, int requestHeight) {
        Bitmap newBitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);

            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);

            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            String orientation = getExifOrientation(imagePath, "0");

            Matrix matrix = new Matrix();
            matrix.postRotate(Float.valueOf(orientation));

            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        } catch (Exception e) {
            Logger.v("ImageUtils", "scaleImage error");
        }

        return newBitmap;
    }

    public static String getExifOrientation(String path, String orientation) {
        Method exif_getAttribute;
        Constructor<ExifInterface> exif_construct;
        String exifOrientation = "";

        int sdk_int = 0;
        try {
            sdk_int = Integer.valueOf(Build.VERSION.SDK);
        } catch (Exception e1) {
            sdk_int = 3; // assume they are on cupcake
        }
        if (sdk_int >= 5) {
            try {
                exif_construct = ExifInterface.class
                        .getConstructor(new Class[] { String.class });
                Object exif = exif_construct.newInstance(path);
                exif_getAttribute = ExifInterface.class
                        .getMethod("getAttribute", new Class[] { String.class });
                try {
                    exifOrientation = (String) exif_getAttribute.invoke(exif,
                            ExifInterface.TAG_ORIENTATION);
                    if (exifOrientation != null) {
                        if (exifOrientation.equals("1")) {
                            orientation = "0";
                        } else if (exifOrientation.equals("3")) {
                            orientation = "180";
                        } else if (exifOrientation.equals("6")) {
                            orientation = "90";
                        } else if (exifOrientation.equals("8")) {
                            orientation = "270";
                        }
                    } else {
                        orientation = "0";
                    }
                } catch (InvocationTargetException ite) {
                    orientation = "0";
                } catch (IllegalAccessException ie) {
                    System.err.println("unexpected " + ie);
                    orientation = "0";
                }
            } catch (NoSuchMethodException nsme) {
                orientation = "0";
            } catch (IllegalArgumentException e) {
                orientation = "0";
            } catch (InstantiationException e) {
                orientation = "0";
            } catch (IllegalAccessException e) {
                orientation = "0";
            } catch (InvocationTargetException e) {
                orientation = "0";
            }
        }
        return orientation;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqW, int reqH) {
        final int h = options.outHeight;
        final int w = options.outWidth;
        int inSampleSize = 1;

        if (h > reqH || w > reqW) {
            final int heightRatio = Math.round((float) h / (float) reqH);
            final int widthRatio = Math.round((float) w / (float) reqW);

            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    public static void updateGallery(Context context, String filename) {
        MediaScannerConnection.scanFile(context, new String[] { filename }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }
}
