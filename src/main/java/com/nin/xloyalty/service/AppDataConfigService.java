package com.nin.xloyalty.service;

import com.nin.xloyalty.model.AppDataConfig;
import com.nin.xloyalty.repository.AppDataConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppDataConfigService {
    @Autowired
    private AppDataConfigRepository appDataConfigRepository;

    @Autowired
    public AppDataConfigService(AppDataConfigRepository appDataConfigRepository) {
        this.appDataConfigRepository = appDataConfigRepository;
    }

    public AppDataConfig findByIsActive(){
        return appDataConfigRepository.findByIsActive(true);
    }

    public AppDataConfig findByAppDataConfigId(Long Id){
        return appDataConfigRepository.findByAppDataConfigId(Id);
    }
    public void createOrUpdateAppDataConfig(AppDataConfig appDataConfig){
         appDataConfigRepository.save(appDataConfig);
         return;
    }
}
