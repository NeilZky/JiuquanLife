package com.jiuquanlife.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextPaint;
import android.text.format.Time;
import android.util.Base64;

import com.jiuquanlife.entity.Photo;

public class PhotoManager {

	private static final String SDCARD = Environment
			.getExternalStorageDirectory().getPath();
	public static final String TEMP_PHOTO_PATH = SDCARD + "/ijq/temp/";
	public static final String TEMP_PHOTO_BITMAP = SDCARD
			+ "/ijq/temp/temp.jpg";
	public static final String CURRENT_PHOTO_PATH = SDCARD
			+ "/ijq/current/";
	public static final String UPLOAD_PHOTO_PATH = SDCARD + "/ijq/upload/";
	private static final double EMPTY_SIZE = 0D;
	private static final int COMPRESS_30 = 30;
	private static final int COMPRESS_MIN = 20;
	private static final int COMPRESS_MAX = 80;
	private static final int COMPRESS_COEFFICIENT = 40;
	private static final int MAX_BITMAP_LENGTH = 1048576; // 1024*1024B �?���?M
	private static final int THUMBNAIL_PHOTO_WIDTH = 100;
	private static final int THUMBNAIL_PHOTO_HEIGHT = 100;
	private static final int THUMBNAIL_OFFLINE_PHOTO_WIDTH = 300;
	private static final int THUMBNAIL_OFFLINE_PHOTO_HEIGHT = 300;

	private static PhotoManager instance;
	private static Object lock = new Object();

	private PhotoManager() {

	}

	public static PhotoManager getInstance() {

		if (null == instance) {
			synchronized (lock) {
				if (null == instance) {
					instance = new PhotoManager();
					instance.createFolder(TEMP_PHOTO_PATH);
					instance.createFolder(UPLOAD_PHOTO_PATH);
				}
			}
		}
		return instance;
	}

	public void createTempFolder() {

		createFolder(TEMP_PHOTO_PATH);
	}

	public void createFolder(String folder) {

		File tempFloder = new File(folder);
		if (!tempFloder.exists()) {
			tempFloder.mkdirs();
		}
	}

	public void savePhoto(String address) {

		copyToCurrentFile(TEMP_PHOTO_BITMAP, address);
	}


	public ArrayList<Photo> getThumbnailPhotos(String photoFolderPath) {

		ArrayList<String> photoPathes = getFilePathes(photoFolderPath);
		ArrayList<Photo> result = new ArrayList<Photo>();
		if (photoPathes != null) {
			for (String tempPhotoPath : photoPathes) {
				Bitmap bitmap = decodeSampledBitmapFromResource(tempPhotoPath,
						THUMBNAIL_PHOTO_WIDTH, THUMBNAIL_PHOTO_HEIGHT);
				if (null != bitmap) {
					Photo photo = new Photo();
					photo.setBitmap(bitmap);
					photo.setFilePath(tempPhotoPath);
					result.add(photo);
				}
			}
		}
		return result;
	}
	
	public Photo getThumbnailPhoto(String path) {
		
		Bitmap bitmap = decodeSampledBitmapFromResource(path,
				THUMBNAIL_PHOTO_WIDTH, THUMBNAIL_PHOTO_HEIGHT);
		if (null != bitmap) {
			Photo photo = new Photo();
			photo.setBitmap(bitmap);
			photo.setFilePath(path);
			return photo;
		}
		return null;
	}
	
	
	public void moveTempFiles() {

		Bitmap bitmap = BitmapFactory.decodeFile(TEMP_PHOTO_BITMAP);
		Time t = new Time();
		t.setToNow();
		String name = t.format("%Y-%m-%d %H_%M_%S") + ".jpg";
		saveToSdcard(bitmap, name);
		deletePhotos(TEMP_PHOTO_PATH);
	}

	public void movePhotos(String src, String dist) {

		File distFile = new File(dist);
		if (!distFile.exists()) {
			distFile.mkdirs();
		}
		File currentPhotoFolder = new File(src);
		File[] currentFiles = currentPhotoFolder.listFiles();
		if (currentFiles != null) {
			for (File tempFile : currentFiles) {
				File newPath = new File(dist + tempFile.getName());
				tempFile.renameTo(newPath);
			}
		}
	}

	public void renamePhoto(int gpsDataID, String folder) {

		File file = new File(folder);
		if (file.exists()) {
			File[] photos = file.listFiles();
			if (photos != null) {
				for (File photo : photos) {
					String newPathName = photo.getParent() + "/" + gpsDataID
							+ "." + photo.getName();
					photo.renameTo(new File(newPathName));
				}
			}
		}
	}

	public void copyToCurrentFile(String path, String address) {

		copyToCurrentFile(path, address, TEMP_PHOTO_PATH, CURRENT_PHOTO_PATH);
	}

	public void copyToCurrentFile(String path, String address,
			String tempFolder, String savePath) {

		if (path != null) {
			File srcFile = new File(path);

			if (srcFile.exists()) {
				int digree = 0;
				try {
					ExifInterface exif = new ExifInterface(TEMP_PHOTO_BITMAP);
					if (exif != null) {
						int ori = exif.getAttributeInt(
								ExifInterface.TAG_ORIENTATION,
								ExifInterface.ORIENTATION_UNDEFINED);
						switch (ori) {
						case ExifInterface.ORIENTATION_ROTATE_90:
							digree = 90;
							break;
						case ExifInterface.ORIENTATION_ROTATE_180:
							digree = 180;
							break;
						case ExifInterface.ORIENTATION_ROTATE_270:
							digree = 270;
							break;
						default:
							digree = 0;
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromSDCard(path,
						800, 800);
				if (digree != 0) {
					Matrix m = new Matrix();
					m.postRotate(digree);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
							bitmap.getHeight(), m, true);
				}
				Time t = new Time();
				t.setToNow();
				String timeText = t.format("%Y-%m-%d %H:%M:%S");
				String name = t.format("%Y-%m-%d %H_%M_%S")
						+ "." + srcFile.getName();
				Bitmap waterMarkedBitmap = addWaterMark(bitmap,
					  timeText + "  "
								+ address);
				File file = new File(path);
				long fileLength = file.length();
				if (fileLength > 1024 * 1024) {
					saveToSdcard(waterMarkedBitmap, name, savePath,
							COMPRESS_MIN);
				} else {
					saveToSdcard(waterMarkedBitmap, name, savePath, COMPRESS_30);
				}
				deletePhotos(tempFolder);
				waterMarkedBitmap.recycle();
				bitmap.recycle();
			}
		}
	}
	
	public String compressPicture(String path, 
			 String distPath) {

		if (path != null) {
			File srcFile = new File(path);

			if (srcFile.exists()) {
				int digree = 0;
				try {
					ExifInterface exif = new ExifInterface(TEMP_PHOTO_BITMAP);
					if (exif != null) {
						int ori = exif.getAttributeInt(
								ExifInterface.TAG_ORIENTATION,
								ExifInterface.ORIENTATION_UNDEFINED);
						switch (ori) {
						case ExifInterface.ORIENTATION_ROTATE_90:
							digree = 90;
							break;
						case ExifInterface.ORIENTATION_ROTATE_180:
							digree = 180;
							break;
						case ExifInterface.ORIENTATION_ROTATE_270:
							digree = 270;
							break;
						default:
							digree = 0;
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromSDCard(path,
						800, 800);
				if (digree != 0) {
					Matrix m = new Matrix();
					m.postRotate(digree);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
							bitmap.getHeight(), m, true);
				}
				Time t = new Time();
				t.setToNow();
				String name = t.format("%Y-%m-%d %H_%M_%S")
						+ "." + srcFile.getName();
				File file = new File(path);
				long fileLength = file.length();
				String res =  null;
				if (fileLength > 1024 * 1024) {
					res = saveToSdcard(bitmap, name, distPath,
							COMPRESS_MIN);
				} else {
					res= saveToSdcard(bitmap, name, distPath, COMPRESS_30);
				}
				deletePhotos(path);
				bitmap.recycle();
				return res;
			}
		}
		return null;
	}
	

	public void deleteCurrentPhotos() {

		deletePhotos(CURRENT_PHOTO_PATH);
	}

	public void deletePhotos(String folder) {

		File currentPhotoFolder = new File(folder);
		File[] photos = currentPhotoFolder.listFiles();
		if (photos != null) {
			for (File photoFile : photos) {
				photoFile.delete();
			}
		}
	}



	public boolean isUploadFolderEmpty() {
		File currentFolder = new File(UPLOAD_PHOTO_PATH);
		if (!currentFolder.exists()) {
			return false;
		}
		return currentFolder.list().length <= 0;
	}

	public double getPhotoSize() {
		File currentFolder = new File(CURRENT_PHOTO_PATH);
		File[] files = currentFolder.listFiles();
		if (files.length == 0) {
			return EMPTY_SIZE;
		}
		double tempsize = 0d;
		for (File temp : files) {
			tempsize += temp.length();
		}
		double res = tempsize / MAX_BITMAP_LENGTH;
		return DecimalUtils.keepSizeTwo(res);
	}


	public Bitmap getThumOfflineBitmap(String path) {

		return decodeSampledBitmapFromResource(path,
				THUMBNAIL_OFFLINE_PHOTO_WIDTH, THUMBNAIL_OFFLINE_PHOTO_HEIGHT);
	}

	private ArrayList<String> getFilePathes(String photoFolderPath) {

		File photoFolder = new File(photoFolderPath);
		File[] photoPathFiles = photoFolder.listFiles();
		ArrayList<String> photoPathes = new ArrayList<String>();
		if (null != photoPathFiles) {
			for (File tempPhotoFile : photoPathFiles) {
				photoPathes.add(tempPhotoFile.getPath());
			}
		}
		return photoPathes;
	}

	public Bitmap getLargePhoto(String photoPath) {

		return BitmapFactory.decodeFile(photoPath);
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
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
		// 将inSampleSize转换为最接近�?的幂，效率更�?
		int result = (int) (Math.log((double) inSampleSize)
				/ Math.log((double) 2) + 1);
		return (int) Math.pow(2, result);
	}

	public String convertFileToBase64String(String path) throws IOException {

		File file = new File(path);
		return convertFileToBase64String(file);
	}

	/**
	 * TODO 封装exception , finally �?close
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String convertFileToBase64String(File file) throws IOException {

		int length = (int) file.length();
		byte[] fileBuffer = new byte[length];
		FileInputStream fis = new FileInputStream(file);
		fis.read(fileBuffer);
		fis.close();
		String result = Base64.encodeToString(fileBuffer, Base64.DEFAULT);
		return result;
	}

	private Bitmap decodeSampledBitmapFromResource(String photoPath,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(photoPath, options);
	}

	private Bitmap addWaterMark(Bitmap src, String text) {

		int w = src.getWidth();
		int h = src.getHeight();
		Time t = new Time();
		t.setToNow();
		Bitmap waterMarkedBitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
		Canvas canvas = new Canvas(waterMarkedBitmap);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		Paint p = new Paint();
		// TODO 字体
		// String familyName = "宋体";
		// Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setColor(Color.WHITE);
		// p.setTypeface(font);
		int textSize = width < height ? width / 20 : height / 20;
		p.setTextSize(textSize);
		canvas.drawBitmap(src, 0, 0, p);
		src.recycle();
		src = null;
		int margin = 2 * textSize;
		TextPaint tp = new TextPaint();
		float[] widths = new float[text.length()];
		tp.getTextWidths(text, widths);

		int textWidth = width - 2 * margin;
		ArrayList<String> texts = new ArrayList<String>();
		int length = text.length();
		float currentLineWidth = 0F;
		int start = 0;
		for (int i = 0; i < length; i++) {
			currentLineWidth = p.measureText((String) text
					.subSequence(start, i));
			if (currentLineWidth > textWidth) {
				texts.add(text.substring(start, i - 1));
				start = i - 1;
				i--;
			} else {
				if (i == length - 1) {
					texts.add(text.substring(start));
				}
			}
		}

		float offsetHeight = height - textSize * 2 * texts.size() - margin;
		Paint bgPaint = new Paint();
		bgPaint.setColor(Color.BLACK);
		bgPaint.setAlpha(100);
		Rect r = new Rect();
		r.left = margin / 2;
		r.right = textWidth + margin + margin / 2;
		r.top = (int) (offsetHeight - 2 / margin);
		r.bottom = (int) (offsetHeight + (texts.size() + 1) * textSize + margin / 2);
		canvas.drawRect(r, bgPaint);
		if (texts != null) {
			for (int j = 0; j < texts.size(); j++) {
				canvas.drawText(texts.get(j), margin, offsetHeight + (j + 2)
						* textSize, p);
			}
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return waterMarkedBitmap;
	}

	private Context context;

	public void setContext(Context context) {

		this.context = context;
	}

	private void saveToSdcard(Bitmap bitmap, String name) {

		saveToSdcard(bitmap, name, CURRENT_PHOTO_PATH);
	}

	private void saveToSdcard(Bitmap bitmap, String name, String folder) {

		saveToSdcard(bitmap, name, folder, COMPRESS_MIN);
	}

	private String saveToSdcard(Bitmap bitmap, String name, String folder,
			int rate) {

		File path = new File(folder);
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(folder, name);
		FileOutputStream bitmapWtriter = null;
		try {
			bitmapWtriter = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, rate, bitmapWtriter);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {

			if (bitmapWtriter != null) {
				try {
					bitmapWtriter.close();
				} catch (IOException e) {
				}
			}

		}
		return file.getPath();
	}

	private int computeCompressRate(Bitmap bitmap) {

		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		int length = height <= width ? height : width;
		int rate = length / COMPRESS_COEFFICIENT;
		if (rate < COMPRESS_MIN) {
			rate = COMPRESS_MIN;
		}
		if (rate > COMPRESS_MAX) {
			rate = COMPRESS_MAX;
		}
		return rate;
	}

}
