package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.xiao.cui.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import httpClient.HttpURLConnHelper;
import httpClient.MyJsonHelper;

//AppCompatActivity
public class H5Activity extends Activity {
    private Context mContext = this;
    private static final String TAG = "H5Activity";
    private WebView webView_h5;
    public static final String BASE_URL = "http://123.57.44.15:8085/webblue/";
    // 考试试卷
    public static final String EXAM_PAPER_URL = "webblue/testQuestionsAction_findTestQuestionsByPaper.action?paperNo=";// 试卷编号：20140608001
    public static final int EXAM_TIME = 2700;
    // 提交考试试卷
    public static final String EXAM_SUBMIT_URL = "webblue/testScoresAction_saveScores.action?";
    private WebSettings webSettings = null;
    private Handler handler = new Handler();
    private List<Map<String, String>> listResult = null;
    private String paperNo = "20140608001";
    private String userid = "123";
    private String paperTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView_h5 = new WebView(this);
        //webView_h5 = (WebView) findViewById(R.id.webView_h5);

        // 设置WebView背景颜色
        //webView_h5.setBackgroundColor(Color.rgb(255, 255, 0));

        //设置WebView的一些缩放功能点
        webView_h5.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView_h5.setHorizontalScrollBarEnabled(false);


        //设置WebView可触摸放大缩小
        webView_h5.getSettings().setSupportZoom(true);
        webView_h5.getSettings().setBuiltInZoomControls(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        webView_h5.getSettings().setUseWideViewPort(true);

        // 设置WebView初始化尺寸，参数为百分比
        webView_h5.setInitialScale(100);

        // 获取WebSettings对象
        webSettings = webView_h5.getSettings();

        // 设置支持访问文件
        webSettings.setAllowFileAccess(true);

        //允许JS执行
        webView_h5.getSettings().setJavaScriptEnabled(true);
        webView_h5.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        H5Activity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("提示：");
                builder.setMessage(message);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });

        // 点击超链接后不弹出浏览器窗口，而在WebView控件中加载URL
        webView_h5.setWebViewClient(new WebViewClient() {
            @Override
            // 目标网页在当前WebView中显示，不会打开系统浏览器来加载目标网页
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 网页开始加载时，设置图片阻塞
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 网页加载完毕后，关闭图片阻塞
                super.onPageFinished(view, url);
                webSettings.setBlockNetworkImage(false);
            }
        });

        // 将一个Java对象绑定到一个Javascript对象中。
        webView_h5.addJavascriptInterface(new MyJavascriptInterface(), "myJsInterface");

        // 阻止网络图片加載
        webView_h5.getSettings().setBlockNetworkImage(true);


        //加载“首页”
        webView_h5.loadUrl("file:///android_asset/yuzhi/index.html");

        setContentView(webView_h5);
    }

    class MyJavascriptInterface {

        @JavascriptInterface
        public void enterExam() {
            // 加载网络数据
            loadExamData();
        }

        @JavascriptInterface
        public void clickSubmitForResult(String _result) {
            final String result = _result;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> myAnswer = getResultMap(result);
                    int score = 0;
                    if (myAnswer != null) {
                        score = getScore(myAnswer, listResult);
                    }
                    Log.i("H5Activity", "---->answer:" + myAnswer);

                    // submitScore(BASE_URL + EXAM_SUBMIT_URL, userid,
                    // score,
                    // paperNo, getCurrentDate());

                    // 弹出得分对话框，点击“知道了”返回上级页面
                    buildConfirmDialog(mContext, score);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView_h5.canGoBack()) {
            webView_h5.goBack();
        }
    }

    private String makeHTML(List<Map<String, String>> list) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html class='ui-viewport-640 mobile ks-webkit600 ks-webkit' lang='zh-CN'>");
        sb.append("<head>");
        sb.append("<meta charset='utf-8'>");
        sb.append("<meta name='HandheldFriendly' content='True'>");
        sb.append("<meta name='MobileOptimized' content='320'>");
        sb.append("<meta name='format-detection' content='telephone=no'>");
        sb.append("<meta http-equiv='cleartype' content='on'>");
        sb.append("<title>育知同创网上入学考试</title>");
        sb.append("<meta name='viewport' content='width=640, user-scalable=no, target-densitydpi=device-dpi'>");
        sb.append("<script src='file:///android_asset/yuzhi/script/jquery-1.8.2.js'></script>");
        sb.append("<link href='file:///android_asset/yuzhi/style/aio.min.css' rel='stylesheet' type='text/css'>");
        sb.append("<link href='file:///android_asset/yuzhi/style/animate.min.css' rel='stylesheet' type='text/css'>");
        sb.append("<script src='file:///android_asset/yuzhi/style/aio.min.js'></script>");
        sb.append("<link href='file:///android_asset/yuzhi/style/cmbChina.css' rel='stylesheet' type='text/css'>");
        sb.append("<div class='swiper-exam page-exam' data-hash='exam' style=''>");

        sb.append("<p class='page-exam clock-title'>计时：<span id='timeShow'></span></p>");
        for (int i = 0; i < list.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");

            int type = Integer.parseInt(list.get(i).get("questionsType"));
            String questionType = "";
            switch (type) {
                case 1:
                    questionType = "【单选】";
                    break;
                case 2:
                    questionType = "【判断】";
                    break;
                case 3:
                    questionType = "【多选】";
                    break;
            }
            sb.append(questionType);
            sb.append(list.get(i).get("questionsStems"));
            sb.append("<br/>");
            String[] arrQuestions = list.get(i).get("questions").split("\n");
            switch (type) {
                case 1:
                    for (int j = 0; j < arrQuestions.length; j++) {
                        sb.append("<input type='radio' value='");
                        sb.append(j);
                        sb.append("' name='question_" + i + "'>");
                        sb.append(arrQuestions[j]);
                        sb.append("<br/>");
                    }
                    break;
                case 2:
                    sb.append("<input type='radio' value='0' name='question_" + i + "'>对");
                    sb.append("<input type='radio' value='1' name='question_" + i + "'>错");
                    sb.append("<br/>");
                    break;
                case 3:
                    for (int j = 0; j < arrQuestions.length; j++) {
                        sb.append("<input type='checkbox' value='");
                        sb.append(j);
                        sb.append("' name='question_" + i + "'>");
                        sb.append(arrQuestions[j]);
                        sb.append("<br/>");
                    }
                    break;
            }
            sb.append("<br/>");
        }
        sb.append("<div class='submit'><input type='submit'  id='submit' onclick='submitForm();' value='提交' ></div></div>");
        // Log.i("ExamActivity_html", "--->" + sb.toString());

        return sb.toString();
    }

    private String makeJS(List<Map<String, String>> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        // 以下是表单提交js
        sb.append("var result='';");
        sb.append("var timer;");
        sb.append("var total=" + EXAM_TIME + ";");
        sb.append("function getFormValue(count) {");
        sb.append("result='';");
        sb.append("for (var i=0; i< count; i++){");
        sb.append("var arr = document.getElementsByName('question_'+i);");
        sb.append("for(var j=0; j<arr.length; j++) {");
        sb.append("if(arr[j].checked) {");
        sb.append("result += i +':' + document.getElementsByName('question_' + i)[j].value + ';';");
        sb.append("}}}");
        sb.append("}");
        sb.append("function submitForm() {getFormValue(" + list.size() + ");");
        sb.append("window.myJsInterface.clickSubmitForResult(result);");
        sb.append("clearTimeout(timer);");
        sb.append("document.getElementById('submit').disabled=true;");
        sb.append("}");
        // 以下是倒计时js
        sb.append("function timeCounter(elemID) {");
        sb.append("var obj = document.getElementById(elemID);");
        sb.append("var h = total/3600 < 10 ? ('0' + parseInt(total/3600)) : parseInt(total/3600);");
        sb.append("var m = (total-h*3600)/60 < 10 ? ('0' + parseInt((total-h*3600)/60)) : parseInt((total-h*3600)/60);");
        sb.append("var s = (total%60) < 10 ? ('0' + total%60) : total%60;");
        sb.append("if (obj!=null){obj.innerHTML = h + ':' + m + ':' + s;");
        sb.append("total--;}");
        sb.append("if(total < 0) {");
        sb.append("submitForm();");
        sb.append("clearTimeout(timer);");
        sb.append("}");
        sb.append("}");
        sb.append("timer = window.setInterval(function () { timeCounter('timeShow'); }, 1000);");
        sb.append("</script>");
        //Log.i("ExamActivity_js", "--->" + sb.toString());
        return sb.toString();
    }

    // 加载考试卷数据
    private void loadExamData() {
        // 通过网络下载文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] result = HttpURLConnHelper.loadByteFromURL(BASE_URL + EXAM_PAPER_URL + paperNo);
                    listResult = MyJsonHelper
                            .parseExamPaperJsonToList(new String(result));
                    final String htmlResult = makeJS(listResult) + makeHTML(listResult);

                    // 将子线程中获取到的内容返回到主线程
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            webView_h5.loadDataWithBaseURL(null, htmlResult, "text/html", "utf-8", null);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 获取答题结果
    private Map<String, String> getResultMap(String str) {
        Map<String, String> map = null;
        if (!TextUtils.isEmpty(str)) {
            map = new TreeMap<String, String>();
            String[] arrResult = str.split(";");
            for (int i = 0; i < arrResult.length; i++) {
                String key = arrResult[i].substring(0,
                        arrResult[i].lastIndexOf(":"));
                int valueInt = Integer.parseInt(arrResult[i]
                        .substring(arrResult[i].lastIndexOf(":") + 1));
                String value = "";
                switch (valueInt) {
                    case 0:
                        value = "A";
                        break;
                    case 1:
                        value = "B";
                        break;
                    case 2:
                        value = "C";
                        break;
                    case 3:
                        value = "D";
                        break;
                    default:
                        break;
                }
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + value);
                } else {
                    map.put(key, value);
                }
            }
            return map;
        }
        return null;
    }

    // 获取考试得分
    private int getScore(Map<String, String> myAnswer,
                         List<Map<String, String>> trueAnswer) {
        int score = 0;
        for (Map.Entry<String, String> entry : myAnswer.entrySet()) {
            if (entry.getValue().equals(
                    trueAnswer.get(Integer.parseInt(entry.getKey())).get(
                            "questionsResult"))) {
                score += Integer.parseInt(trueAnswer.get(
                        Integer.parseInt(entry.getKey())).get("scores"));
            }
        }
        return score;
    }

    // 提交考试结果到服务器
    private void submitScore(final String urlString, String username,
                             int score, String paperRef, String scoresTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("testScoresvo.userRef=");
        sb.append(username);
        sb.append("&testScoresvo.scores=");
        sb.append(score);
        sb.append("&testScoresvo.paperRef=");
        sb.append(paperRef);
        sb.append("&testScoresvo.scoresTime=");
        sb.append(scoresTime);
        final String params = sb.toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] result = HttpURLConnHelper.loadByteFromURL(urlString
                            + params);
                    Log.i("MainActivity_submitUrl", "--->" + urlString + params);

                    final Map<String, String> mapResult = MyJsonHelper
                            .parseSubmitExamJsonToMap(new String(result));
                    // 将子线程中获取到的内容返回到主线程
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (mapResult.get("result").equals("true")) {
                                    Toast.makeText(H5Activity.this, "考试成绩保存成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                }
                            } catch (Exception e) {
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 生成确定对话框
    protected void buildConfirmDialog(Context context, int score) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        String infoString = "考试结束，您的分数是：" + score + "相关结果请注意查收邮件";
        builder.setMessage(infoString);

        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    // 获取当前日期
    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    // 创建进度对话框
    protected ProgressDialog createProgressDialog() {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        return pDialog;
    }
}
