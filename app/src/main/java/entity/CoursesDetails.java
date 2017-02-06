package entity;

import java.util.List;

/**
 * Created by xiao on 2016/9/9.
 */

public class CoursesDetails {
    /**
     * status : 1
     * data : [{"id":194,"name":"入门必备","description":"Linux入门知识。","seqid":"1","parent_id":"0","children":[{"id":195,"name":"Linux基础","description":".环境安装\n.常用命令\n.shell入门\n.VIM文本编辑器\n.磁盘管理\n.用户管理","seqid":"2","parent_id":"194","courses":[{"id":175,"title":" Linux达人养成计划 I","img":"http://img.mukewang.com/57035f110001a57906000338-240-180.jpg"},{"id":111,"title":"Linux 达人养成计划 II","img":"http://img.mukewang.com/57035f580001a91806000338-240-180.jpg"}]}]},{"id":196,"name":"常用操作与服务","description":"掌握Linux常用操作和服务。","seqid":"3","parent_id":"0","children":[{"id":197,"name":"网络管理","description":".网络管理基础\n.Linux网络管理命令\n.常用远程连接工具","seqid":"4","parent_id":"196","courses":[{"id":258,"title":"Linux网络管理","img":"http://img.mukewang.com/5704cf8c00012c9006000338-240-180.jpg"}]},{"id":198,"name":"软件安装","description":".rpm命令安装\n.yum在线安装\n.源码包安装\n","seqid":"5","parent_id":"196","courses":[{"id":447,"title":"Linux软件安装管理","img":"http://img.mukewang.com/559f35ad00017e0006000338-240-180.jpg"}]},{"id":199,"name":"权限管理","description":".基本权限\n.特殊权限","seqid":"6","parent_id":"196","courses":[{"id":481,"title":"Linux权限管理之基本权限","img":"http://img.mukewang.com/55dd95220001ca4d06000338-240-180.jpg"},{"id":499,"title":"Linux权限管理之特殊权限","img":"http://img.mukewang.com/55f147b400019c4d25001408-240-180.jpg"}]},{"id":200,"name":"服务管理","description":".服务管理\n.进程管理\n.系统管理\n.crontab","seqid":"7","parent_id":"196","courses":[{"id":216,"title":"Linux中的计划任务\u2014Crontab","img":"http://img.mukewang.com/544f0d9500017d6306000338-240-180.jpg"},{"id":537,"title":"Linux服务管理","img":"http://img.mukewang.com/563b13700001ebf906000338-240-180.jpg"},{"id":583,"title":"Linux系统管理","img":"http://img.mukewang.com/5697799a0001e99006000338-240-180.jpg"}]}]},{"id":201,"name":"运维自动化必备","description":".shell基础\n.shell实战","seqid":"8","parent_id":"0","children":[{"id":202,"name":"shell基础","description":".变量\n.运算符\n.环境变量\n.正则表达式\n.条件判断与流程控制","seqid":"9","parent_id":"201","courses":[{"id":336,"title":"shell编程之变量","img":"http://img.mukewang.com/55063ec30001774b06000338-240-180.jpg"},{"id":355,"title":"shell编程之运算符","img":"http://img.mukewang.com/5704ce7700019f8706000338-240-180.jpg"},{"id":361,"title":"shell编程之环境变量配置文件","img":"http://img.mukewang.com/55237dcc0001128c06000338-240-180.jpg"},{"id":378,"title":"shell编程之正则表达式","img":"http://img.mukewang.com/5704cea700017b9e06000338-240-180.jpg"},{"id":408,"title":"shell编程之条件判断与流程控制","img":"http://img.mukewang.com/5704ce550001ce3606000338-240-180.jpg"}]},{"id":203,"name":"shell实战","description":".主控脚本\n.系统信息及运行状态\n.Mysql与Ngnix服务监控\n.日志","seqid":"10","parent_id":"201","courses":[{"id":522,"title":"Shell典型应用之主控脚本实现","img":"http://img.mukewang.com/562da5410001b40a06000338-240-180.jpg"},{"id":538,"title":"Shell典型应用之系统信息及运行状态获取","img":"http://img.mukewang.com/5653bd1d0001801906000338-240-180.jpg"},{"id":539,"title":"Shell典型应用之nginx和mysql应用状态分析","img":"http://img.mukewang.com/567a67fb00013a2b06000338-240-180.jpg"},{"id":540,"title":"Shell典型应用之应用日志分析","img":"http://img.mukewang.com/563b150200019d4d06000338-240-180.jpg"}]}]},{"id":204,"name":"企业级应用","description":".安全应用\n.网络服务","seqid":"11","parent_id":"0","children":[{"id":205,"name":"安全应用","description":".扫描技术\n.iptables","seqid":"12","parent_id":"204","courses":[{"id":344,"title":"Linux系统扫描技术及安全防范","img":"http://img.mukewang.com/5510c2c500016e9e06000338-240-180.jpg"},{"id":389,"title":"用iptables搭建一套强大的安全防护盾","img":"http://img.mukewang.com/554b17ee0001e87206000338-240-180.jpg"}]},{"id":206,"name":"网络服务","description":".DNS","seqid":"13","parent_id":"204","courses":[{"id":634,"title":"Linux智能DNS服务搭建之Bind服务","img":"http://img.mukewang.com/56e617dd0001583d06000338-240-180.jpg"}]},{"id":207,"name":"服务搭建","description":".LAMP服务\n","seqid":"14","parent_id":"204","courses":[{"id":170,"title":"在Ubuntu Server下搭建LAMP环境","img":"http://img.mukewang.com/53ed6e6d0001122a06000338-240-180.jpg"}]}]}]
     * errorCode : 1000
     * errorDesc : 成功
     * timestamp : 1473425650103
     */

    private String status;
    private int errorCode;
    private String errorDesc;
    private long timestamp;
    /**
     * id : 194
     * name : 入门必备
     * description : Linux入门知识。
     * seqid : 1
     * parent_id : 0
     * children : [{"id":195,"name":"Linux基础","description":".环境安装\n.常用命令\n.shell入门\n.VIM文本编辑器\n.磁盘管理\n.用户管理","seqid":"2","parent_id":"194","courses":[{"id":175,"title":" Linux达人养成计划 I","img":"http://img.mukewang.com/57035f110001a57906000338-240-180.jpg"},{"id":111,"title":"Linux 达人养成计划 II","img":"http://img.mukewang.com/57035f580001a91806000338-240-180.jpg"}]}]
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
         * id : 195
         * name : Linux基础
         * description : .环境安装
         .常用命令
         .shell入门
         .VIM文本编辑器
         .磁盘管理
         .用户管理
         * seqid : 2
         * parent_id : 194
         * courses : [{"id":175,"title":" Linux达人养成计划 I","img":"http://img.mukewang.com/57035f110001a57906000338-240-180.jpg"},{"id":111,"title":"Linux 达人养成计划 II","img":"http://img.mukewang.com/57035f580001a91806000338-240-180.jpg"}]
         */

        private List<ChildrenBean> children;

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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private int id;
            private String name;
            private String description;
            private String seqid;
            private String parent_id;
            /**
             * id : 175
             * title :  Linux达人养成计划 I
             * img : http://img.mukewang.com/57035f110001a57906000338-240-180.jpg
             */

            private List<CoursesBean> courses;

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

            public List<CoursesBean> getCourses() {
                return courses;
            }

            public void setCourses(List<CoursesBean> courses) {
                this.courses = courses;
            }

            public static class CoursesBean {
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
}
