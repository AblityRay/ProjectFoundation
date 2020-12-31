package com.haier.hailicommontlib.mvp.model.pushreceiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;

import com.haier.hailicommontlib.ApplicationUtils;
import com.haier.hailicommontlib.R;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import androidx.core.app.NotificationCompat;

/**
 * @author： songqingwei
 * @date： 2020/8/19
 * @describe：推送通知栏布局自定义 目前自定义类型只有一个  builder_id==1
 */
public class MyUmengMessageHandler extends UmengMessageHandler {
    private static String TAG = "MyUmengMessageHandler";


    @Override
    public Notification getNotification(Context context, UMessage msg) {
        switch (msg.builder_id) {
            case 1:
                return createNotificationStyleOne(context, msg);

            default:
                //默认为0，若填写的builder_id并不存在，也使用默认。
                return super.getNotification(context, msg);
        }
    }


    public Notification createNotificationStyleOne(Context context, UMessage msg) {
        Resources resources = context.getResources();
        NotificationManager mNotificationManager = (NotificationManager) ApplicationUtils.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        //notification channel work
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && mNotificationManager != null) {
            builder = new NotificationCompat.Builder(ApplicationUtils.getAppContext(), "default");
             @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("default", resources.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        } else {
            builder = new NotificationCompat.Builder(ApplicationUtils.getAppContext());
        }
//        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mpush_notification);
//        mRemoteViews.setTextViewText(R.id.notification_title, msg.title);
//        mRemoteViews.setTextViewText(R.id.notification_text, msg.text);

        //创建大文本样式
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        String contentText = msg.text;

// !=null&&msg.text.length()>100?msg.text.substring(0,80)+"...":msg.text;
        bigTextStyle.setBigContentTitle(msg.title)
                .bigText(contentText);
        builder.setWhen(System.currentTimeMillis())
                //通知栏未展开时显示的小图标
//                .setSmallIcon(R.mipmap.iv_wash_call_manager_logo)
                .setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setStyle(bigTextStyle) //设置大文本样式---多行展示
                .setAutoCancel(true); //可以被清除
         return builder.build();
    }


}
