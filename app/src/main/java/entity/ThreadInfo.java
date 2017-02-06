package entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * create by luoxiaoke on 2016/4/29 15:22.
 * use for 线程信息类
 */
@DatabaseTable(tableName ="ThreadInfo" )
public class ThreadInfo {
    @DatabaseField(columnName = "_id" , generatedId = true)
    private int id;
    @DatabaseField(columnName = "url" , dataType = DataType.STRING)
    private String url;
    @DatabaseField(columnName = "start" , dataType = DataType.LONG)
    private long start;
    @DatabaseField(columnName = "end" , dataType = DataType.LONG)
    private long end;
    @DatabaseField(columnName = "finish" , dataType = DataType.LONG)
    private long finish;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, long start, long end, long finish) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finish = finish;
    }
    public ThreadInfo( String url, long start, long end, long finish) {

        this.url = url;
        this.start = start;
        this.end = end;
        this.finish = finish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }
}
