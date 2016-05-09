package com.jim.androidarchiteture.util;

//import android.app.Activity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.dabai360.dabaisite.R;
//import com.dabai360.dabaisite.config.AppConfig;
//import com.dabai360.dabaisite.config.AppSetting;
//import com.dabai360.dabaisite.util.viewutil.DialogUtil;

/**
 * Created by JimGong on 2015/11/4.
 */
public final class EvnUtil {

//    public static final void setUpTestEvnView(final Activity pActivity, View pTestEvnView) {
//        if (!AppConfig.DEBUG_MODE) {
//            return;
//        }
//        pTestEvnView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (!AppConfig.DEBUG_MODE) {
//                    return false;
//                }
//
//                String dataServer = AppSetting.getInstance().getServerIp();
//                final Activity activity = pActivity;
//                View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_testing_utility, null);
//                final TextView dataServerTitle = (TextView) dialogView.findViewById(R.id.test_serverDataTxt);
//                final Button setDevEnvBtn = (Button) dialogView.findViewById(R.id.test_setToDevEnvBtn);
//                final Button setTestEnvBtn = (Button) dialogView.findViewById(R.id.test_setToTestEnvBtn);
//                final Button setProdEnvBtn = (Button) dialogView.findViewById(R.id.test_setToProdEnvBtn);
//                final EditText setNewEnvTxt = (EditText) dialogView.findViewById(R.id.test_newServerTxt);
//                final Button setNewEnvBtn = (Button) dialogView.findViewById(R.id.test_setNewServerBtn);
//                dataServerTitle.setText(dataServer);
//                setDevEnvBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String dataServer1 = "dev2.dabai360.com/";
//                        AppSetting.getInstance().setServerIp(dataServer1);
//                        AppSetting.getInstance().cleanUserCache();
//                        dataServerTitle.setText(dataServer1);
//                        activity.finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
//                });
//                setTestEnvBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String dataServer1 = "functest.dabai360.com";
//                        AppSetting.getInstance().setServerIp(dataServer1);
//                        AppSetting.getInstance().cleanUserCache();
//
//                        dataServerTitle.setText(dataServer1);
//                        activity.finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
//                });
//                setProdEnvBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String dataServer1 = "im.dabai360.com";
//                        AppSetting.getInstance().setServerIp(dataServer1);
//                        AppSetting.getInstance().cleanUserCache();
//
//                        dataServerTitle.setText(dataServer1);
//                        activity.finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
//                });
//                setNewEnvBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String newHost = setNewEnvTxt.getText().toString();
//                        if (StringUtils.isBlank(newHost)) {
//                            return;
//                        }
//                        String dataServer1 = "" + newHost;
//                        AppSetting.getInstance().setServerIp(dataServer1);
//                        AppSetting.getInstance().cleanUserCache();
//
//                        dataServerTitle.setText(dataServer1);
//                        activity.finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    }
//                });
//                DialogUtil.showDialog(activity, dialogView);
//                return false;
//            }
//        });
//    }
}
