package com.haier.hailicommontlib.mvp.model.utils.signutil;

import android.util.Base64;

import com.haier.hailicommontlib.mvp.model.AppConstant;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

//身份证校验工具类
public class DesUtil {

	private final static String DES = "DES";

	/**
	 * Description 根据键值进行加密
	 *
	 * @param
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String key) throws Exception {
		//获取指定区域的北京时间
		SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
		String ee = dff.format(new Date());
		String  data = AppConstant.KEY + dff.parse(ee).getTime();
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = Base64.encodeToString(bt, Base64.DEFAULT);
		return strs;
	}
	/**
	 * Description 根据键值进行加密
	 *
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
	public static String SSID() {
		try {
			return encrypt(AppConstant.CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}




}
