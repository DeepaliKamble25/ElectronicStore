package com.electronic.store.helper;

import com.electronic.store.dto.UserDto;
import com.electronic.store.model.User;
import com.electronic.store.playload.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
//U==entity V==Dto
    public static<U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type){
        List<U> entity = page.getContent();
        List<V> userDtos = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse pageableResponse=new PageableResponse();
        pageableResponse.setContent(userDtos);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());
        return pageableResponse;

    }
}
