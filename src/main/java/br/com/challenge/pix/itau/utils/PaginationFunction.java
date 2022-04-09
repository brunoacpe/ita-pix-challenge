package br.com.challenge.pix.itau.utils;

import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationFunction {


    public static Page<PixRegisterResponse> of(List<PixRegisterResponse> list, Integer page, Integer size){
        Pageable paging = PageRequest.of(page,size);
        int start = Math.min((int)paging.getOffset(), list.size());
        int end = Math.min((start + paging.getPageSize()), list.size());
        Page<PixRegisterResponse> pageReturn = new PageImpl<>(list.subList(start, end), paging, list.size());
        return pageReturn;
    }
}
