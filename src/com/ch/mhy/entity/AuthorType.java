package com.ch.mhy.entity;

import java.io.Serializable;
import java.util.Date;

public class AuthorType implements Serializable {
    // 精品类别ID
    private Integer typeId;

    // 类别名称
    private String typeName;

    // 类别图片
    private String typePic;

    // 排序
    private Integer type_seq;

    // 创建日期
    private Date createTime;

    private Integer typeNum;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypePic() {
        return typePic;
    }

    public void setTypePic(String typePic) {
        this.typePic = typePic;
    }

    public Integer getType_seq() {
        return type_seq;
    }

    public void setType_seq(Integer type_seq) {
        this.type_seq = type_seq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(Integer typeNum) {
        this.typeNum = typeNum;
    }


}