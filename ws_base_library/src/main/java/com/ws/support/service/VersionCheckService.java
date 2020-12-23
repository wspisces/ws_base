package com.ws.support.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.ws.base.BuildConfig;
import com.ws.support.http.BaseObserver;
import com.ws.support.http.HttpHelper;
import com.ws.support.http.ResultTO;
import com.ws.support.http._ApiUrl;
import com.ws.support.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ws.support.http.HttpHelper.*;

/**
 * 版本验证服务,升级
 *
 * @author ws
 */
public class VersionCheckService extends IntentService
{
    private static final String ACTION = "com.heinqi.im.service.action.version";
    public static        String mEnvironmentDirectory
                                       = Environment.getExternalStorageDirectory().getPath();
    public static        String path_apk
                                       = mEnvironmentDirectory + "/htIM.apk";
    int type;

    public VersionCheckService()
    {
        super("VersionCheckService");
    }

    public static void startAction(Context context, int type, int version)
    {
        Intent intent = new Intent(context, VersionCheckService.class);
        intent.setAction(ACTION);
        intent.putExtra("type", type);
        intent.putExtra("version", version);
        context.startService(intent);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onHandleIntent(Intent intent)
    {
        /*测试安装网易新闻*/
        //downloadAPK("http://3g.163.com/links/4636");

        if (intent != null)
        {
            Logger.i("启动版本检查");
            final String action = intent.getAction();
            type = intent.getIntExtra("type", -1);
            int version = intent.getIntExtra("version", 0);

            if (ACTION.equals(action) && type > 0 && version > 0)
            {
                update(version);
            }
        }

    }

    private void update(int version)
    {
        subscribe(createService(_ApiUrl.class).GetCurrentVersion(), new BaseObserver<ResultTO>(this, false)
        {
            @Override
            public void onSuccess(ResultTO o)
            {
                if (o.isSucc())
                {
                    try
                    {
                        JSONObject result = o.toJsonObject();
                        int versionCode = result.optInt("verNo");
                        //if (BuildConfig.VERSION_CODE < versionCode)
                        Logger.i("currentVersion=" + version + " serverVersion=" + versionCode);
                        if (version < versionCode)
                        {
                            String downloadUrl = result.optString("downloadUrl");
                            if (StringUtils.isEmptyWithNull(downloadUrl)) return;
                            Logger.i("downloadUrl=" + downloadUrl);
                            EventBus.getDefault().postSticky(NewVersionEvent.hasNew(true, type));
                            downloadAPK(downloadUrl);
                        } else
                        {
                            File file = new File(path_apk);
                            if (file.exists())
                            {
                                file.delete();//删除旧的APK
                            }
                            EventBus.getDefault().postSticky(NewVersionEvent.hasNew(false, type));
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(Throwable e)
            {
                Logger.e("获取版本", e);
            }
        });
    }


    private void downloadAPK(String downloadUrl)
    {
        new Thread(() ->
        {
            EventBus.getDefault().postSticky(NewVersionEvent.downLoadStart(type));
            //OK设置请求超时时间，读取超时时间
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            _ApiUrl apiService = retrofit.create(_ApiUrl.class);
            //String url = "http://oa.tjpc.com.cn:8099/Content/App/android/heinqi_OA-release.apk";
            Observable<ResponseBody> observable = apiService.download(downloadUrl);
            observable.subscribeOn(Schedulers.io())
                    .subscribe(new Observer<ResponseBody>()
                    {
                        @Override
                        public void onSubscribe(Disposable d)
                        {

                        }

                        @Override
                        public void onNext(ResponseBody responseBody)
                        {
                            InputStream inputStream = null;
                            long total = 0;
                            long responseLength;
                            FileOutputStream fos = null;
                            try
                            {
                                byte[] buf = new byte[2048];
                                int len;
                                responseLength = responseBody.contentLength();
                                inputStream = responseBody.byteStream();

                                File dir = new File(mEnvironmentDirectory);
                                if (!dir.exists())
                                {
                                    dir.mkdirs();
                                }
                                final File file = new File(path_apk);
                                if (file.exists())
                                {
                                    file.delete();
                                } else
                                {
                                    file.createNewFile();
                                }
                                fos = new FileOutputStream(file);
                                int progress = 0;
                                int lastProgress;
                                long startTime = System.currentTimeMillis(); // 开始下载时获取开始时间
                                while ((len = inputStream.read(buf)) != -1)
                                {
                                    fos.write(buf, 0, len);
                                    total += len;
                                    lastProgress = progress;
                                    progress = (int) (total * 100 / responseLength);
                                    long curTime = System.currentTimeMillis();
                                    long usedTime = (curTime - startTime) / 1000;
                                    if (usedTime == 0)
                                    {
                                        usedTime = 1;
                                    }
                                    long speed = (total / usedTime); // 平均每秒下载速度
                                    // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                                    if (progress > 0 && progress != lastProgress)
                                    {
                                        Logger.e("VersionCheckService process=" + progress);
                                    }
                                }
                                fos.flush();
                                /*发送到主页*/
                                EventBus.getDefault().postSticky(NewVersionEvent.downLoadFinish(type));
                            } catch (final Exception e)
                            {
                                Logger.e("下载新版本", e);
                                EventBus.getDefault().postSticky(NewVersionEvent.downLoadError(type));
                            } finally
                            {
                                try
                                {
                                    if (fos != null)
                                    {
                                        fos.close();
                                    }
                                    if (inputStream != null)
                                    {
                                        inputStream.close();
                                    }
                                } catch (Exception e)
                                {
                                    EventBus.getDefault().postSticky(NewVersionEvent.downLoadError(type));
                                    e.printStackTrace();
                                }

                            }

                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            Logger.e("下载新版本", e);
                            EventBus.getDefault().postSticky(NewVersionEvent.downLoadError(type));
                        }

                        @Override
                        public void onComplete()
                        {

                        }
                    });


        }).start();
    }
}
