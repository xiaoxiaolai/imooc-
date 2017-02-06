package entity;

/**
 * Created by xiao on 2016/8/31.
 */

public class CourseInfor {
    /**
     * status : 1
     * data : {"id":679,"name":"基于SSH实现员工管理系统之案例实现篇","pic":"http://img.mukewang.com/57a1ca250001b2f206000338-533-300.jpg","desc":"SSH框架整合案例之实现篇！","is_learned":1,"company_id":0,"numbers":6559,"duration":10800000,"finished":1,"is_follow":0,"max_chapter_seq":4,"max_media_seq":4,"last_time":"1472527211","chapter_seq":"4","media_seq":"4"}
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1472584068008
     */

    private int status;
    /**
     * id : 679
     * name : 基于SSH实现员工管理系统之案例实现篇
     * pic : http://img.mukewang.com/57a1ca250001b2f206000338-533-300.jpg
     * desc : SSH框架整合案例之实现篇！
     * is_learned : 1
     * company_id : 0
     * numbers : 6559
     * duration : 10800000
     * finished : 1
     * is_follow : 0
     * max_chapter_seq : 4
     * max_media_seq : 4
     * last_time : 1472527211
     * chapter_seq : 4
     * media_seq : 4
     */

    private DataBean data;
    private int errorCode;
    private String errorDesc;
    private long timestamp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        private int id;
        private String name;
        private String pic;
        private String desc;
        private int is_learned;
        private int company_id;
        private int numbers;
        private int duration;
        private int finished;
        private int is_follow;
        private int max_chapter_seq;
        private int max_media_seq;
        private String last_time;
        private String chapter_seq;
        private String media_seq;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIs_learned() {
            return is_learned;
        }

        public void setIs_learned(int is_learned) {
            this.is_learned = is_learned;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public int getNumbers() {
            return numbers;
        }

        public void setNumbers(int numbers) {
            this.numbers = numbers;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getFinished() {
            return finished;
        }

        public void setFinished(int finished) {
            this.finished = finished;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public int getMax_chapter_seq() {
            return max_chapter_seq;
        }

        public void setMax_chapter_seq(int max_chapter_seq) {
            this.max_chapter_seq = max_chapter_seq;
        }

        public int getMax_media_seq() {
            return max_media_seq;
        }

        public void setMax_media_seq(int max_media_seq) {
            this.max_media_seq = max_media_seq;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getChapter_seq() {
            return chapter_seq;
        }

        public void setChapter_seq(String chapter_seq) {
            this.chapter_seq = chapter_seq;
        }

        public String getMedia_seq() {
            return media_seq;
        }

        public void setMedia_seq(String media_seq) {
            this.media_seq = media_seq;
        }
    }
}
