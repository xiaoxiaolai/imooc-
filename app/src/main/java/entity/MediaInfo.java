package entity;

/**
 * Created by xiao on 2016/8/25.
 */

public class MediaInfo {

    /**
     * status : 1
     * data : {"media":{"id":12622,"name":"课程介绍","type":1,"share_url":"http://www.imooc.com/wap/learn/?id=712","chapter_id":3461,"chapter_seq":1,"media_seq":1,"last_time":0,"last_date":0,"status":0,"have_ques":0,"duration":135700,"media_size":4747803,"media_down_size":7046380,"media_url":"http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/L.mp4","media_down_url":"http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/M.mp4"},"course":{"id":712,"is_follow":0}}
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1472071181935
     */

    private int status;
    /**
     * media : {"id":12622,"name":"课程介绍","type":1,"share_url":"http://www.imooc.com/wap/learn/?id=712","chapter_id":3461,"chapter_seq":1,"media_seq":1,"last_time":0,"last_date":0,"status":0,"have_ques":0,"duration":135700,"media_size":4747803,"media_down_size":7046380,"media_url":"http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/L.mp4","media_down_url":"http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/M.mp4"}
     * course : {"id":712,"is_follow":0}
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
        /**
         * id : 12622
         * name : 课程介绍
         * type : 1
         * share_url : http://www.imooc.com/wap/learn/?id=712
         * chapter_id : 3461
         * chapter_seq : 1
         * media_seq : 1
         * last_time : 0
         * last_date : 0
         * status : 0
         * have_ques : 0
         * duration : 135700
         * media_size : 4747803
         * media_down_size : 7046380
         * media_url : http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/L.mp4
         * media_down_url : http://v1.mukewang.com/ea728861-0d9e-4605-a674-5e509576b286/M.mp4
         */

        private MediaBean media;
        /**
         * id : 712
         * is_follow : 0
         */

        private CourseBean course;

        public MediaBean getMedia() {
            return media;
        }

        public void setMedia(MediaBean media) {
            this.media = media;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public static class MediaBean {
            private int id;
            private String name;
            private int type;
            private String share_url;
            private int chapter_id;
            private int chapter_seq;
            private int media_seq;
            private int last_time;
            private int last_date;
            private int status;
            private int have_ques;
            private int duration;
            private int media_size;
            private int media_down_size;
            private String media_url;
            private String media_down_url;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public int getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(int chapter_id) {
                this.chapter_id = chapter_id;
            }

            public int getChapter_seq() {
                return chapter_seq;
            }

            public void setChapter_seq(int chapter_seq) {
                this.chapter_seq = chapter_seq;
            }

            public int getMedia_seq() {
                return media_seq;
            }

            public void setMedia_seq(int media_seq) {
                this.media_seq = media_seq;
            }

            public int getLast_time() {
                return last_time;
            }

            public void setLast_time(int last_time) {
                this.last_time = last_time;
            }

            public int getLast_date() {
                return last_date;
            }

            public void setLast_date(int last_date) {
                this.last_date = last_date;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getHave_ques() {
                return have_ques;
            }

            public void setHave_ques(int have_ques) {
                this.have_ques = have_ques;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getMedia_size() {
                return media_size;
            }

            public void setMedia_size(int media_size) {
                this.media_size = media_size;
            }

            public int getMedia_down_size() {
                return media_down_size;
            }

            public void setMedia_down_size(int media_down_size) {
                this.media_down_size = media_down_size;
            }

            public String getMedia_url() {
                return media_url;
            }

            public void setMedia_url(String media_url) {
                this.media_url = media_url;
            }

            public String getMedia_down_url() {
                return media_down_url;
            }

            public void setMedia_down_url(String media_down_url) {
                this.media_down_url = media_down_url;
            }
        }

        public static class CourseBean {
            private int id;

            private int is_follow;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }



            public int getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(int is_follow) {
                this.is_follow = is_follow;
            }
        }
    }
}
