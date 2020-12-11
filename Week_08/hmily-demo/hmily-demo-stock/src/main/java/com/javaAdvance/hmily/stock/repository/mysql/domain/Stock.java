package com.javaAdvance.hmily.stock.repository.mysql.domain;

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
 * @since 2020-12-10
 */
public class Stock extends Model<Stock> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 当前品类总库存
     */
    private Integer stockAll;

    /**
     * 当前品类已占用库存
     */
    private Integer stockPre;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockAll() {
        return stockAll;
    }

    public void setStockAll(Integer stockAll) {
        this.stockAll = stockAll;
    }

    public Integer getStockPre() {
        return stockPre;
    }

    public void setStockPre(Integer stockPre) {
        this.stockPre = stockPre;
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
        return "Stock{" +
        "id=" + id +
        ", goodsId=" + goodsId +
        ", stockAll=" + stockAll +
        ", stockPre=" + stockPre +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
