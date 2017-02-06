package httpClient;

import java.util.Map;

import entity.ChapterInfo;
import entity.CourseInfor;
import entity.CourseListModel;
import entity.CoursesDetails;
import entity.CoursesPosition;
import entity.MediaInfo;
import entity.Project;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by steven on 16/3/29.
 */
public interface MyServerInterface {
    ///////////////////////////////////////////////////////////////////////////
    // GET网络请求方式
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 作用：GET请求最简单的写法,无Path参数和Query参数
     */
    @GET("article/list/latest?page=1")
    Call<ResponseBody> getLatestString();

//    /**
//     * 作用：GET请求，指定Path参数和Query参数
//     */
//    @GET("article/list/{type}?")
//    Call<QiushiModel> getInfoList(@Path("type") String type, @Query("page") int page);


    /**
     * 作用：GET请求提交数据
     *
     * @return
     */
    @GET("MyWeb/RegServlet")
    Call<ResponseBody> getRegInfo(@QueryMap Map<String, String> map);

    /**
     * 作用：GET请求,指定URL参数
     */
    @GET
    Call<CourseListModel> getInfoList(@Url String urlString);

    /**
     * 作用：访问网络，获取网络返回数据
     *
     * @return
     */
    @GET("http://img.265g.com/userup/1201/201201071126534773.jpg")
    Call<ResponseBody> getNetworkData();

    /**
     * 作用：访问网络，获取网络返回数据
     *
     * @return
     */
    @GET
    Call<ResponseBody> getNetworkData(@Url String urlString);

    /**
     * 作用：访问网络，下载大文件。
     * 默认情况下，Retrofit在处理结果前会将服务器端的Response全部读进内存。
     * 如果服务器端返回的是一个非常大的文件，则容易oom。
     *
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> getNetworkDataAsync(@Url String urlString, @HeaderMap Map<String, String> header);

    ///////////////////////////////////////////////////////////////////////////
    // POST网络请求方式
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 作用：post网络请求，向服务器提交表单域数据
     *
     * @param username
     * @param password
     * @param age
     * @return
     */
    @FormUrlEncoded
    @POST("MyWeb/RegServlet")
    Call<ResponseBody> postFormFields(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("age") String age);

    /**
     * 作用：post网络请求，向服务器提交表单域数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("MyWeb/RegServlet")
    Call<ResponseBody> postFormFieldMap(@FieldMap Map<String, String> map);

    /**
     * 作用：POST网络请求，上传单个文件，上传后的文件名称已经被指定
     *
     * @param
     * @return
     */
    @Multipart
    @POST("MyWeb/UploadServlet")
    Call<ResponseBody> postUploadFile(@Part("uploadfile\";filename=\"myuploadimg.png") RequestBody requestBody);

    /**
     * 作用：POST网络请求，上传多个文件，同时上传表单域数据
     *
     * @param
     * @return
     */
    @POST("MyWeb/UPloadServlet")
    Call<ResponseBody> postUploadFilesMultipartBody(@Body MultipartBody multipartBody);

    /**
     * 作用：POST网络请求，上传多个文件，同时上传表单域数据
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("courselist_ver2")
    Call<CourseListModel> postCourselist_ver2(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    /***
     * http://www.imooc.com/api3/getmediainfo_ver2
     */
    @FormUrlEncoded
    @POST("getmediainfo_ver2")
    Call<MediaInfo> postMediainfo_ver2(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    /***
     * http://www.imooc.com/api3/getcpinfo_ver2
     */
    @FormUrlEncoded
    @POST("getcpinfo_ver2")
    Call<ChapterInfo> postCpinfo_ver2(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    /***
     * http://www.imooc.com/api3/getcourseinfor
     */
    @FormUrlEncoded
    @POST("getcourseinfor")
    Call<CourseInfor> postCourseInfor(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    //    http://www.imooc.com/api3/program
    @FormUrlEncoded
    @POST("program")
    Call<CoursesPosition> postProgram(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    //    http://www.imooc.com/api3/programtype
    @FormUrlEncoded
    @POST("programtype")
    Call<Project> postProgramtype(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    //    http://www.imooc.com/api3/programtype
    @FormUrlEncoded
    @POST("programtype")
    Call<CoursesDetails> postCoursesDetails(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);

    //    http://www.imooc.com/api3/coursetype
    @FormUrlEncoded
    @POST("coursetype")
    Call<CoursesDetails> postCourseType(@FieldMap Map<String, String> fieldMap, @HeaderMap Map<String, String> header);


}
