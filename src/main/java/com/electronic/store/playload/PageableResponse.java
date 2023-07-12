package com.electronic.store.playload;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse <T>{

    private List<T>  content;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;


}
