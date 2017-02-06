package entity;

import java.util.List;

/**
 * Created by xiao on 2016/8/29.
 */

public class ChapterInfo {
    /**
     * status : 1
     * data : [{"chapter":{"id":3243,"name":"导学","cid":670,"seq":1},"media":[{"id":11971,"name":"案例演示","type":1,"chapter_seq":1,"chapter_id":3243,"media_seq":1,"media_url":"http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/L.mp4","media_down_url":"http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/M.mp4","duration":163840,"last_time":164000,"last_date":1469298184000,"share_url":"http://www.imooc.com/video/11971","have_ques":0,"media_size":6787880,"media_down_size":9430984,"status":2}]},{"chapter":{"id":3244,"name":"代码编写","cid":670,"seq":2},"media":[{"id":11972,"name":"权限申请，代码导入","type":1,"chapter_seq":2,"chapter_id":3244,"media_seq":1,"media_url":"http://v1.mukewang.com/c9e21c7c-676a-41e9-8f99-96132c64723d/L.mp4","media_down_url":"http://v1.mukewang.com/c9e21c7c-676a-41e9-8f99-96132c64723d/M.mp4","duration":800340,"last_time":0,"last_date":1472407764000,"share_url":"http://www.imooc.com/video/11972","have_ques":0,"media_size":33074276,"media_down_size":46319763,"status":1},{"id":11973,"name":"定位回顾","type":1,"chapter_seq":2,"chapter_id":3244,"media_seq":2,"media_url":"http://v1.mukewang.com/5ddebe35-f0bb-4cd5-8b12-ca73a8df5628/L.mp4","media_down_url":"http://v1.mukewang.com/5ddebe35-f0bb-4cd5-8b12-ca73a8df5628/M.mp4","duration":586110,"last_time":0,"last_date":0,"share_url":"http://www.imooc.com/video/11973","have_ques":0,"media_size":23703546,"media_down_size":33126662,"status":0},{"id":11974,"name":"移动标志到指定位置","type":1,"chapter_seq":2,"chapter_id":3244,"media_seq":3,"media_url":"http://v1.mukewang.com/904ee782-6174-4c00-b572-77d7fb537fc4/L.mp4","media_down_url":"http://v1.mukewang.com/904ee782-6174-4c00-b572-77d7fb537fc4/M.mp4","duration":458990,"last_time":0,"last_date":0,"share_url":"http://www.imooc.com/video/11974","have_ques":0,"media_size":18865293,"media_down_size":26560505,"status":0},{"id":11975,"name":"路线导航","type":1,"chapter_seq":2,"chapter_id":3244,"media_seq":4,"media_url":"http://v1.mukewang.com/c27b4798-fc6e-432e-a9b3-aa4b45394634/L.mp4","media_down_url":"http://v1.mukewang.com/c27b4798-fc6e-432e-a9b3-aa4b45394634/M.mp4","duration":1056750,"last_time":0,"last_date":0,"share_url":"http://www.imooc.com/video/11975","have_ques":0,"media_size":43575477,"media_down_size":61051502,"status":0},{"id":11976,"name":"起始点调整","type":1,"chapter_seq":2,"chapter_id":3244,"media_seq":5,"media_url":"http://v1.mukewang.com/44895720-c61d-40ea-94d2-0f9660f8f2bb/L.mp4","media_down_url":"http://v1.mukewang.com/44895720-c61d-40ea-94d2-0f9660f8f2bb/M.mp4","duration":825000,"last_time":0,"last_date":0,"share_url":"http://www.imooc.com/video/11976","have_ques":0,"media_size":33519345,"media_down_size":46887931,"status":0}]},{"chapter":{"id":3245,"name":"总结","cid":670,"seq":3},"media":[{"id":11977,"name":"课程梳理","type":1,"chapter_seq":3,"chapter_id":3245,"media_seq":1,"media_url":"http://v1.mukewang.com/528c5954-2a68-4a1b-9f1b-8dc793d9a362/L.mp4","media_down_url":"http://v1.mukewang.com/528c5954-2a68-4a1b-9f1b-8dc793d9a362/M.mp4","duration":96110,"last_time":0,"last_date":0,"share_url":"http://www.imooc.com/video/11977","have_ques":0,"media_size":4089114,"media_down_size":5740273,"status":0}]}]
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1472407799417
     * hash : 1608290151442631
     */

    private int status;
    private int errorCode;
    private String errorDesc;
    private long timestamp;
    private String hash;
    /**
     * chapter : {"id":3243,"name":"导学","cid":670,"seq":1}
     * media : [{"id":11971,"name":"案例演示","type":1,"chapter_seq":1,"chapter_id":3243,"media_seq":1,"media_url":"http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/L.mp4","media_down_url":"http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/M.mp4","duration":163840,"last_time":164000,"last_date":1469298184000,"share_url":"http://www.imooc.com/video/11971","have_ques":0,"media_size":6787880,"media_down_size":9430984,"status":2}]
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3243
         * name : 导学
         * cid : 670
         * seq : 1
         */

        private ChapterBean chapter;
        /**
         * id : 11971
         * name : 案例演示
         * type : 1
         * chapter_seq : 1
         * chapter_id : 3243
         * media_seq : 1
         * media_url : http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/L.mp4
         * media_down_url : http://v1.mukewang.com/64077ac1-b826-4d9a-b0f8-d36aa7ce1d5f/M.mp4
         * duration : 163840
         * last_time : 164000
         * last_date : 1469298184000
         * share_url : http://www.imooc.com/video/11971
         * have_ques : 0
         * media_size : 6787880
         * media_down_size : 9430984
         * status : 2
         */

        private List<MediaBean> media;

        public ChapterBean getChapter() {
            return chapter;
        }

        public void setChapter(ChapterBean chapter) {
            this.chapter = chapter;
        }

        public List<MediaBean> getMedia() {
            return media;
        }

        public void setMedia(List<MediaBean> media) {
            this.media = media;
        }

        public static class ChapterBean {
            private int id;
            private String name;
            private int cid;
            private int seq;

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

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }
        }

        public static class MediaBean {
            private int id;
            private String name;
            private int type;
            private int chapter_seq;
            private int chapter_id;
            private int media_seq;
            private String media_url;
            private String media_down_url;
            private int duration;
            private int last_time;
            private long last_date;
            private String share_url;
            private int have_ques;
            private int media_size;
            private int media_down_size;
            private int status;

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

            public int getChapter_seq() {
                return chapter_seq;
            }

            public void setChapter_seq(int chapter_seq) {
                this.chapter_seq = chapter_seq;
            }

            public int getChapter_id() {
                return chapter_id;
            }

            public void setChapter_id(int chapter_id) {
                this.chapter_id = chapter_id;
            }

            public int getMedia_seq() {
                return media_seq;
            }

            public void setMedia_seq(int media_seq) {
                this.media_seq = media_seq;
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

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getLast_time() {
                return last_time;
            }

            public void setLast_time(int last_time) {
                this.last_time = last_time;
            }

            public long getLast_date() {
                return last_date;
            }

            public void setLast_date(long last_date) {
                this.last_date = last_date;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public int getHave_ques() {
                return have_ques;
            }

            public void setHave_ques(int have_ques) {
                this.have_ques = have_ques;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
