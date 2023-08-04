package com.electronic.store.model;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class BaseEntity {

    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
}
