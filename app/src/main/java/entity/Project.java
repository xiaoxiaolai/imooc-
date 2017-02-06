package entity;

import java.util.List;

/**
 * Created by xiao on 2016/9/9.
 */

public class Project {
    /**
     * status : 1
     * data : [{"id":252,"name":"战斗伊始\u2014\u2014SpringMVC","description":"SpringMVC为我们提供了一个基于组件和松耦合的MVC实现框架。在本步骤中，我们将学习SpringMVC的基本使用，以及如何在项目中应用SpringMVC拦截器。\n","seqid":"1","parent_id":"0","course":[{"id":47,"title":"Spring MVC起步","img":"http://img.mukewang.com/570765d90001bf1406000338-240-180.jpg"},{"id":498,"title":"Spring MVC拦截器","img":"http://img.mukewang.com/55f147640001ae6406000338-240-180.jpg"}]},{"id":253,"name":"转战阵地\u2014\u2014Spring","description":"Spring是为解决企业应用程序开发复杂性而创建的一个Java开源框架。本步骤将介绍Spring的特点、IoC和AOP的概念及应用，以及Spring事务管理的内容。","seqid":"2","parent_id":"0","course":[{"id":196,"title":"Spring入门篇","img":"http://img.mukewang.com/5704d2a90001bb1b06000338-240-180.jpg"},{"id":478,"title":"Spring事务管理","img":"http://img.mukewang.com/5704d19a0001d38406000338-240-180.jpg"}]},{"id":254,"name":"再下一城\u2014\u2014MyBatis","description":"MyBatis 是支持普通SQL查询、存储过程和高级映射的优秀持久层框架。本步骤将通过案例介绍MyBatis的常见应用。\n","seqid":"3","parent_id":"0","course":[{"id":154,"title":"通过自动回复机器人学Mybatis---基础版","img":"http://img.mukewang.com/5704a3fd0001310306000338-240-180.jpg"},{"id":260,"title":"通过自动回复机器人学Mybatis---加强版","img":"http://img.mukewang.com/5704a4220001c6fa06000338-240-180.jpg"}]},{"id":255,"name":"终极目标\u2014\u2014整合案例","description":"本步骤介绍如何使用SpringMVC+Spring+MyBatis实现秒杀系统案例。","seqid":"4","parent_id":"0","course":[{"id":587,"title":"Java高并发秒杀API之业务分析与DAO层","img":"http://img.mukewang.com/572bff580001c7a006000338-240-180.jpg"},{"id":631,"title":"Java高并发秒杀API之Service层","img":"http://img.mukewang.com/5733de380001615d06000338-240-180.jpg"},{"id":630,"title":"Java高并发秒杀API之web层","img":"http://img.mukewang.com/5733e1c00001516f06000338-240-180.jpg"},{"id":632,"title":"Java高并发秒杀API之高并发优化","img":"http://img.mukewang.com/5733e336000141f806000338-240-180.jpg"}]}]
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1473411614236
     */

    private String status;
    private int errorCode;
    private String errorDesc;
    private long timestamp;
    /**
     * id : 252
     * name : 战斗伊始——SpringMVC
     * description : SpringMVC为我们提供了一个基于组件和松耦合的MVC实现框架。在本步骤中，我们将学习SpringMVC的基本使用，以及如何在项目中应用SpringMVC拦截器。

     * seqid : 1
     * parent_id : 0
     * course : [{"id":47,"title":"Spring MVC起步","img":"http://img.mukewang.com/570765d90001bf1406000338-240-180.jpg"},{"id":498,"title":"Spring MVC拦截器","img":"http://img.mukewang.com/55f147640001ae6406000338-240-180.jpg"}]
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
        private String description;
        private String seqid;
        private String parent_id;
        /**
         * id : 47
         * title : Spring MVC起步
         * img : http://img.mukewang.com/570765d90001bf1406000338-240-180.jpg
         */

        private List<CourseBean> course;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSeqid() {
            return seqid;
        }

        public void setSeqid(String seqid) {
            this.seqid = seqid;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public List<CourseBean> getCourse() {
            return course;
        }

        public void setCourse(List<CourseBean> course) {
            this.course = course;
        }

        public static class CourseBean {
            private int id;
            private String title;
            private String img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
