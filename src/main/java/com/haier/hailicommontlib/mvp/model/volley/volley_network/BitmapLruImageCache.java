package com.haier.hailicommontlib.mvp.model.volley.volley_network;

import android.graphics.Bitmap;
import android.util.Log;

import com.haier.hailicommontlib.mvp.model.volley.toolbox.ImageLoader;

import androidx.collection.LruCache;


public class BitmapLruImageCache extends LruCache<String, Bitmap> implements
		ImageLoader.ImageCache {

	private final String TAG = this.getClass().getSimpleName();

	public BitmapLruImageCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		Log.v(TAG, "Retrieved item from Mem Cache");
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Log.v(TAG, "Added item to Mem Cache");
		put(url, bitmap);
	}
}
