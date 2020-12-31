package com.haier.hailicommontlib.mvp.model.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.haier.hailicommontlib.mvp.model.utils.LogUtil;

import java.util.ArrayList;

/**
 * @author：焦俊峰
 * @date：2017/12/4
 * @describe： 数据库（保存未知支付结果订单）
 */

public class OrderPayDataBase extends SQLiteOpenHelper {

//    public static final String ZFB = "zfb";//支付宝
//    public static final String UNION = "union";//银联
//    public static final String YKT = "cmb";//一网通 招商银行
    public static final String DATA_BASE_NAME = "zhifuBaoOrderPayDataBase";


    public static final int NEW_DATA_BASE_VERSION = 6;//数据库版本 当前线上版本3 测试版本5
    public static final int OLD_DATA_BASE_VERSION = 5;//数据库版本 当前线上版本3 测试版本5

    //通用表格
    public static final String TABLE_NAME_PAY_NOT_RESULT = "OrderPayNotResultTable";
    //通用字段
    public static final String ID = "_id";//主键
    public static final String ORDER_ID = "orderId";//定单号
    public static final String THIS_ORDER_ID = "this_order_id";//第三方平台订单号码
    public static final String CREAT_TIME = "creatTime";//创建时间
    public static final String TYPE_PAY = "payType";//支付方式

    //通用表格
    private final String createTable_PAYNotResult = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PAY_NOT_RESULT + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ORDER_ID + " TEXT NOT NULL ," +
            CREAT_TIME + " TEXT NOT NULL ," +
            TYPE_PAY + " TEXT NOT NULL ," +
            THIS_ORDER_ID + " TEXT NOT NULL  " + ")";

//    public static final String TABLE_NAME_ZFB = "zhifuBaoOrderPayTable";
//
//    public static final String TABLE_NAME_UNION = "unionOrderPayTable";
//    //一网通
//    public static final String TABLE_NAME_CMB = "CMBOrderPayTable";
//    //支付宝
//    public static final String BATCH = "out_trade_no";//交易流水号  版本4更改为 out_trade_no THIS_ORDER_ID
//
//
//    //银联所需字段--版本2 添加银联
//    public static final String TXN_TIME = "txnTime";//交易流水产生时间
//    public static final String MER_ID = "merId";//版本4更改为THIS_ORDER_ID

    //一网通表格字段 (NEW_DATA_BASE_VERSION=4 添加)

//
//    //创建支付宝表
//    private final String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ZFB + "(" +
//            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            ORDER_ID + " TEXT NOT NULL ," +
//            THIS_ORDER_ID + " TEXT NOT NULL ," +
//            TYPE_PAY + " TEXT NOT NULL   " + ")";
//    //支付宝表格增加字段
//    private final String add_payType = "alter table  " + TABLE_NAME_ZFB + "  add COLUMN  "
//            + TYPE_PAY + "  TEXT ";
//    private final String ZFB_ADD_payType = "alter table  " + TABLE_NAME_ZFB + "  add COLUMN  "
//            + ORDER_ID + "  TEXT ";
//    //创建银联支付表格
//    private final String createTable_union = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_UNION + "(" +
//            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            ORDER_ID + " TEXT NOT NULL ," +
//            THIS_ORDER_ID + " TEXT NOT NULL ," +
//            TXN_TIME + " TEXT NOT NULL ," +
//            TYPE_PAY + " TEXT NOT NULL  " + ")";//CREAT_TIME
//    //创建一网通
//    private final String createTable_CMB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CMB + "(" +
//            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//            ORDER_ID + " TEXT NOT NULL ," +
//            CREAT_TIME + " TEXT NOT NULL ," +
//            TYPE_PAY + " TEXT NOT NULL ," +
//            THIS_ORDER_ID + " TEXT NOT NULL  " + ")";


//    //修改支付宝表格字段名称---为表格的统一做准备
//    private final String ZFB_TIBLE_RENAME_BATCH = "alter table " + TABLE_NAME_ZFB + " rename column " + BATCH + " to " + THIS_ORDER_ID;
//    //修改银联表格名称
//    private final String ZFB_TIBLE_RENAME_MER_ID = "alter table " + TABLE_NAME_UNION + " rename column " + MER_ID + " to " + THIS_ORDER_ID;

    public OrderPayDataBase(Context context) {
        super(context, DATA_BASE_NAME, null, NEW_DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//            db.execSQL(createTable);
//            db.execSQL(createTable_union);
            db.execSQL(createTable_PAYNotResult);
        } catch (SQLException e) {
            LogUtil.I("DataBase————", "onCreate.Error=" + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case NEW_DATA_BASE_VERSION:
                try {
                    LogUtil.I(TAG, "DataBase————onUpgrade ");
                    //创建一网通表格
                    db.execSQL(createTable_PAYNotResult);
                } catch (SQLException e) {
                    LogUtil.I(TAG, "DataBase————onUpgrade Error" + e.toString());
                }
                break;

//            case 3://第二次升级 ，版本号3（暂未执行，用做范例）
//                if (oldVersion == 2) {//如果目前旧版本不是上一个版本，则把上一个版本的改动也执行
//                    break;
//                }
//            case 2://第一次升级 版本号改为2，添加银联订单表格
//                try {
//                    LogUtil.I(TAG, "DataBase————onUpgrade ");
//                    //创建银联支付记录表格
//                    db.execSQL(createTable_union);
//                    //支付宝表格添加字段
//                    db.execSQL(add_payType);
//                } catch (SQLException e) {
//                    LogUtil.I(TAG, "DataBase————onUpgrade " + TABLE_NAME_UNION + "Error" + e.toString());
//                }
//                break;
//            default:
        }
        LogUtil.I(TAG, "DataBase————onUpgrade oldVersion= " + oldVersion + " newVersion=" + newVersion);
    }

    //删除数据库 根据订单号
    public int delete(String orderId, String tableName) {
        orderId = orderId.trim();
        String whereClause = null;
        String whereArgs[] = null;
        SQLiteDatabase writableDatabase = null;
        whereClause = ORDER_ID + " = ? ";
        whereArgs = new String[]{String.valueOf(orderId)};
        writableDatabase = this.getWritableDatabase();
        int delete = writableDatabase.delete(tableName, whereClause, whereArgs);
        writableDatabase.close();
        LogUtil.I(TAG, tableName + "delete=" + delete);
        this.close();
        return delete;
    }

    /**
     * 添加   0 未添加  -1  已经存在  大于0  添加成功
     *
     * @param contentValues 参数集合
     * @return
     */
    private String TAG = "doStartPayPlugin";

    /**
     * 如果已存在这个参数  并且支付方式为当前使用的支付方式，则返回-1
     *
     * @param contentValues
     * @return
     */
    public long insert(ContentValues contentValues) {
        //通过ding单号查询是否存在此订单
        String code = null;
        String payType=null;
        ArrayList<SQLDataBaseBean> sqlDataBaseBeans = null;
        //从对应的表格中 ，获取Id（此ID为 订单code）
        code = (String) contentValues.get(OrderPayDataBase.ORDER_ID);
        payType = (String) contentValues.get(OrderPayDataBase.TYPE_PAY);
        sqlDataBaseBeans = queryValue(code,payType);
        for (int i = 0; i < sqlDataBaseBeans.size(); i++) {
            //查看对应的表格中是否存在这个数据以及支付方式是否相同
            SQLDataBaseBean sqlDataBaseBean = sqlDataBaseBeans.get(i);
            if (code.equals(sqlDataBaseBean.getOrderId())&&payType.equals(sqlDataBaseBean.getPayType())) {
                LogUtil.I(TAG, "orderCode=" + code + "---数据库" + sqlDataBaseBean.getOrderId());
                return -1;
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        //插入数据
        long insert = db.insert(OrderPayDataBase.TABLE_NAME_PAY_NOT_RESULT, null, contentValues);
        //关闭数据库
        db.close();
        this.close();
        LogUtil.I(TAG, OrderPayDataBase.TABLE_NAME_PAY_NOT_RESULT + "insert=" + insert);
        return insert;
    }

    /**
     * 查询 根据交易号或者订单号查询
     *
     * @param orderId 交易号
     * @return
     */
    public ArrayList<SQLDataBaseBean> queryValue(String orderId, String payType) {
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SQLDataBaseBean> database = new ArrayList<>();

        orderId = orderId.trim();
        String[] columns = null;
        String selection = null;
        if (db.isOpen()) {
            columns = new String[]{ORDER_ID, CREAT_TIME, THIS_ORDER_ID, TYPE_PAY}; //需要查询的列
            selection = ORDER_ID + " = ? and "+TYPE_PAY+" = ? "; // 选择条件，给null查询所有
        } else {
            db.close();
            return database;
        }
        String[] selectionArgs = {orderId};//选择条件参数,会把选择条件中的？替换成这个数组中的值
        String groupBy = null; // 分组语句 group by name  注意些的时候需要要group by 去掉
        String having = null;  // 过滤语句
        String orderBy = null; //排序

        cursor = db.query(OrderPayDataBase.TABLE_NAME_PAY_NOT_RESULT,
                columns, selection, selectionArgs, groupBy, having, orderBy);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                database.add(new SQLDataBaseBean(
                        cursor.getString(0) + "",
                        cursor.getString(1) + "",
                        cursor.getString(2) + "",
                        cursor.getString(3) + ""));
                LogUtil.I("PAY_TEST_RESULT", "cursor.getString(0)=" + cursor.getString(0) +
                        "\n  cursor.getString(1)=" + cursor.getString(1) + "\n  cursor.getString(2)=" + cursor.getString(2)
                        + "\n  cursor.getString(3)=" + cursor.getString(3) +
                        "\n  cursor.getString(4)=" + cursor.getString(4));
            } while (cursor.moveToNext());

        }


        if (cursor != null)
            cursor.close();
        if (db != null)
            db.close();
        return database;

    }

    /**
     * 更改
     *
     * @param contentValues
     * @return
     */
    public int modify(ContentValues contentValues) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;
        selection = ORDER_ID + " = ?"; // 选择条件，给null查询所有
        selectionArgs = new String[]{(String) contentValues.get(OrderPayDataBase.ORDER_ID)};//选择条件参数,会把选择条件中的？替换成这个数组中的值
        int update = db.update(TABLE_NAME_PAY_NOT_RESULT, contentValues, selection, selectionArgs);
        LogUtil.I(TAG, "updata=" + update);
        db.close();
        this.close();
        return update;
    }
}
