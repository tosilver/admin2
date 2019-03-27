package co.b4pay.admin.entity;


import co.b4pay.admin.entity.base.BaseEntity;

import java.util.Date;

public class JobTrade extends BaseEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3050115515232457103L;

    private String content;
    private String notifyUrl;
    private Date execTime;
    private Integer count;
    private Integer status;

    public JobTrade() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobTrade{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", execTime=" + execTime +
                ", count=" + count +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
