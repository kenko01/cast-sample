package com.example.sampleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.sampleapp.utils.PermissionUtils;
import com.memo.CastContext;
import com.memo.CastMediaInfo;
import com.memo.CastMediaInfoConstract;
import com.memo.cable.MemoDeviceServiceHelper;
import com.memo.remote.CastSessionManager;
import com.memo.sdk.IMemoDeviceListener;
import com.memo.sdk.MemoTVCastSDK;
import com.memo.uiwidget.CastButton;
import com.memo.uiwidget.MiniController;
import com.memo.uiwidget.TubiRemoteDialogFragment;

import org.cybergarage.upnp.Device;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMemoDeviceListener, AdapterView.OnItemSelectedListener{
    public ExecutorService mThreadPool = Executors.newFixedThreadPool(5);
    android.support.v4.app.FragmentManager mFragmentManager;
    CastMediaInfo mCastMediaInfo;
    CastButton mCastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        mFragmentManager = getSupportFragmentManager();
        CastContext.getSharedInstance(this).checkPermission(this);
        CastContext.getSharedInstance(this).checkLocationSwitch(this);
        findViewById(R.id.btn_scan).setOnClickListener(this);
        findViewById(R.id.btn_device).setOnClickListener(this);
        mCastButton = new CastButton();
        mCastButton.prepare(this, (ImageView) findViewById(R.id.tvcast_btn));
        findViewById(R.id.tvcast_btn).setOnClickListener(this);
        MiniController.getInstance().onCreate(MainActivity.this);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return mCastButton.onShowMenu(this,menu);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
        MemoTVCastSDK.unRegisterDeviceListener(this);
    }
    @Override
    public void onClick(View v) {
       if(R.id.tvcast_btn==v.getId()){
            onClickCastButton();
       }
    }

    public void onClickCastButton(){
        final String playUrl = "http://your real playing url";
        final String rawUrl = "http://your raw video url";
        final String videoName= "your video name";
        final String cover = "http://your cover image url";
        final String avater = "http://your avater image url";
        final String author= "video author";
        final CastMediaInfoConstract.CastMediaInfoCallback callback = new CastMediaInfoConstract.CastMediaInfoCallback(){

            @Override
            public boolean isSyncPlayingInfo() {
                return false;
            }

            @Override
            public CastMediaInfo getSyncCastMediaInfo() {
                if(mCastMediaInfo!=null)
                {
                    return mCastMediaInfo;
                }else {
                    mCastMediaInfo = new CastMediaInfo.Builder()
                            .setPlayUrl(playUrl)
                            .setVideoName(videoName)
                            .setRawUrl(rawUrl)
                            .setVideoCover(cover)
                            .setVideoAuthorName("Author")
                            .setVideoAvater(avater)
                            .build();
                }
                return null;
            }

            @Override
            public void getASyncCastMediaInfo(final CastMediaInfoConstract.AyncMediaInfoCallback callback) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "https://host/get - remote-playing-url";
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        Call call = okHttpClient.newCall(request);
                        try {
                            Response response = call.execute();
                            String responseString = response.body().string();
                            JSONObject json = new JSONObject(responseString);
                            String playingUrl = json.optString("playingUrl");
                            String videoName = json.optString("videoName");
                            String rawUrl =json.optString("rawUrl");
                            String avater =json.optString("avater");
                            String cover =json.optString("cover");
                            mCastMediaInfo = new CastMediaInfo.Builder()
                                    .setPlayUrl(playingUrl)
                                    .setVideoName(videoName)
                                    .setRawUrl(rawUrl)
                                    .setVideoCover(cover)
                                    .setVideoAuthorName(author)
                                    .setVideoAvater(avater)
                                    .build();
                            callback.onResponseCastMediaInfo(mCastMediaInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };

        mCastButton.onClickCastButton(MainActivity.this,callback);
    }

    @Override
    public void onDeviceAdd(Device device) {
        Log.i("NOTFOUND","onDeviceAdd");
    }

    @Override
    public void onDeviceRemove(Device device) {
        if( MemoDeviceServiceHelper.getInstance().getDevices().size()==0){
        }
    }

    @Override
    public void onDeviceCheated(String name, String chipId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCastButton.onActivityDestroy();
        MemoTVCastSDK.exit();
    }

    public int mSelectIndex = -1;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectIndex = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.media_route_menu_item){
            onClickCastButton();
        }
        return super.onOptionsItemSelected(item);

    }

    private void initToolbar() {
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.tb_toolbar);
        if (mToolbarTb != null) {
            TextView labelTitle = (TextView) mToolbarTb.findViewById(R.id.tv_title_toolbar);
            setSupportActionBar(mToolbarTb);
        }
    }
}
