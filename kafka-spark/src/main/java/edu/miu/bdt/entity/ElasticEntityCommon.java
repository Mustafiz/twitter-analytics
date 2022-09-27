package edu.miu.bdt.entity;

import edu.miu.bdt.util.DateFormat;
import edu.miu.bdt.util.Utils;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Data
@MappedSuperclass
public abstract class ElasticEntityCommon {
    @Id
    protected String id;
    protected String createTime;
    protected Long createTimeInMs;
    protected String createdBy;
    protected String editTime;
    protected Long editTimeInMs;
    protected String editedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrePersist
    public void beforeSave() {
        if (this.createTime == null) {
            this.createTime = Utils.toDateStringFromDate(new Date(), DateFormat.YYYY_MM_DDTHH_MM_SS_SSS_DASH);
        }
    }

    @PreUpdate
    public void beforeEdit() {
        this.editTime = Utils.toDateStringFromDate(new Date(), DateFormat.YYYY_MM_DDTHH_MM_SS_SSS_DASH);
        ;
    }

    @PreRemove
    public void beforeDelete() {
        this.editTime = Utils.toDateStringFromDate(new Date(), DateFormat.YYYY_MM_DDTHH_MM_SS_SSS_DASH);
        ;
    }
}
