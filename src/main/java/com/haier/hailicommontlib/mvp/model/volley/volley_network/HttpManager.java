package com.haier.hailicommontlib.mvp.model.volley.volley_network;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.haier.hailicommontlib.mvp.model.volley.Request;
import com.haier.hailicommontlib.mvp.model.volley.RequestQueue;
import com.haier.hailicommontlib.mvp.model.volley.toolbox.ImageLoader;
import com.haier.hailicommontlib.mvp.model.volley.toolbox.ImageLoader.ImageCache;
import com.haier.hailicommontlib.mvp.model.volley.toolbox.Volley;

public class HttpManager {

	private static final int MAX_CACHE = 10;
	/**
	 * Log or request TAG
	 */
	public static final String TAG = "HttpManager";
	private String userAgent="saywash-android";

	/**
	 * Volley recommends in-memory L1 cache but both a disk and memory cache are
	 * provided. Volley includes a L2 disk cache out of the box but you can
	 * technically use a disk cache as an L1 cache provided you can live with
	 * potential i/o blocking.
	 * 
	 */
	public enum ImageCacheType {
		DISK, MEMORY
	}

	/**
	 * A singleton instance of the HttpManager class for easy access in other
	 * places
	 */
	private static HttpManager mInstance;

	/**
	 * Global request queue for Volley
	 */
	private RequestQueue mRequestQueue;

	/**
	 * Global image loader for Volley
	 */
	private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private ImageCache mImageCache;

	private HttpManager() {
	}

	public static HttpManager getInstance() {
		if (mInstance == null)
			mInstance = new HttpManager();

		return mInstance;
	}

	/**
	 * Initializer for the manager. Must be called prior to use.
	 * 
	 * @param context
	 *            application context
	 * @param uniqueName
	 *            name for the cache location
	 * @param compressFormat
	 *            file type compression format.
	 * @param quality
	 */
	public void init(Context context, String uniqueName,
                     CompressFormat compressFormat, int quality, ImageCacheType type) {
		if (mInstance == null)
			mInstance = new HttpManager();
		/*HttpStack stack;
		if(Build.VERSION.SDK_INT >= 9) {
			stack = new HurlStack();
		} else {
			stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
		}*/
		mRequestQueue = Volley.newRequestQueue(context);
		/*mRequestQueue = Volley.newRequestQueue(context,
				new HttpClientStack(AndroidHttpClient.newInstance(userAgent)));*/


		int memClass = ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int max = memClass / 8;
		if (max > MAX_CACHE)
			max = MAX_CACHE;
		int cacheSize = 1024 * 1024 * max;

		switch (type) {
		case DISK:
			mImageCache = (ImageCache) new DiskLruImageCache(context, uniqueName, cacheSize,
					compressFormat, quality);
			break;
		case MEMORY:
			mImageCache = (ImageCache) new BitmapLruImageCache(cacheSize);
		default:

			mImageCache = (ImageCache) new BitmapLruImageCache(cacheSize);
			break;
		}

		mImageLoader = new ImageLoader(mRequestQueue, mImageCache);
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	public ImageLoader getImageLoader() {
		if (mImageLoader != null) {
			return mImageLoader;
		} else {
			throw new IllegalStateException("ImageLoader not initialized");
		}
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 * 
	 * @param req
	 *
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	public void cancelPendingRequests() {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(TAG);
		}
	}
}
