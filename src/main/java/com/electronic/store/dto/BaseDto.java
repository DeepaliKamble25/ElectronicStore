package com.electronic.store.dto;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Date;
@Data
@Embeddable
public class BaseDto {

    private String createdBy;
    private String updatedBy;
    private Date createdDate;
    private Date updatedDate;
}
