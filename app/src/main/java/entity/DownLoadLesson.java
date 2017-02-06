package entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by xiao on 2016/8/30.
 */
@DatabaseTable(tableName ="downLoadlesson" )
public class DownLoadLesson implements Serializable{
    public void setId_(long id_) {
        this.id_ = id_;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @DatabaseField(columnName = "_id" , generatedId = true)
    private long id_ ;
    @DatabaseField(columnName = "lessonName" , dataType = DataType.STRING)
    private String lessonName;
    @DatabaseField(columnName = "num" , dataType = DataType.INTEGER)
    private int num;

    public int getDownloadnum() {
        return downloadnum;
    }

    public void setDownloadnum(int downloadnum) {
        this.downloadnum = downloadnum;
    }

    @DatabaseField(columnName = "downloadnum" , dataType = DataType.INTEGER ,defaultValue = "0")
    private int downloadnum;
    @DatabaseField(columnName = "size" , dataType = DataType.LONG)
    private long size;
    @ForeignCollectionField(eager = true)
    private ForeignCollection<DownLoadChapterInfo> chapterInfos ;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @DatabaseField(columnName = "cid" , dataType = DataType.INTEGER)
    private int cid;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @DatabaseField(columnName = "pic" , dataType = DataType.STRING)
    private String pic;

    public DownLoadLesson() {
    }

    public ForeignCollection<DownLoadChapterInfo> getChapterInfos() {
        return chapterInfos;
    }

    public void setChapterInfos(ForeignCollection<DownLoadChapterInfo> chapterInfos) {
        this.chapterInfos = chapterInfos;
    }

    public Long getId_() {
        return id_;
    }

    public void setId_(Long id_) {
        this.id_ = id_;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
