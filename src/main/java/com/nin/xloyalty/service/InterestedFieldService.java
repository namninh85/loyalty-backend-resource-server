package com.nin.xloyalty.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nin.xloyalty.repository.InterestedFieldRepository;

@Service
public class InterestedFieldService {
    @Autowired
    private InterestedFieldRepository interestedFieldRepository;

    @Autowired
    public InterestedFieldService(InterestedFieldRepository interestedFieldRepository) {
        this.interestedFieldRepository = interestedFieldRepository;
    }

    public List<Object[]> findByListId(String Id){
        List<Integer> listIds = new ArrayList<>();
        if(StringUtils.isEmpty(Id)) return new ArrayList<Object[]>();
        Arrays.asList(Id.split(",")).forEach(id -> {
            listIds.add(Integer.parseInt(id));
        });
        return interestedFieldRepository.findByListId(listIds);
    }
}
