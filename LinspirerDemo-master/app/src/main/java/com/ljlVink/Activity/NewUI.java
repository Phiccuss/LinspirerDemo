package com.ljlVink.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.king.zxing.CameraScan;
import com.ljlVink.utils.Toast;
import com.ljlVink.core.core.Postutil;
import com.ljlVink.utils.DataUtils;
import com.huosoft.wisdomclass.linspirerdemo.BuildConfig;
import com.ljlVink.utils.ContentUriUtil;
import com.huosoft.wisdomclass.linspirerdemo.R;
import com.ljlVink.core.core.HackMdm;
import com.ljlVink.utils.appsecurity.RSA;

import com.ljlVink.linspirerfake.uploadHelper;
import com.ljlVink.utils.Sysutils;
import com.ljlVink.services.vpnService;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import activitylauncher.MainActivity;

public class NewUI extends AppCompatActivity {
    private final int Lenovo_Mia = 3;
    private final int Lenovo_Csdk = 2;
    private final int T11=4;
    private String currMDM = "DevicePolicyManager";
    private HackMdm hackMdm;
    private BaseAdapter mAdapter = null;
    private ArrayList<String> superlist = new ArrayList<>();
    private ArrayList<icon> mData = null;
    private int MMDM;
    private GridView grid_photo;
    private Postutil postutil;
    private LinearLayout right;
    private TitleBar titleBar;
    final String pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy7Zi/oJPPPsomYWcP2lB\n" +
            "bdo1ovpqvr2tvCrxUKjWqgUSsYnrCPNkj5MOAjoyBB4wTB5SAOwLXFsB0Cu8YE8a\n" +
            "4U38XdPF4wH3Tst7hlU1x9KyOg/bgYKkT8NTQ7lgy8WsmlcKiI/u2Aea8+XpCTBw\n" +
            "UdIBkuF0apT+qOzOBGPuJtIhR20SIGLdW7R9ZSjuXO7CgQp4sna6xfX0ae0blqwn\n" +
            "ASbXRLvFofTx39sDgZTibRwYp/1UEuTfBKjK3BJ0R4S2OopqD3gVHFba0YPP+Q5q\n" +
            "bOX+/KU+ASo/lM9qFSKM6NpgLjuUR0VaAcZFcYl59v+jb58/PcqYLr1cY7Zj08xu\n" +
            "OwIDAQAB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
        }
        ImmersionBar.with(this).transparentStatusBar().init();
        setContentView(R.layout.activity_new_ui);
        titleBar=findViewById(R.id.titlebar);
        super.onCreate(savedInstanceState);
        hackMdm = new HackMdm(this);
        postutil = new Postutil(this);
        postutil.CloudAuthorize();
        hackMdm.initHack(0);
        //初始化view
        new uploadHelper(this,true).uplpadfakeapps();
        if(!Sysutils.isTabletDevice(this)){
            right=findViewById(R.id.right);
            right.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        MMDM = hackMdm.getMMDM();
        grid_photo = (GridView) findViewById(R.id.grid_photo);
        mData = new ArrayList<icon>();
        mData.add(new icon(R.drawable.backtodesktop, "返回桌面"));
        mData.add(new icon(R.drawable.installapps, "应用安装"));
        mData.add(new icon(R.drawable.appmanage,"应用管理"));
        mData.add(new icon(R.drawable.action_hide, "回领创配置隐藏"));
        mData.add(new icon(R.drawable.recycle, "杀进程(长按配置)"));
        mData.add(new icon(R.drawable.floatview, "打开悬浮窗"));
        mData.add(new icon(R.drawable.huawei, "华为专区"));
        mData.add(new icon(R.drawable.lenovo, "联想专区"));
        mData.add(new icon(R.drawable.device_settings, "设备设置"));
        mData.add(new icon(R.drawable.app_settings, "程序设置"));
        mData.add(new icon(R.drawable.activitylauncher_ic_launcher_foreground, "活动启动器"));
        mData.add(new icon(R.drawable.help, "帮助"));
        mData.add(new icon(R.drawable.ic_baseline_calculate_24, "计算器"));
        mData.add(new icon(R.drawable.swordplan, "执剑计划"));
        mData.add(new icon(R.drawable.linspirer, "密码计算"));
        mData.add(new icon(R.drawable.linspirer, "应用上传(长按配置)"));
        mData.add(new icon(R.drawable.linspirer, "三方教育桌面app隐藏"));
        mData.add(new icon(R.drawable.tensafe,"T11专区"));
        mAdapter = new MyAdapter<icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };
        grid_photo.setAdapter(mAdapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (Sysutils.getLauncherPackageName(getApplicationContext()) != null) {
                            try {
                                Intent home=new Intent(Intent.ACTION_MAIN);
                                home.addCategory(Intent.CATEGORY_HOME);
                                startActivity(home);                                //startActivity(intent);
                            } catch (Exception e) {
                                startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
                            }
                        } else {
                            startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
                        }
                        break;
                    case 1:
                        final String[] items = new String[]{"通过apk安装(自动选择)", "通过apk安装(DocumentUI)", "静默安装(Filepicker)", "静默安装(仅限EMUI10静默)", "写app白名单"};
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NewUI.this);
                        builder.setIcon(R.drawable.installapps);
                        builder.setTitle("请选择方式：");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                which++;
                                try {
                                    if (which == 1) {
                                        if (MMDM != Lenovo_Mia) {
                                            Intent FS = new Intent(Intent.ACTION_GET_CONTENT);
                                            FS.setType("application/vnd.android.package-archive");
                                            startActivityForResult(FS, 1);
                                        } else {
                                            new MaterialFilePicker().withActivity(NewUI.this).withCloseMenu(true).withRootPath("/storage").withHiddenFiles(true).withFilter(Pattern.compile(".*\\.(apk)$")).withFilterDirectories(false).withTitle("new API_选择文件").withRequestCode(1000).start();
                                        }
                                    } else if (which == 2) {
                                        Intent FS = new Intent(Intent.ACTION_GET_CONTENT);
                                        FS.setType("application/vnd.android.package-archive");
                                        startActivityForResult(FS, 1);
                                    } else if (which == 3) {
                                        new MaterialFilePicker().withActivity(NewUI.this).withCloseMenu(true).withRootPath("/storage").withHiddenFiles(true).withFilter(Pattern.compile(".*\\.(apk)$")).withFilterDirectories(false).withTitle("new API_选择文件").withRequestCode(1000).start();
                                    } else if (which == 4) {
                                        if (hackMdm.isEMUI10Device()) {
                                            Intent FS = new Intent(Intent.ACTION_GET_CONTENT);
                                            FS.setType("application/vnd.android.package-archive");
                                            startActivityForResult(FS, 2);
                                        }
                                    } else if (which == 5) {
                                        final EditText et = new EditText(NewUI.this);
                                        new MaterialAlertDialogBuilder(NewUI.this).setTitle("请输入包名")
                                                .setIcon(R.drawable.installapps)
                                                .setView(et)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        hackMdm.appwhitelist_add(et.getText().toString());
                                                    }
                                                }).setNegativeButton("取消", null).show();
                                    }

                                } catch (Exception e) {
                                    Toast.ShowErr(NewUI.this,"该设置对你无效");
                                }
                            }
                        });
                        builder.create().show();
                        break;
                    case 2:
                        startActivity(new Intent(NewUI.this,AppManageActivity.class));
                        break;
                    case 3:
                        superlist.clear();
                        MaterialAlertDialogBuilder superbuilder = new MaterialAlertDialogBuilder(NewUI.this);
                        superbuilder.setTitle("选择超级名单");
                        PackageManager pm3 = getPackageManager();
                        List<PackageInfo> packages3 = pm3.getInstalledPackages(0);
                        ArrayList<String> apps_super = new ArrayList<>();
                        ArrayList<String> appnames_super = new ArrayList<>();
                        for (PackageInfo packageInfo : packages3) {
                            // 判断系统/非系统应用
                            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                if ("com.android.launcher3".equals(packageInfo.packageName) || "com.ndwill.swd.appstore".equals(packageInfo.packageName) || BuildConfig.APPLICATION_ID.equals(packageInfo.packageName)) {
                                    continue;
                                }
                                apps_super.add(packageInfo.packageName);
                                appnames_super.add(packageInfo.applicationInfo.loadLabel(pm3).toString());
                            }
                        }
                        boolean[] mylist = new boolean[10000 + 50];
                        Set<String> st = DataUtils.readStringList(getApplicationContext(), "superapp");
                        ArrayList<String> lst = new ArrayList(st);
                        int sz = st.size();
                        int super_sz = apps_super.size();
                        for (int i = 0; i < super_sz; i++)
                            for (int j = 0; j < sz; j++) {
                                if (apps_super.get(i).equals(lst.get(j))) {
                                    mylist[i] = true;
                                    superlist.add(lst.get(j));
                                }
                            }
                        superbuilder.setMultiChoiceItems(appnames_super.toArray(new String[0]), mylist, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean ischeck) {
                                if (ischeck) {
                                    superlist.add(apps_super.get(which));
                                } else {
                                    superlist.remove(apps_super.get(which));
                                }

                            }
                        });
                        superbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hackMdm.savesuperapps(superlist);
                            }
                        });
                        superbuilder.show();

                        break;
                    case 4:
                        hackMdm.killApplicationProcess(DataUtils.ReadStringArraylist(getApplicationContext(), "notkillapp"));
                        break;
                    case 5:
                        if (EasyFloat.isShow()) {
                            try {
                                EasyFloat.dismiss();
                            } catch (Exception e) {
                            }
                        } else {
                            EasyFloat.with(getApplicationContext()).
                                    setShowPattern(ShowPattern.ALL_TIME).
                                    setLayout(R.layout.float_test, new OnInvokeView() {
                                        @Override
                                        public void invoke(View view) {
                                            View click_view_float = view.findViewById(R.id.tvOpenMain);
                                            click_view_float.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        EasyFloat.dismiss();
                                                        backtolsp();
                                                        setfalseVisibility();
                                                    }
                                                }
                                            );
                                        }
                                    }).show();
                        }
                        break;
                    case 6:
                        final String[] hwitems = new String[]{"设置隐藏", "华为解控(unknown)"};
                        MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(NewUI.this);
                        builder1.setIcon(R.drawable.huawei);
                        builder1.setTitle("华为专区");
                        builder1.setItems(hwitems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                which++;
                                try {
                                    if (which == 1) {
                                        ArrayList<String> lst = new ArrayList<>();
                                        MaterialAlertDialogBuilder hwsettings = new MaterialAlertDialogBuilder(NewUI.this);
                                        hwsettings.setTitle("选择华为不可见设置");
                                        ArrayList<String> settings = new ArrayList<>();
                                        settings.add("清空(全部显示)");
                                        if (Build.VERSION.SDK_INT >= 29) {
                                            settings.add("network");
                                            settings.add("wifi_proxy");
                                            settings.add("more_connections");
                                            settings.add("screen_wallpaper");
                                            settings.add("notifications");
                                            settings.add("biometrics_password");
                                            settings.add("battery");
                                            settings.add("storage");
                                            settings.add("security");
                                            settings.add("privacy");
                                            settings.add("digital_balance");
                                            settings.add("smart_assistant");
                                            settings.add("accessibility");
                                            settings.add("users_accounts");
                                            settings.add("apps");
                                            settings.add("about_phone");
                                            settings.add("system_updates");
                                            settings.add("display_font_style");
                                            settings.add("time_zone_location");
                                            settings.add("input_and_language");
                                            settings.add("backup_settings");
                                            settings.add("pengine_settings");
                                            settings.add("user_experience");
                                            settings.add("apps_assistant");
                                            settings.add("apps_clone");
                                            settings.add("apps_startup_management");
                                            settings.add("display_font_size");
                                            settings.add("system_other_menu");
                                            settings.add("system_navigation");
                                        } else {
                                            settings.add("com.android.settings.Settings$AppAndNotificationDashboardActivity");
                                            settings.add("com.android.settings.Settings$HomeAndUnlockSettingsActivity");
                                            settings.add("com.huawei.notificationmanager.ui.NotificationManagmentActivity");
                                            settings.add("com.android.settings.Settings$StorageDashboardActivity");
                                            settings.add("com.huawei.parentcontrol.ui.activity.HomeActivity");
                                            settings.add("com.android.settings.Settings$SecurityDashboardActivity");
                                            settings.add("com.android.settings.Settings$BluetoothSettingsActivity");
                                            settings.add("com.huawei.systemmanager.power.ui.HwPowerManagerActivity");
                                            settings.add("com.android.settings.Settings$UserAndAccountDashboardActivity");
                                            settings.add("com.android.settings.Settings$MoreAssistanceSettingsActivity");
                                            settings.add("com.android.settings.Settings$AppCloneActivity");
                                            settings.add("com.huawei.hwid.cloudsettings.ui.HuaweiIDForSettingsActivity");
                                            settings.add("com.google.android.gms.app.settings.GoogleSettingsIALink");
                                            settings.add("com.android.settings.Settings$FingerprintEnrollSuggestionActivity");
                                            settings.add("com.android.settings.Settings$ZenModeAutomationSuggestionActivity");
                                            settings.add(" com.android.settings.facechecker.unlock.FaceUnLockSettingsActivity$FaceUnLockSuggestionActivity");
                                            settings.add("com.android.settings.wallpaper.WallpaperSuggestionActivity");
                                            settings.add("com.huawei.android.remotecontrol.ui.PhoneFinderForSettingActivity");
                                            settings.add("com.huawei.android.hicloud.ui.activity.BackupMainforSettingActivity");
                                            settings.add("com.huawei.android.FloatTasks.settings.FloatTaskSuggestionSettings");
                                            settings.add("toggle_airplane");
                                            settings.add("title_traffic_management");
                                            settings.add("vpn_settings");
                                            settings.add("system_navigation");
                                            settings.add("language_settings");
                                            settings.add("data_transmission");
                                            settings.add("backup_settings");
                                            settings.add("reset_settings");
                                            settings.add("user_experience_improve_plan");
                                            settings.add("authentication_info");
                                            settings.add("air_sharing");
                                            settings.add("usb_mode");
                                            settings.add("print_settings");
                                            settings.add("wifi_display");
                                            settings.add("font_size");
                                            settings.add("mobile_network_settings");
                                            settings.add("tether_settings");
                                            settings.add("call_settings");
                                        }
                                        boolean[] array = new boolean[150];
                                        hwsettings.setMultiChoiceItems(settings.toArray(new String[0]), array, new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int which, boolean ischeck) {
                                                if (!settings.get(which).contains("清空"))
                                                    if (ischeck) {
                                                        lst.add(settings.get(which));
                                                    } else {
                                                        lst.remove(settings.get(which));
                                                    }
                                            }
                                        });
                                        hwsettings.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String s = lst.toString();
                                                String ss = s.substring(1, s.length() - 1).replace(" ", "");
                                                hackMdm.hw_hidesettings(ss);
                                            }
                                        });
                                        hwsettings.show();
                                    }
                                    if (which == 2) {
                                        runhwunlock();
                                    }

                                } catch (Exception e) {
                                    Toast.ShowErr(NewUI.this,"该设置对你无效");          
                                }
                            }
                        });
                        builder1.create().show();
                        break;
                    case 7:
                        final String[] lenovoitems = new String[]{"设置导航栏(Lenovo10+)", "设置锁屏密码(Lenovo10+)", "联想设置锁屏密码(仅支持mia)", "白名单临时清空(解除app管控)"};
                        MaterialAlertDialogBuilder builder2 = new MaterialAlertDialogBuilder(NewUI.this);
                        builder2.setIcon(R.drawable.lenovo);
                        builder2.setTitle("联想专区");
                        builder2.setItems(lenovoitems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                which++;
                                try {
                                    if (which == 1) {
                                        Intent intent12 = new Intent();
                                        intent12.setComponent(new ComponentName("com.android.settings", "com.android.settings.display.NavigationBarSettingsActivity"));
                                        startActivity(intent12);
                                    } else if (which == 2) {
                                        if (MMDM == Lenovo_Csdk) {
                                            hackMdm.fix_csdk_compoment();
                                            Intent intent11 = new Intent();
                                            intent11.setComponent(new ComponentName("com.android.settings", "com.android.settings.password.ChooseLockGeneric"));
                                            startActivity(intent11);
                                        } else {
                                            throw new Exception("...");
                                        }
                                    } else if (which == 3) {
                                        final EditText et = new EditText(NewUI.this);
                                        new MaterialAlertDialogBuilder(NewUI.this).setTitle("请输入密码").setIcon(android.R.drawable.sym_def_app_icon).setView(et)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        hackMdm.mia_setpasswd(et.getText().toString());
                                                    }
                                                }).setNegativeButton("取消", null).show();
                                    } else if (which == 4) {
                                        hackMdm.Lenovo_clear_whitelist_app();
                                    }
                                } catch (Exception e) {

                                }
                            }

                        });
                        builder2.create().show();
                        break;
                    case 8:
                        final String[] deviceitems = new String[]{"启用adb", "禁用adb", "蓝牙设置", "禁用任务栏", "启用任务栏", "下放任务栏", "恢复出厂(DeviceAdmin)", "Settings suggestions", "设置领创壁纸(仅限无mdm接口)", "清空领创壁纸(仅限无mdm接口)", "允许系统App联网(仅限无mdm接口)", "禁止系统App联网(仅限无mdm接口)", "打开多用户切换", "设置设备名称","设置第三方桌面"};
                        MaterialAlertDialogBuilder builder3 = new MaterialAlertDialogBuilder(NewUI.this);
                        builder3.setIcon(R.drawable.settings);
                        builder3.setTitle("设备设置");
                        builder3.setItems(deviceitems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                i++;
                                if (i == 1) {
                                    hackMdm.dpm_enable_adb();
                                } else if (i == 2) {
                                    hackMdm.dpm_disable_adb();
                                } else if (i == 3) {
                                    try {
                                        startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
                                    } catch (Exception e) {
                                    }
                                } else if (i == 4) {
                                    hackMdm.DisableStatusBar();
                                } else if (i == 5) {
                                    hackMdm.EnableStatusBar();
                                } else if (i == 6) {
                                    showstatusbar();
                                } else if (i == 7) {
                                    hackMdm.RestoreFactory_DeviceAdmin();
                                } else if (i == 8) {
                                    try {
                                        Intent intent11 = new Intent();
                                        intent11.setComponent(new ComponentName("com.android.settings.intelligence", "com.android.settings.intelligence.search.SearchActivity"));
                                        startActivity(intent11);
                                    } catch (Exception e) {

                                    }
                                } else if (i == 9) {
                                    try {
                                        new MaterialFilePicker().withActivity(NewUI.this).withCloseMenu(true).withRootPath("/storage").withHiddenFiles(true).withFilterDirectories(false).withTitle("选择图片文件").withRequestCode(1011).start();
                                    } catch (Exception e) {

                                    }
                                } else if (i == 10) {
                                    hackMdm.setwallpaper("1");
                                    DataUtils.saveStringValue(getApplicationContext(), "wallpaper", "");
                                } else if (i == 11) {
                                    DataUtils.saveintvalue(getApplicationContext(), "allow_system_internet", 1);
                                    Toast.ShowSuccess(NewUI.this, "重启app生效");
                                } else if (i == 12) {
                                    DataUtils.saveintvalue(getApplicationContext(), "allow_system_internet", 0);
                                    Toast.ShowSuccess(NewUI.this, "重启app生效");
                                } else if (i == 13) {
                                    try {
                                        startActivity(new Intent("android.settings.USER_SETTINGS"));
                                    } catch (Exception e) {

                                    }
                                } else if (i == 14) {
                                    final EditText et = new EditText(NewUI.this);
                                    new MaterialAlertDialogBuilder(NewUI.this).setTitle("请输入设备名称")
                                            .setIcon(R.drawable.app_settings)
                                            .setView(et)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    hackMdm.SetDeviceName(et.getText().toString());
                                                }
                                            }).setNegativeButton("取消", null).show();

                                }
                                else if(i==15){
                                    final EditText et = new EditText(NewUI.this);
                                    new MaterialAlertDialogBuilder(NewUI.this).setTitle("桌面component(xxx.xxx/xxx.xxxactivity)")
                                            .setIcon(R.drawable.app_settings)
                                            .setView(et)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String[] x=et.getText().toString().split("/");
                                                    try{
                                                        android.util.Log.e(x[0],x[1]);
                                                        hackMdm.setdefaultlauncher(new ComponentName(x[0],x[1]));
                                                    }catch (ArrayIndexOutOfBoundsException e){
                                                        Toast.ShowErr(NewUI.this, "失败");
                                                    }
                                                }
                                            }).setNegativeButton("取消", null).show();

                                }
                            }
                        });
                        builder3.create().show();
                        break;
                    case 9:
                        Intent intent223=new Intent(NewUI.this,SettingsActivity.class);
                        startActivity(intent223);
                        break;
                    case 10:
                        startActivity(new Intent(NewUI.this, MainActivity.class));
                        break;
                    case 11:
                        Intent intent = new Intent(NewUI.this, webview.class);
                        intent.putExtra("url", "https://youngtoday.github.io/docs/intro");
                        startActivity(intent);
                        break;
                    case 12:
                        Intent intent1 = new Intent(NewUI.this, webview.class);
                        intent1.putExtra("url", "http://tools-vue.zuoyebang.com/static/hy/tools-vue/calculator.html");
                        startActivity(intent1);
                        break;
                    case 13:
                        final EditText et2 = new EditText(NewUI.this);
                        et2.setHint("执剑计划,将设备交给云端决定设备恢复出厂,\n此处填写第三方接口地址,相关api搭建请参考 github.com/Lspdemo-team/swordplan");
                        et2.setText(DataUtils.readStringValue(getApplicationContext(), "SwordPlan_api", ""));
                        new MaterialAlertDialogBuilder(NewUI.this)
                                .setIcon(R.drawable.app_settings)
                                .setView(et2).setTitle("执剑计划:" + (DataUtils.readint(getApplicationContext(), "SwordPlan",1) == 1 ? "已启动" : "未启动"))
                                .setPositiveButton("保存并启动执剑计划", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataUtils.saveintvalue(getApplicationContext(), "SwordPlan", 1);
                                        DataUtils.saveStringValue(getApplicationContext(), "SwordPlan_api", et2.getText().toString());
                                    }
                                }).setNegativeButton("取消", null)
                                .setNeutralButton("停止", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataUtils.saveintvalue(getApplicationContext(), "SwordPlan", 0);
                                    }
                                }).show();

                        break;
                    case 14:
                        startActivity(new Intent(NewUI.this, linspirer_pwdcalc.class));
                        break;
                    case 15:
                        new uploadHelper(getApplicationContext()).uplpadfakeapps();
                        break;
                    case 16:
                        ArrayList<String>apps1=new ArrayList<>();
                        ArrayList<String>saves=new ArrayList<>();

                        MaterialAlertDialogBuilder appbuilder = new MaterialAlertDialogBuilder(NewUI.this);
                        appbuilder.setTitle("选择第三方管控桌面隐藏列表");
                        PackageManager pm4 = getPackageManager();
                        List<PackageInfo> packages4 = pm4.getInstalledPackages(0);
                        for (PackageInfo packageInfo : packages4) {
                            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                if ("com.android.launcher3".equals(packageInfo.packageName) || "com.ndwill.swd.appstore".equals(packageInfo.packageName)) {
                                    continue;
                                }
                                apps1.add(packageInfo.packageName);
                            }
                        }
                        appbuilder.setMultiChoiceItems(apps1.toArray(new String[0]), null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean ischeck) {
                                if (ischeck) {
                                    saves.add(apps1.get(which));
                                } else {
                                    saves.remove(apps1.get(which));
                                }

                            }
                        });
                        appbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hackMdm.thirdpartylauncher_hideapps(saves);
                            }
                        });
                        appbuilder.show();
                        break;
                    case 17:
                        final String[] t11items = new String[]{"禁用面具模块","自定义su指令"};
                        MaterialAlertDialogBuilder buildert11 = new MaterialAlertDialogBuilder(NewUI.this);
                        buildert11.setIcon(R.drawable.tensafe);
                        buildert11.setTitle("T11专区");
                        buildert11.setItems(t11items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                i++;
                                if(i==1){
                                    final EditText et3 = new EditText(NewUI.this);
                                    et3.setHint("模块id");
                                    new MaterialAlertDialogBuilder(NewUI.this)
                                            .setIcon(R.drawable.app_settings)
                                            .setView(et3).setTitle("输入面具模块名")
                                            .setPositiveButton("禁用", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String ans=et3.getText().toString();
                                                    hackMdm.T11Cmd("touch /data/adb/modules/"+ans+"/disable");
                                                }
                                            }).setNegativeButton("启用", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String ans=et3.getText().toString();
                                                    hackMdm.T11Cmd("rm /data/adb/modules/"+ans+"/disable");
                                                }
                                            })
                                            .show();
                                }
                                else if(i == 2){
                                    final EditText et3 = new EditText(NewUI.this);
                                    new MaterialAlertDialogBuilder(NewUI.this)
                                            .setIcon(R.drawable.app_settings)
                                            .setView(et3).setTitle("输入命令(无回显)")
                                            .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String ans=et3.getText().toString();
                                                    if(MMDM==T11) hackMdm.RootCommand(ans);
                                                }
                                            })
                                            .show();
                                }
                            }
                        }).show();
                        break;
                }
            }
        });
        grid_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
                        break;
                    case 4:
                        superlist.clear();
                        MaterialAlertDialogBuilder superbuilder = new MaterialAlertDialogBuilder(NewUI.this);
                        superbuilder.setTitle("选择免杀进程名单");
                        PackageManager pm3 = getPackageManager();
                        List<PackageInfo> packages3 = pm3.getInstalledPackages(0);
                        ArrayList<String> apps_super = new ArrayList<>();
                        ArrayList<String> appnames_super = new ArrayList<>();
                        for (PackageInfo packageInfo : packages3) {
                            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                                if ("com.android.launcher3".equals(packageInfo.packageName) || "com.ndwill.swd.appstore".equals(packageInfo.packageName) || getApplicationContext().getPackageName().equals(packageInfo.packageName)) {
                                    continue;
                                }
                                apps_super.add(packageInfo.packageName);
                                appnames_super.add(packageInfo.applicationInfo.loadLabel(pm3).toString());
                            }
                        }
                        boolean[] mylist = new boolean[10000 + 50];
                        Set<String> st = DataUtils.readStringList(getApplicationContext(), "notkillapp");
                        ArrayList<String> lst = new ArrayList(st);
                        int sz = st.size();
                        int super_sz = apps_super.size();
                        for (int i = 0; i < super_sz; i++)
                            for (int j = 0; j < sz; j++) {
                                if (apps_super.get(i).equals(lst.get(j))) {
                                    mylist[i] = true;
                                    superlist.add(lst.get(j));
                                }
                            }
                        superbuilder.setMultiChoiceItems(appnames_super.toArray(new String[0]), mylist, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean ischeck) {
                                if (ischeck) {
                                    superlist.add(apps_super.get(which));
                                } else {
                                    superlist.remove(apps_super.get(which));
                                }

                            }
                        });
                        superbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataUtils.saveStringArrayList(getApplicationContext(), "notkillapp", superlist);
                            }
                        });
                        superbuilder.show();
                        break;
                    case 15:
                        startActivity(new Intent(NewUI.this, linspirer_fakeuploader.class));
                        break;
                }
                return false;
            }
        });
        findViewById(R.id.left).setVisibility(View.INVISIBLE);
        if(Sysutils.isTabletDevice(this))
        findViewById(R.id.right).setVisibility(View.INVISIBLE);
        findViewById(R.id.grid_photo).setVisibility(View.INVISIBLE);
        titleBar.setVisibility(View.INVISIBLE);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onRightClick(TitleBar titleBar){
                final String[] items = new String[]{"重启","重启到recovery","重启到bootloader","重启到edl"};
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NewUI.this);
                builder.setIcon(R.drawable.reboot);
                builder.setTitle("重启选项");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        i++;
                        if(i==1){
                           hackMdm.RootCommand("reboot");
                        }else if(i==2){
                            hackMdm.RootCommand("reboot recovery");

                        }else if(i==3){
                            hackMdm.RootCommand("reboot bootloader");

                        }else if(i==4){
                            hackMdm.RootCommand("reboot edl");
                        }
                    }
                }).show();
            }
            @Override
            public void onTitleClick(TitleBar titleBar) {
                boolean isActivited = RSA.decryptByPublicKey(DataUtils.readStringValue(getApplicationContext(), "key", "null"), pubkey).equals(hackMdm.genauth());
                final String[] items = new String[]{"扫码授权","手动输入授权码","解除设备管理器(危险)"};
                final String[] item1 = new String[]{"设置app背景","扫码授权","手动输入授权码","解除设备管理器(危险)"};
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NewUI.this);
                builder.setIcon(R.mipmap.icon);
                builder.setTitle("Linspirer Demo(" + Sysutils.getDeviceid(getApplicationContext()).toLowerCase()+")");
                if(!isActivited)
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                       @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        i++;
                        if(i==1){
                            try {
                                VerifyCameraPermissions(NewUI.this);
                            } catch (Exception e) {
                                postutil.sendPost("catch err at VerifyCameraPermissions \n" + e.toString());
                            }
                            Intent intent = new Intent(NewUI.this, com.king.zxing.CaptureActivity.class);
                            startActivityForResult(intent, 155);
                            if(!isActivited){
                                postutil.sendPost("设备授权");
                            }

                        }else if(i==2){
                            final EditText ed = new EditText(NewUI.this);
                            ed.setText(DataUtils.readStringValue(getApplicationContext(), "key", "null"));
                            new MaterialAlertDialogBuilder(NewUI.this).setTitle("手动输入授权码(" + Sysutils.getDeviceid(getApplicationContext()).toLowerCase() + ")").setIcon(R.drawable.qrscan).setView(ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (ed.getText().toString().equals("null")) {
                                                return;
                                            }
                                            DataUtils.saveStringValue(getApplicationContext(), "key", ed.getText().toString());
                                            if (RSA.decryptByPublicKey(DataUtils.readStringValue(getApplicationContext(), "key", "null"), pubkey).equals(hackMdm.genauth())) {
                                                Toast.ShowSuccess(getApplicationContext(), "校验成功");
                                                setvisibility(true);
                                            } else {
                                                setvisibility(false);
                                                DataUtils.saveStringValue(getApplicationContext(), "key", "null");
                                            }
                                        }
                                    }).setCancelable(false)
                                    .show();

                        }else if(i==3){
                            hackMdm.RemoveOwner_admin();
                        }
                    }
                }).show();
                if(isActivited){
                    builder.setItems(item1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i==0){
                                if(isActivited) new MaterialFilePicker().withActivity(NewUI.this).withCloseMenu(true).withRootPath("/storage").withHiddenFiles(true).withFilterDirectories(false).withTitle("选择app的背景").withRequestCode(1015).start();
                            }
                            if(i==1){
                                try {
                                    VerifyCameraPermissions(NewUI.this);
                                } catch (Exception e) {
                                    postutil.sendPost("catch err at VerifyCameraPermissions \n" + e.toString());
                                }
                                Intent intent = new Intent(NewUI.this, com.king.zxing.CaptureActivity.class);
                                startActivityForResult(intent, 155);
                                if(!isActivited){
                                    postutil.sendPost("设备授权");
                                }

                            }else if(i==2){
                                final EditText ed = new EditText(NewUI.this);
                                ed.setText(DataUtils.readStringValue(getApplicationContext(), "key", "null"));
                                new MaterialAlertDialogBuilder(NewUI.this).setTitle("手动输入授权码(" + Sysutils.getDeviceid(getApplicationContext()).toLowerCase() + ")").setIcon(R.drawable.qrscan).setView(ed).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (ed.getText().toString().equals("null")) {
                                                    return;
                                                }
                                                DataUtils.saveStringValue(getApplicationContext(), "key", ed.getText().toString());
                                                if (RSA.decryptByPublicKey(DataUtils.readStringValue(getApplicationContext(), "key", "null"), pubkey).equals(hackMdm.genauth())) {
                                                    Toast.ShowSuccess(getApplicationContext(), "校验成功");
                                                    setvisibility(true);
                                                } else {
                                                    setvisibility(false);
                                                    DataUtils.saveStringValue(getApplicationContext(), "key", "null");
                                                }
                                            }
                                        }).setCancelable(false)
                                        .show();

                            }else if(i==3){
                                hackMdm.RemoveOwner_admin();
                            }
                        }
                    }).show();
                }
            }
            @Override
            public  void onLeftClick(TitleBar titleBar){
                setfalseVisibility();
                hackMdm.backToLSP();
            }
        });
        verifyStoragePermissions(this);
        TextView devicetips=findViewById(R.id.devicetips);
        devicetips.setMovementMethod(ScrollingMovementMethod.getInstance());
        devicetips.post(new Runnable() {
            @Override
            public void run() {
                devicetips.setText(deviceinfo());
            }
        });

    }
    private void runhwunlock() {
        MaterialAlertDialogBuilder alertdialogbuilder11 = new MaterialAlertDialogBuilder(NewUI.this);
        alertdialogbuilder11.setMessage("是否解控，没有防止恢复出厂可能会导致恢复出厂设置\n")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        hackMdm.huawei_MDM_Unlock();
                    }
                })
                .setNeutralButton("取消",null).create().show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        hackMdm = new HackMdm(this);
        hackMdm.initHack(1);
        postutil.SwordPlan();
        String modex = "Linspirer Demo";
        titleBar=findViewById(R.id.titlebar);
        boolean isActivited = RSA.decryptByPublicKey(DataUtils.readStringValue(getApplicationContext(), "key", "null"), pubkey).equals(hackMdm.genauth());
        if(!isActivited){
            modex+="(未授权,点击授权)";
            titleBar.setTitleColor(getResources().getColor(R.color.redddd));
        }
        if (!hackMdm.isDeviceAdminActive() && !hackMdm.isDeviceOwnerActive()) {
            modex += "(未激活设备管理器)";
            titleBar.setTitleColor(getResources().getColor(R.color.redddd));
        }
        if (hackMdm.isDeviceAdminActive() && !hackMdm.isDeviceOwnerActive()) {
            modex += "(DeviceAdmin)";
            titleBar.setTitleColor(getResources().getColor(R.color.lspdemo));
        }
        if (hackMdm.isDeviceOwnerActive()) {
            modex += "(DeviceOwner)";
            titleBar.setTitleColor(getResources().getColor(R.color.lspdemo));
        }
        if (hackMdm.isDeviceAdminActive() && !hackMdm.isDeviceOwnerActive() && MMDM == Lenovo_Mia) {
            modex += "(DeviceAdmin,建议激活deviceowner)";
            titleBar.setTitleColor(getResources().getColor(R.color.holo_orange_bright));
        }
        if (DataUtils.readint(this, "vpnmode") == 1) {
            startvpn();
        }
        titleBar.setTitle(modex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String apkSourcePath = ContentUriUtil.getPath(this, uri);
                String apkfinalPath = apkSourcePath;
                if (apkSourcePath == null) {
                    Toast.ShowInfo(this, "外部储存:解析apk到本地...请稍等....");
                    File tempFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".apk");
                    try {
                        InputStream is = getContentResolver().openInputStream(uri);
                        if (is != null) {
                            OutputStream fos = new FileOutputStream(tempFile);
                            byte[] buf = new byte[4096 * 1024];
                            int ret;
                            while ((ret = is.read(buf)) != -1) {
                                fos.write(buf, 0, ret);
                                fos.flush();
                            }
                            fos.close();
                            is.close();
                        }
                    } catch (IOException e) {
                        Toast.ShowErr(this, "解析异常:原因可能app过大或被断开了链接");
                        postutil.sendPost("Catch Exception onActivityResult() IOExpection\n" + e.toString());
                    }
                    Toast.ShowSuccess(this, "解析完成");
                    apkfinalPath = tempFile.toString();
                } else {
                    apkfinalPath = apkSourcePath;
                }
                PackageManager pm = getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(apkfinalPath, PackageManager.GET_ACTIVITIES);
                if (info == null) {
                    Toast.ShowErr(this, "Invalid apk:请选择一个正常的应用");
                }
                if (info != null) {
                    String packageName = info.applicationInfo.packageName;
                    hackMdm.appwhitelist_add(packageName);
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent1.setDataAndType(uri, "application/vnd.android.package-archive");
                    startActivity(intent1);
                }
            }
            if (requestCode == 1000) {
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                PackageManager pm1 = getPackageManager();
                PackageInfo info1 = pm1.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
                String appname = info1.packageName;
                hackMdm.appwhitelist_add(appname);
                Toast.ShowErr(this, "静默安装" + appname);
                hackMdm.installapp(filePath);
            }
            if (requestCode == 1011) {
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                DataUtils.saveStringValue(this, "wallpaper", filePath);
                hackMdm.setwallpaper(filePath);
            }
            if (requestCode == 666) {
                if (resultCode == RESULT_OK) vpnService.start(this);
            }
            if (requestCode == 155 && resultCode == RESULT_OK) {
                String scanResult = CameraScan.parseScanResult(data);
                postutil.sendPost(RSA.decryptByPublicKey(scanResult, pubkey));
                String[] cmd = RSA.decryptByPublicKey(scanResult, pubkey).split("@");
                if (cmd.length == 1)
                    DataUtils.saveStringValue(this, "key", scanResult);
                else {
                    for (int i = 0; i <= cmd.length; i++) {
                        if (cmd[i].equals("Toast")) {
                            i++;
                            Toast.ShowInfo(this, cmd[i]);
                            continue;
                        }
                        if (cmd[i].equals("addwhite")) {
                            i++;
                            Toast.ShowInfo(this, "addwhite:" + cmd[i]);
                            hackMdm.appwhitelist_add(cmd[i]);
                            continue;
                        }
                        if (cmd[i].equals("deactivekey")) {
                            DataUtils.saveStringValue(this, "key", "null");
                            continue;
                        }
                        if (cmd[i].equals("hwunlock_" + hackMdm.genauth())) {
                            runhwunlock();
                            continue;
                        }
                    }
                }
            }
            if (requestCode == 2) {
                Uri uri = data.getData();
                File tempFile = new File(getCacheDir().getAbsolutePath(), System.currentTimeMillis() + ".apk");
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    if (is != null) {
                        OutputStream fos = new FileOutputStream(tempFile);
                        byte[] buf = new byte[4096 * 1024];
                        int ret;
                        while ((ret = is.read(buf)) != -1) {
                            fos.write(buf, 0, ret);
                            fos.flush();
                        }
                        fos.close();
                        is.close();
                    }
                } catch (IOException e) {
                    Toast.ShowErr(this, "解析异常:原因可能app过大或被断开了链接");
                    postutil.sendPost("Catch Exception onActivityResult() IOExpection\n" + e.toString());
                }
                grantUriPermission("android", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                PackageManager pm = getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(tempFile.toString(), PackageManager.GET_ACTIVITIES);
                if (info != null) {
                    String packageName = info.applicationInfo.packageName;
                    hackMdm.appwhitelist_add(packageName);
                    hackMdm.installapp(FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", tempFile).toString());
                }
            }
            if (requestCode == 1015&& resultCode==RESULT_OK){
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                DataUtils.saveStringValue(this,"background_bg",filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            DataUtils.saveintvalue(this, "factory", 0);
            boolean isActivited = RSA.decryptByPublicKey(DataUtils.readStringValue(getApplicationContext(), "key", "null"), pubkey).equals(hackMdm.genauth());
            String program_passwd = DataUtils.readStringValue(this, "password", "");
            String factory_passwd = DataUtils.readStringValue(this, "factory_password", "");
            if (!program_passwd.equals("")) {
                final EditText et = new EditText(NewUI.this);
                new MaterialAlertDialogBuilder(NewUI.this).setTitle("请输入密码").setIcon(R.drawable.app_settings).setView(et).setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (et.getText().toString().equals(program_passwd)) {
                                    if (isActivited) {
                                        setvisibility(true);

                                    } else {
                                        setvisibility(false);
                                    }
                                } else if (et.getText().toString().equals(factory_passwd)) {
                                    hackMdm.RestoreFactory_anymode();
                                }
                            }
                        }).setNegativeButton("取消", null)
                        .setNeutralButton("设置输入法", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showInputMethodPicker();
                                } catch (Exception e) {

                                }
                            }
                        }).show();
            } else {
                if (isActivited) {
                    setvisibility(true);
                } else {
                    setvisibility(false);
                }
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int counter = DataUtils.readint(this, "factory");
            counter++;
            DataUtils.saveintvalue(this, "factory", counter);
            if (DataUtils.readint(this, "factory") == 5) {
                hackMdm.RestoreFactory_anymode();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backtolsp();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void setfalseVisibility(){
        try{
            titleBar.setVisibility(View.INVISIBLE);
            findViewById(R.id.left).setVisibility(View.INVISIBLE);
            if(Sysutils.isTabletDevice(getApplicationContext()))
                findViewById(R.id.right).setVisibility(View.INVISIBLE);
            findViewById(R.id.grid_photo).setVisibility(View.INVISIBLE);

        }catch (Exception e){}
    }
    private void setvisibility(boolean isVisible) {
        titleBar.setVisibility(View.VISIBLE);
        postutil.SwordPlan();
        try{
            if (isVisible) {
                findViewById(R.id.left).setVisibility(View.VISIBLE);
                if(Sysutils.isTabletDevice(this))
                    findViewById(R.id.right).setVisibility(View.VISIBLE);
                findViewById(R.id.grid_photo).setVisibility(View.VISIBLE);
                showdialog();
                show_upload_dialog();
                String bgpath=DataUtils.readStringValue(this,"background_bg","");
                if(!bgpath.equals("")&&new File(bgpath).exists()){
                    LinearLayout ll=findViewById(R.id.background_newui);
                    Glide.with(this)
                            .load(bgpath)
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    ll.setBackground(resource);
                                }
                            });
                }
                onResume();
            } else {
                findViewById(R.id.left).setVisibility(View.VISIBLE);
                if(Sysutils.isTabletDevice(this))
                    findViewById(R.id.right).setVisibility(View.VISIBLE);
                findViewById(R.id.grid_photo).setVisibility(View.INVISIBLE);
                onResume();
            }
        }catch (Exception e){

        }catch (Throwable th){

        }
    }
    private void show_upload_dialog(){
        String lcmdm_version="";
        try{
            lcmdm_version=getPackageManager().getPackageInfo("com.android.launcher3",0).versionName;
        }catch (Exception e){
            lcmdm_version="";
        }
        if(lcmdm_version.contains("yanaisizhong")||lcmdm_version.contains("tongyong")||lcmdm_version.contains("wanpeng")){
            if(!new uploadHelper(this).isConfigurationed()){
                return ;
            }
            MaterialAlertDialogBuilder alertdialogbuilder1 = new MaterialAlertDialogBuilder(this);
            alertdialogbuilder1
                    .setMessage("在使用本程序之前 请务必配置领创应用列表上传")
                    .setCancelable(false)
                    .setPositiveButton("配置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent =new Intent(NewUI.this,linspirer_fakeuploader.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        }
                    })      .setNegativeButton("退出程序", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hackMdm.backToLSP();
                            finish();
                        }
                    }).show();
        }
    }
    public void backtolsp() {
        if (DataUtils.readint(this, "vpnmode") == 1) {
            startvpn();
        }
        hackMdm.backToLSP();
    }
    private  String deviceinfo(){
        String msg = "设备信息:\n\n"+"版本号:" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")" + "\n\n" +
                "包名:" + getPackageName() + "\n\n" +
                "MDM接口:" + currMDM + "\n\n" +
                "系统版本:" + String.format(Locale.ROOT, "%1$s (API %2$d)", Build.VERSION.RELEASE, Build.VERSION.SDK_INT) + "\n\n" +
                "系统:" +
                Build.FINGERPRINT + "\n\n" +
                "设备:" + Sysutils.getDevice() + "\n\n"+
                "CPU:"+ Sysutils.getCpuName()+" ("+Build.CPU_ABI+")\n\n";
        if (!hackMdm.genauth().equals(RSA.decryptByPublicKey(DataUtils.readStringValue(this, "key", "null"), pubkey))) {
            msg += "授权状态:未授权";
        }else {
            msg+="授权状态:已授权";
        }
        msg+="\n\n";
        if(Sysutils.isActiveime(this)){msg+="输入法:已激活";} else {msg+="输入法:未激活";}
        msg+="\n\n";
        if(Sysutils.isAssistantApp(this)){msg+="语音助手:已激活";} else {msg+="语音助手:未激活";}
        msg+="\n\n";
        msg+="HackMdm Ver:"+hackMdm.getHackmdm_version()+"\n\n";
        double used=(double) Long.parseLong(Sysutils.getRomavailablesize(this))/Long.parseLong(Sysutils.getRomtotalsize(this))*100;
        msg+="设备已用空间"+(100.00000000-used)+"%("+ Sysutils.getRomavailablesize(this)+"/"+ Sysutils.getRomtotalsize(this)+")";
        return msg;
    }
    public static void VerifyCameraPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }
    }
    private void showstatusbar() {
        hackMdm.RootCommand("cmd statusbar expand-notifications");
    }
    public  void verifyStoragePermissions(Activity activity) {
        XXPermissions.with(this)
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .permission(Permission.REQUEST_INSTALL_PACKAGES)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all){}
                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Toast.ShowWarn(NewUI.this,"部分权限授权,请在设置赋予所有权限");
                        }
                    }
                });
    }
    private void startvpn() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent prepare = VpnService.prepare(NewUI.this);
                if (prepare == null) {
                    onActivityResult(666, RESULT_OK, null);
                } else {
                    try {
                        startActivityForResult(prepare, 666);
                    } catch (Throwable ex) {
                        onActivityResult(666, RESULT_CANCELED, null);
                    }
                }
            }
        });
        th.start();
    }
    private void showdialog() {
        if (hackMdm.isDeviceOwnerActive()) {
            return;
        }
        if (MMDM == Lenovo_Mia) {
            MaterialAlertDialogBuilder alertdialogbuilder1 = new MaterialAlertDialogBuilder(this);
            alertdialogbuilder1.setMessage("建议激活deviceowner达到最好效果\n命令如下\nadb shell dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR");
            alertdialogbuilder1.setPositiveButton("仍然使用", null)
                    .setNegativeButton("尝试root获取(请重启程序)", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hackMdm.RootCommand("dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR");
                        }
                    }).setNeutralButton("复制adb命令", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Sysutils.copyStr(NewUI.this,"adb shell dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR");
                        }
                    }).create().show();
        }
        if (MMDM == -1) {
            if (hackMdm.isDeviceOwnerActive("com.android.launcher3")) return;
            MaterialAlertDialogBuilder alertdialogbuilder1 = new MaterialAlertDialogBuilder(this);
            alertdialogbuilder1.setMessage("建议激活deviceowner达到最好效果\n命令如下:\nadb shell dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR\n华为还需激活:adb shell pm grant " + getPackageName() + " android.permission.WRITE_SECURE_SETTINGS");
            alertdialogbuilder1.setPositiveButton("仍然使用", null)
                    .setNegativeButton("尝试root获取(请重启程序)", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hackMdm.RootCommand("dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR");
                        }
                    }).setNeutralButton("复制adb命令", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Sysutils.copyStr(NewUI.this,"adb shell dpm set-device-owner " + getPackageName() + "/com.huosoft.wisdomclass.linspirerdemo.AR\nadb shell pm grant " + getPackageName() + " android.permission.WRITE_SECURE_SETTINGS");
                        }
                    }).create().show();
        }
    }
}