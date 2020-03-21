package com.nin.xloyalty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nin.xloyalty.model.SpecialOffer;
import com.nin.xloyalty.repository.SpecialOfferRepository;

@Service
public class SpecialOfferService {

    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    public SpecialOfferService(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;       
    }

    public List<SpecialOffer> findAllActive() {
    	return specialOfferRepository.findByIsActive(true);
    }
}
