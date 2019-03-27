package co.b4pay.admin.entity.base;

import co.b4pay.admin.common.util.DateUtil;
import co.b4pay.admin.controller.Utils.Utils;

import java.util.Date;

/**
 * 基类
 *
 * @author YK
 * @version $Id: BaseEntity.java, v 0.1 2015年3月21日 上午11:35:09 YK Exp $
 */
public abstract class BaseEntity implements java.io.Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1733293242024528231L;

    /**
     * 实体编号（唯一标识）
     */
    protected String id;                                     // 唯一标识ID
    protected Integer status;                                 // 状态标记（-1：删除； 0：初始草稿； >=1：其他正常状态；）
    protected String remark;                                 // 备注
    protected String createBy;                               // 创建者
    protected Date createTime;                             // 创建时间
    protected String updateBy;                               // 更新者
    protected Date updateTime;                             // 更新时间

    public BaseEntity() {
        try {

            Date times = DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now()));
//            System.out.println(Utils.now().toString());
//            System.out.println(DateUtil.yMdhms.format(Utils.now()));
//            System.out.println(DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now().toString())));
            createTime = times;
            updateTime = times;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
    }

    public BaseEntity(String id) {
        this.id = id;
    }

    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public final String getRemark() {
        return remark;
    }

    public final void setRemark(String remark) {
        this.remark = remark;
    }

    public final String getCreateBy() {
        return createBy;
    }

    public final void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public final Date getCreateTime() {
        return createTime;
    }

    public final void setCreateTime(Date createTime) {
        try {
            this.createTime = DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now()));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }

        this.createTime = createTime;
    }

    public final String getUpdateBy() {
        return updateBy;
    }

    public final void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public final Date getUpdateTime() {
        return updateTime;
    }

    public final void setUpdateTime(Date updateTime) {
        try {
            this.updateTime = DateUtil.YMdhms.parse(DateUtil.YMdhms.format(Utils.now()));
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        this.updateTime = updateTime;
    }

    /**
     * 插入之前执行方法，子类实现
     */
    public void preInsert() {
        if (createTime == null) {
            this.createTime = new Date();
        }
        if (updateTime == null) {
            this.updateTime = createTime;
        }
    }

    /**
     * 更新之前执行方法，子类实现
     */
    public void preUpdate() {
        this.updateTime = new Date();
    }

}