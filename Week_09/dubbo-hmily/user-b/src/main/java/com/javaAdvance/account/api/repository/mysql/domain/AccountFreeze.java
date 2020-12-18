package com.javaAdvance.account.api.repository.mysql.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hitopei
 * @since 2020-12-16
 */
public class AccountFreeze extends Model<AccountFreeze> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userId;

    private BigDecimal freezeRmb;

    private BigDecimal freezeUsd;

    private Date createTime;

    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getFreezeRmb() {
        return freezeRmb;
    }

    public void setFreezeRmb(BigDecimal freezeRmb) {
        this.freezeRmb = freezeRmb;
    }

    public BigDecimal getFreezeUsd() {
        return freezeUsd;
    }

    public void setFreezeUsd(BigDecimal freezeUsd) {
        this.freezeUsd = freezeUsd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AccountFreeze{" +
        "id=" + id +
        ", userId=" + userId +
        ", freezeRmb=" + freezeRmb +
        ", freezeUsd=" + freezeUsd +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
