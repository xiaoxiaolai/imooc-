package entity;

import java.util.List;

/**
 * Created by xiao on 2016/9/9.
 */

public class CoursesPosition {
    /**
     * status : 1
     * data : [{"id":32,"name":"Web前端工程师","study_persons":146358,"description":"随着互联网的发展速度迅猛，前端工程师职业越来越火热，想学习Web前端技能吗 ? 该路径从基础知识到实战案例演练，一步步带您快速掌握如何搭建网站静态页面、开发网站交互特效，为您打开WEB前端工程师大门。还在等什么？快来学习吧!","courses":28,"status":0,"path_pic_fmt":"http://img.mukewang.com/56551e2800014fa909600720-960-720.jpg","share":"http://www.imooc.com/course/programdetail/pid/32"},{"id":33,"name":"Android工程师","study_persons":69120,"description":"Android作为目前最热门的技术之一，工作前景一片大好！不是不想学，只是不知从何学起？没关系！由小慕来带领大家学习从最基本的Java语言基础，到各种Android应用案例，为你的求职做足准备，助你成为一名合格的Android开发工程师！","courses":18,"status":1,"path_pic_fmt":"http://img.mukewang.com/56551e450001afcd09600720-960-720.jpg","share":"http://www.imooc.com/course/programdetail/pid/33"},{"id":34,"name":"PHP工程师","study_persons":47198,"description":"想成为一个合格PHPer却不知从何下手？此求职宝典能一解您的燃眉之急。此宝典从最基本的PHP语法基础开始，步步深入，一直到框架应用，都做出了详细的讲解，助你迈出重要一步。","courses":27,"status":0,"path_pic_fmt":"http://img.mukewang.com/56551e5d0001a66209600720-960-720.jpg","share":"http://www.imooc.com/course/programdetail/pid/34"},{"id":31,"name":"Java工程师","study_persons":104079,"description":"想成为Java攻城狮？不知道该如何入门？表捉急，本计划从最基本的Java语言基础、各种常用工具到Java Web基础以及框架应用，迈出成为优秀的Java工程师的重要一步。","courses":40,"status":1,"path_pic_fmt":"http://img.mukewang.com/56551e6700018b0c09600720-960-720.jpg","share":"http://www.imooc.com/course/programdetail/pid/31"},{"id":45,"name":"Linux运维工程师","study_persons":33037,"description":"本计划专为立志修炼成为一名Linux运维工程师的小伙伴量身打造！零基础入门，从系统安装、基本命令，到常用操作，再到最后的企业级实战，步步为营，带你实现Linux运维的梦想！","courses":22,"status":0,"path_pic_fmt":"http://img.mukewang.com/565681830001c9a309600720-960-720.jpg","share":"http://www.imooc.com/course/programdetail/pid/45"}]
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1473392651481
     */

    private String status;
    private int errorCode;
    private String errorDesc;
    private long timestamp;
    /**
     * id : 32
     * name : Web前端工程师
     * study_persons : 146358
     * description : 随着互联网的发展速度迅猛，前端工程师职业越来越火热，想学习Web前端技能吗 ? 该路径从基础知识到实战案例演练，一步步带您快速掌握如何搭建网站静态页面、开发网站交互特效，为您打开WEB前端工程师大门。还在等什么？快来学习吧!
     * courses : 28
     * status : 0
     * path_pic_fmt : http://img.mukewang.com/56551e2800014fa909600720-960-720.jpg
     * share : http://www.imooc.com/course/programdetail/pid/32
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String name;
        private int study_persons;
        private String description;
        private int courses;
        private int status;
        private String path_pic_fmt;
        private String share;

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

        public int getStudy_persons() {
            return study_persons;
        }

        public void setStudy_persons(int study_persons) {
            this.study_persons = study_persons;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCourses() {
            return courses;
        }

        public void setCourses(int courses) {
            this.courses = courses;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPath_pic_fmt() {
            return path_pic_fmt;
        }

        public void setPath_pic_fmt(String path_pic_fmt) {
            this.path_pic_fmt = path_pic_fmt;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }
    }
}
