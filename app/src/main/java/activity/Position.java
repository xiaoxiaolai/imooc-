package activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.xiao.cui.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.CoursesPosition;
import httpClient.Constant;
import httpClient.MyServerInterface;
import httpClient.OkHttp3Utils;
import modle.PageAdapter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scroll.SwipeBackControllerLayout;
import ui.LoadingDialog;
import ui.ZoomOutPageTransformer;
import utils.Blur;
import utils.ImageUtils;

public class Position extends AppCompatActivity {
    private MyServerInterface serverInterface;
    private ViewPager viewPager;
    private PageAdapter adapter;
    private LoadingDialog loaddingDialog;
    private ArrayList<CoursesPosition.DataBean> list = new ArrayList<>();
    private Map<String, String> map;
    private Map<String, String> header;
    private View view;
    private SwipeBackControllerLayout layout;

    @Override
    protected void onResume() {
        super.onResume();
        PageAdapter.isStart=false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        view =  findViewById(R.id.background);
        init();
        LoadData();
        layout = (SwipeBackControllerLayout) findViewById(R.id.position);
        layout.init(this);
    }

    private void init() {
        //获取一个Api实例
        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_imooc)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverInterface = retrofit.create(MyServerInterface.class);
        loaddingDialog = new LoadingDialog(this);
        //初始化ViewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new PageAdapter(this, list, view);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final int screenWidth   = ImageUtils.getScreenWidth(Position.this);
                final int screenHeight  = ImageUtils.getScreenHeight(Position.this);

                Glide.with(Position.this).load(list.get(position).getPath_pic_fmt()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 60;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                                resource.compress(Bitmap.CompressFormat.PNG, 1, output);//把bitmap100%高质量压缩 到 output对象里
//                                resource.recycle();//自由选择是否进行回收
                                byte[] result = output.toByteArray();//转换成功了
                                try {
                                    output.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Bitmap image= BitmapFactory.decodeByteArray(result,0,result.length,options);
                                final Bitmap newImg2 = Blur.fastblur(Position.this,image, 2);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.setBackgroundDrawable(new BitmapDrawable(newImg2));
                                    }
                                });


                            }
                        }).start();

                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取豆瓣Api数据
     */
    private void LoadData() {
        loaddingDialog.show();
        serverInterface.postProgram(getMap(), getHeader()).enqueue(new Callback<CoursesPosition>() {
            @Override
            public void onResponse(Call<CoursesPosition> call, Response<CoursesPosition> response) {
                list.addAll(response.body().getData());
                viewPager.setAdapter(adapter);
                loaddingDialog.cancel();
            }

            @Override
            public void onFailure(Call<CoursesPosition> call, Throwable t) {
                loaddingDialog.cancel();
            }
        });
    }

    private Map<String, String> getMap() {
        if (map == null)
            map = new HashMap<>();
        map.clear();

        map.put("typeid", "1");
        map.put("token", "922fbe1b4313f6329be6d106493b4406");
        map.put("uid", String.valueOf(2932390));
        return map;
    }

    private Map<String, String> getHeader() {
        if (header == null)
            header = new HashMap<>();
        header.put("User-Agent", "mukewang/");
        return header;
    }
}
