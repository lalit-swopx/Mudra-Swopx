package com.mudra.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.mudra.R;
import com.mudra.Splash;


public class LocalNotification {
    public void localNotification(String message, String notification_type, String id, String roylty, String sub_industry, Context context) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.login_logo)
                .setContentTitle("Mudra")
                .setContentText(id)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(id));
        Constant.Log("Notification Type", "Notification Type=====1" + notification_type + "Hello");
//        fragment_replace(notification_type,id,context);
        Intent notificationIntent = new Intent(context, Splash.class);
        notificationIntent.putExtra("type", message);
        notificationIntent.putExtra("project_id", notification_type);
        notificationIntent.putExtra("roylty", roylty);
        notificationIntent.putExtra("sub_industry", sub_industry);
       /* notificationIntent.putExtra("id",id);
        notificationIntent.putExtra("message",message);*/
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.putExtra("notification_type",notification_type);
//        notificationIntent.putExtra("id",id);
//        notificationIntent.putExtra("message",message);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(notificationIntent);
    }


    public void localNotification(String message, Context context) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.login_logo)
                .setContentTitle("Swopx")
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        Constant.Log("Notification Type", "Notification Type=====1" + message + "Hello");
//        fragment_replace(notification_type,id,context);
        Intent notificationIntent = new Intent(context, Splash.class);
        notificationIntent.putExtra("type", message);
       /* notificationIntent.putExtra("id",id);
        notificationIntent.putExtra("message",message);*/
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.putExtra("notification_type",notification_type);
//        notificationIntent.putExtra("id",id);
//        notificationIntent.putExtra("message",message);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(notificationIntent);
    }

   /* private void fragment_replace(String notification_type, String id, Context context) {
        if (notification_type.equalsIgnoreCase("10")) {
            Bundle b = new Bundle();
            b.putString("skill_id", id);
            RolesDetails roleDetails = new RolesDetails();
            roleDetails.setArguments(b);
            ((MainActivity) context).replaceFragment(roleDetails);
        }

        if (notification_type.equalsIgnoreCase("11")
                ||notification_type.equalsIgnoreCase("18")) {
            Myprofile roleDetails = new Myprofile();
            ((MainActivity) context).replaceFragment(roleDetails);
        }
        if (notification_type.equalsIgnoreCase("12")||notification_type.equalsIgnoreCase("13")) {
            Bundle b=new Bundle();
            b.putString("skill_id",id);
            Article_Details skillsDetails=new Article_Details();
            skillsDetails.setArguments(b);
            ((MainActivity) context).replaceFragment(skillsDetails);
        }
        if (notification_type.equalsIgnoreCase("14")
                ||notification_type.equalsIgnoreCase("15")
                ||notification_type.equalsIgnoreCase("16")
                ||notification_type.equalsIgnoreCase("20")
                ||notification_type.equalsIgnoreCase("21")) {
            Bundle b=new Bundle();
            b.putString("type","individual");
            b.putString("course_id",id);
            b.putString("notification","notification");
            Selected_course skillsDetails=new Selected_course();
            skillsDetails.setArguments(b);
            ((MainActivity) context).replaceFragment(skillsDetails);
        }
        if (notification_type.equalsIgnoreCase("17")
                ||notification_type.equalsIgnoreCase("23")) {
//            Bundle b=new Bundle();
//            b.putString("type","individual");
//            b.putString("course_id",id);
//            b.putString("title",skill_list.get(adapterPosition).getName());
//            Bundle_Individual_Detail_Fragment fragment=new Bundle_Individual_Detail_Fragment();
//            fragment.setArguments(b);
//            ((MainActivity)context).replaceFragment(fragment);
        }
        if (notification_type.equalsIgnoreCase("22")) {
            ((MainActivity)context).replaceFragment(new Employability_Main_Class());
        }
    }
*/

//    public void localNotification(String msg, String strNotifyType, String context) {
//        Log.d(">>>localNotification", strNotifyType);
//        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Pharmacy notification")
//                .setContentText(msg)
//                .setDefaults(NotificationCompat.DEFAULT_SOUND)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
//
////        if (strNotifyType.equals("1008")) {
////            Intent notificationIntent = new Intent(context, BillsActivity.class);
////            notificationIntent.putExtra("notification_type", strNotifyType);
////            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////
////            builder.setContentIntent(contentIntent);
////        } else {
////
////            Intent notificationIntent = new Intent(context, DashBoardActivity.class);
////            notificationIntent.putExtra("notification_type", strNotifyType);
////            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////
////            builder.setContentIntent(contentIntent);
////        }
//
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }


}
