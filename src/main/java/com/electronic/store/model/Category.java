package com.electronic.store.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="categories")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_title")
    private String title;

    @Column(name="category_description")
    private String description;

    @Column(name="category_cover_Image_name")
    private String coverImage;










}
