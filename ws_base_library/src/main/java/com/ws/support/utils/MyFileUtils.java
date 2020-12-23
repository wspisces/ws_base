package com.ws.support.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.core.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * <p>Title:FileUtils.java</p>
 * <p>Description:文件管理类</p>
 */
public class MyFileUtils {
	public static String AppCode = "heniqi";

	private final static String Log = "log";
	private final static String File = "file";
	private final static String Img = "img";
	private final static String Video = "video";

	private static String mEnvironmentDirectory
		= Environment.getExternalStorageDirectory().getPath()+ "/" + AppCode;

	public static void initPath() {
		mEnvironmentDirectory = Environment.getExternalStorageDirectory().getPath()+ "/" + AppCode;
	}

	public static String getRootDirectory() {
		try{
			java.io.File file = new File("/");
			if(null != file){
				if(file.isDirectory()){
					java.io.File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						if(null != files[i]){
							Logger.i("path="+files[i].getPath());
						}
					}
				}else{
					Logger.i("path="+"/");
				}
			}
		}catch(Exception e){
			Logger.i("file is null");
		}
		return "";
	}

	/**
	 * 获取图片Base64字节
	 * @param imagePath
	 * @return
	 */
	public static String getImageBase64Str(String imagePath) {
		String str = "";
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			str = Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
			if (!bitmap.isRecycled())
				bitmap.recycle();
		} catch (Exception e) {}
		return str;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		return baos.toByteArray();
	}

	/**
	 * 通过文件路径获取名称
	 */
	public static String getFileNameByPath(String path) {
		try {
			if (!StringUtils.isEmpty(path)) {
				java.io.File file = new File(path);
				if (file.exists()) {
					return file.getName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

//	/**
//	 * 获取当前时间日期
//	 *
//	 * @param format
//	 *            自定义格式,例：yyyy-MM-dd hh:mm:ss
//	 * @return
//	 */
//	public static String getFormatTime(String format) {
//		Date date = new Date();
//		SimpleDateFormat df = new SimpleDateFormat(format);
//		String time = df.format(date);
//		return time;
//	}

	/**
	 * 获取日志文件地址
	 */
	public static String getLogDir() {
		return getDir(Log);
	}

	/**
	 * 获取文件地址
	 */
	public static String getFileDir() {
		return getDir(File);
	}

	/**
	 * 获取视频文件地址
	 */
	public static String getVideoDir() {
		return getDir(Video);
	}

	/**
	 * 获取图片文件地址
	 */
	public static String getImgDir() {
		return getDir(Img);
	}

	public static String getDir(String...dir) {
		StringBuilder sb = new StringBuilder(mEnvironmentDirectory);
		for (String str : dir) {
			sb.append("/" + str);
		}
		mkDir(sb.toString());
		return sb.toString();
	}

	/**
	 * 创建文件目录
	 * @param dir 如：/sdcard/log
	 */
	public static java.io.File mkDir(String dir) {
		if (isSDCARDMounted()) {
			java.io.File file = null;
			try {
				file = new File(dir);
				if (!file.exists()) {
					file.mkdirs();
				}
				return file;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 判断SDCARD是否有效
	 */
	public static boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(status))
			return true;
		return false;
	}

	/**
	 * 创建文件路径
     */
	public static void createDirectoryPath(String path) {
		java.io.File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 新建文件
     */
	public static java.io.File createNewDirectory(String path) {
		java.io.File file = new File(path);
		createDirectoryPath(file.getParent());
		if (file.exists()) {
			if (file.delete())
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static long getFileTime(String path) {

		java.io.File file = new File(path);

		if (file.exists()) {
			return new Date(file.lastModified()).getTime();
		} else {
			return 0;
		}
	}

	/**
	 * 获取应用数据目录
	 *
	 * @param context
	 * @return
	 */
	public static String getDiskFileDir(Context context) {
		String filePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			filePath = context.getExternalFilesDir(null).getPath();
		} else {
			filePath = context.getFilesDir().getPath();
		}
		return filePath;
	}

	/**
	 * 应用内部目录
	 *
	 * @param context
	 * @return
	 */
	public static String getInnerFileDir(Context context) {
		return context.getFilesDir().getPath();
	}


	/**
	 * 获取SDCard剩下的大小
	 *
	 * @return SDCard剩下的大小
	 * @since V1.0
	 */
	public static long getSDCardRemainSize() {
		StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long blockSize = statfs.getBlockSize();
		long availableBlocks = statfs.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 检查文件名后缀
	 * @param fileName 文件全名称
	 * @param fileSuffix 所检查后缀
	 * @return 该文件是否是包含所检查的后缀
	 */
	public static boolean checkSuffix(String fileName, String[] fileSuffix) {
		for (String suffix : fileSuffix) {
			if (fileName != null) {
				if (fileName.toLowerCase().endsWith(suffix)) {
					return true;
				}
			}
		}
		return false;
	}


	private static boolean isExternalStorageDocument(Uri uri)
	{
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}


	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
	{
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};
		try
		{
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst())
			{
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally
		{
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	public static String getFilePathByUri(Context context, Uri uri)
	{
		String path = null;
		String scheme = uri.getScheme();

		// 以 file:// 开头的
		if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()))
		{
			path = uri.getPath();
			return path;
		}
		// 以 content:// 开头的，比如 content://media/extenral/images/media/17766
		if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
		{
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			if (cursor != null)
			{
				if (cursor.moveToFirst())
				{
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (columnIndex > -1)
					{
						path = cursor.getString(columnIndex);
					}
				}
				cursor.close();
			}
			return path;
		}
		// 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
		if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			if (DocumentsContract.isDocumentUri(context, uri))
			{
				if (isExternalStorageDocument(uri))
				{
					// ExternalStorageProvider
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];
					if ("primary".equalsIgnoreCase(type))
					{
						path = Environment.getExternalStorageDirectory() + "/" + split[1];
						return path;
					}
				}
			} else if ("content".equalsIgnoreCase(uri.getScheme()))
			{
				return getDataColumn(context, uri, null, null);
			} else if ("file".equalsIgnoreCase(uri.getScheme()))
			{
				return uri.getPath();
			} else
			{
				String[] paths = uri.getPath().split("/0/");
				if (paths.length == 2)
				{
					return Environment.getExternalStorageDirectory() + "/" + paths[1];
				}

			}
		}
		return null;
	}

	// 在手机上打开文件的method
	public static void openFile(Context context, File file) {

		openFileByPackageNameFromCache(context, null, file);
	}

	// 在手机上打开文件的method
	public static void openFile(Context context, String packageName, File file) {

		openFileByPackageNameFromCache(context, packageName, file);
	}

	/**
	 * <P>打开手机文件 兼容7.0系统问题，context使用Application最佳，</P>
	 * <P>这边的缓存为从SharePreferUtil.getPackageName()获取的，请优先在Application里面配置下</P>
	 * @param context 上下文 context使用Application最佳
	 * @param file 打开的文件
	 */
	public static void openFileByPackageNameFromCache(Context context, String packageName, File file) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			if (StringUtils.isEmptyWithNull(packageName)) {
				packageName = context.getPackageName();
			}
			packageName = StringUtils.isNotEmptyWithNull(packageName) ? packageName : context.getApplicationContext().getPackageName();
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Logger.e(packageName);
			Logger.e(file.getAbsolutePath());
			Uri contentUri = FileProvider.getUriForFile(context, (packageName + ".fileprovider"), file);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
            /*调用getMIMEType()来取得MimeType
            设置intent的file与MimeType*/
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		}
		context.startActivity(intent);
	}
}



