package com.andoresu.kelmarstore.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import com.andoresu.kelmarstore.R;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;
import static com.andoresu.kelmarstore.utils.MyUtils.checkHasAllPermissions;
import static com.andoresu.kelmarstore.utils.MyUtils.removeTrailingLineFeed;
import static com.andoresu.kelmarstore.utils.MyUtils.showErrorDialog;

import com.crashlytics.android.Crashlytics;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;


@SuppressLint({"Registered", "LogNotTimber"})
public abstract class BaseActivity extends AppCompatActivity implements BaseView{


    private String TAG = BASE_TAG + BaseActivity.class.getSimpleName();

    private Bundle mainBundle;

    public int MENU_GROUP_ID = 0;

    public final int ITEM_MENU_LOGOUT_ID = 1;

    public final int ITEM_MENU_DOWNLOAD_MAP = 2;

    private boolean bound = false;

    private Class<?> childClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

//        if (isNightMode()){
//            setTheme(R.style.NightAppTheme);
//        }else{
//            setTheme(R.style.AppTheme);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestActivityPermissions();
        }
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void restart(Class<?> cls, Bundle params){
        Intent intent = new Intent(this, cls);
        if(params != null){
            intent.putExtras(params);
        }
        startActivity(intent);
        finish();
    }

    public void setMainBundle(Bundle bundle){
        this.mainBundle = bundle;
    }


    public CharSequence getText(int id, Object... args) {
        for(int i = 0; i < args.length; ++i)
            args[i] = args[i] instanceof String? TextUtils.htmlEncode((String)args[i]) : args[i];
        return removeTrailingLineFeed(Html.fromHtml(String.format(Html.toHtml(new SpannedString(getText(id))), args)));
    }

    public void requestActivityPermissions(){
        boolean permissions = checkHasAllPermissions(this);
        if (!permissions) {
            MyUtils.requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!checkHasAllPermissions(this)){
            showErrorDialog(this, getString(R.string.error_no_permission), (String) null, (dialogInterface, i) -> requestActivityPermissions());
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public interface ServiceConnectionListener{

        void onServiceConnected();

        void onServiceDisconnected();
    }

}