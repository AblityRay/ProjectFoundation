package com.haier.hailicommontlib.mvp.model.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： jjf
 * @date： 2020/9/18
 * @describe：
 */
public class StringUtil {

    //10进制转16进制
    public static int IntToHexIn(int n) {
        char[] ch = new char[20];
        int nIndex = 0;
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15)
                ch[nIndex] = 'F';
            else if (k == 14)
                ch[nIndex] = 'E';
            else if (k == 13)
                ch[nIndex] = 'D';
            else if (k == 12)
                ch[nIndex] = 'C';
            else if (k == 11)
                ch[nIndex] = 'B';
            else if (k == 10)
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char) ('0' + k);
            nIndex++;
            if (m == 0)
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();
        String strHex = new String("0x");
        strHex += sb.toString();
        return Integer.valueOf(strHex, 16);
    }

    //10进制转16进制
    public static String IntToHex(int n) {
        char[] ch = new char[20];
        int nIndex = 0;
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15)
                ch[nIndex] = 'F';
            else if (k == 14)
                ch[nIndex] = 'E';
            else if (k == 13)
                ch[nIndex] = 'D';
            else if (k == 12)
                ch[nIndex] = 'C';
            else if (k == 11)
                ch[nIndex] = 'B';
            else if (k == 10)
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char) ('0' + k);
            nIndex++;
            if (m == 0)
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();
        String strHex = new String("0x");
        strHex += sb.toString();
        return strHex;
    }

    //10进制转16进制
    public static String IntToHexNo0x(int n) {
        char[] ch = new char[100];
        int nIndex = 0;
        while (true) {
            int m = n / 16;
            int k = n % 16;
            if (k == 15)
                ch[nIndex] = 'F';
            else if (k == 14)
                ch[nIndex] = 'E';
            else if (k == 13)
                ch[nIndex] = 'D';
            else if (k == 12)
                ch[nIndex] = 'C';
            else if (k == 11)
                ch[nIndex] = 'B';
            else if (k == 10)
                ch[nIndex] = 'A';
            else
                ch[nIndex] = (char) ('0' + k);
            nIndex++;
            if (m == 0)
                break;
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(ch, 0, nIndex);
        sb.reverse();

        return sb.toString();
    }

    public static int covert(byte... content) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < content.length; i++) {
            str.append(StringUtil.bytesToHexString(content[i]));
        }
        return covert(str.toString());
    }

    /**
     * 每一位byte代表一个int
     *
     * @param content
     * @return
     */
    public static int[] coverts(byte[] content) {
        int[] integers = new int[content.length];
        for (int i = 0; content.length > 0 && i < content.length; i++) {
            integers[i] = content[i] & 0xFF;
        }
        return integers;
    }

    /**
     * @param: [content]
     * @return: int
     * @description: 十六进制转十进制
     */
    public static int covert(String content) {
        if (content == null) {
            return -1;
        }

        content = content.toUpperCase();
        if (content.split("X").length == 2) {
            content = content.split("X")[1];
        }
        int number = -1;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            try {
                int x = map.get(str[i]);
                number += x * Math.pow(16, str.length - 1 - i);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("进制转换--参数异常:" + content);
            }

        }

        return number;
    }

    /**
     * @param: [content]
     * @return: int
     * @description: 十六进制转十进制
     */
    public static int covert(Byte b) {
        if (b == null) {
            return -1;
        }
        String content = bytesToHexString(b);

        content = content.toUpperCase();
        if (content.split("X").length == 2) {
            content = content.split("X")[1];
        }
        int number = -1;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            try {
                int x = map.get(str[i]);
                number += x * Math.pow(16, str.length - 1 - i);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("进制转换--参数异常:" + content);
            }

        }

        return number;
    }

    /**
     * 16进制bytes 拼接成16进制字符串
     *
     * @param bytes
     * @return
     */

    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

    /**
     * byte 转int
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte bytes) {
        String result = "";

        String hexString = Integer.toHexString(bytes & 0xFF);
        if (hexString.length() == 1) {
            hexString = '0' + hexString;
        }
        result += hexString.toUpperCase();

        return result;
    }

    /**
     * 获取16进制的数据用int类型表示
     *
     * @param bytes
     * @return
     */
    public static int bytesToHex10Int(byte bytes) {
        String result = "";

        String hexString = Integer.toHexString(bytes & 0xFF);
        if (hexString.length() == 1) {
            hexString = '0' + hexString;
        }
        result += hexString.toUpperCase();

        return Integer.parseInt(result, 10);
    }

    /**
     * 获取16进制的数据用int类型表示
     *
     * @param bytes
     * @return
     */
    public static int bytesToHex16Int(byte bytes) {
        String result = "";

        String hexString = Integer.toHexString(bytes & 0xFF);
        if (hexString.length() == 1) {
            hexString = '0' + hexString;
        }
        result += hexString.toUpperCase();

        return Integer.parseInt(result, 16);
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 16进制字符串转为16进制
     *
     * @param hex 16进制的字符串
     * @return
     */
    public static byte[] hexString2Bytes(String hex) {
        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }
    }

    //   int 转byte
    public static byte integersChangeBytes(int s) {
        byte bytes = 0x00;
        if (s >= 0) {
            bytes = Integer.valueOf(s).byteValue();
        }
        return bytes;
    }
}
