package entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by xiao on 2016/8/30.
 */
@DatabaseTable(tableName ="DownLoadChapterInfo" )
public class DownLoadChapterInfo implements Serializable {
    @DatabaseField(columnName = "_id" , generatedId = true)
    private long id_ ;
    @DatabaseField(columnName = "chapterName" , dataType = DataType.STRING)
    private String chapterName;
    @DatabaseField(columnName = "isread" , dataType = DataType.BOOLEAN)
    private boolean isread;
    @DatabaseField(columnName = "size" , dataType = DataType.LONG)
    private long size;
    @DatabaseField(foreign = true, foreignAutoRefresh = true,columnName="downLoadLesson_id")
    private DownLoadLesson downLoadLesson;
    @DatabaseField(columnName = "order" , dataType = DataType.LONG)
    private long order;
    @DatabaseField(columnName = "isDownLoad" , dataType = DataType.BOOLEAN,defaultValue = "false")
    private boolean isDownLoad;
    @DatabaseField(columnName = "start" , dataType = DataType.LONG)
    private long start;
    @DatabaseField(columnName = "end" , dataType = DataType.LONG)
    private long end;
    @DatabaseField(columnName = "finish" , dataType = DataType.LONG,defaultValue="0")
    private long finish;
    @DatabaseField(columnName = "url" , dataType = DataType.STRING)
    private String url;
    @DatabaseField(columnName = "isPause" , dataType = DataType.INTEGER,defaultValue = "0")
    private int isPause=0;

    public int getIsPause() {
        return isPause;
    }

    public void setIsPause(int isPause) {
        this.isPause = isPause;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    public boolean isDownLoad() {
        return isDownLoad;
    }

    public void setDownLoad(boolean downLoad) {
        isDownLoad = downLoad;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }



    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId_(long id_) {
        this.id_ = id_;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isread() {
        return isread;
    }

    public void setIsread(boolean isread) {
        this.isread = isread;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public DownLoadLesson getDownLoadLesson() {
        return downLoadLesson;
    }

    public void setDownLoadLesson(DownLoadLesson downLoadLesson) {
        this.downLoadLesson = downLoadLesson;
    }


    public DownLoadChapterInfo() {
    }




    public Long getId_() {
        return id_;
    }

    public void setId_(Long id_) {
        this.id_ = id_;
    }

    public Boolean getIsread() {
        return isread;
    }

    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "DownLoadChapterInfo{" +
                "id_=" + id_ +
                ", chapterName='" + chapterName + '\'' +
                ", isread=" + isread +
                ", size=" + size +
                ", downLoadLesson=" + downLoadLesson +
                ", order=" + order +
                ", isDownLoad=" + isDownLoad +
                ", start=" + start +
                ", end=" + end +
                ", finish=" + finish +
                ", url='" + url + '\'' +
                '}';
    }




}
