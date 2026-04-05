package com.misrshoryanelataa.misr_shoryan_elataa.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.CampaignEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.PREntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.CampaignRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.PrRepo;

@Service
public class PRService {
  
    @Autowired
    private PrRepo prRepo;

    @Autowired
    private CampaignRepo campaignRepo;

    public PREntity createCampaign(CampaignEntity campaign) {
     PREntity prEntity = new PREntity();
     prEntity.setCampaign(campaign);
     campaignRepo.save(campaign);
     return prRepo.save(prEntity);
    }

    public List<CampaignEntity> getAllCampaigns() {
        return prRepo.findAll().stream()
        .flatMap(pr -> pr.getCampaigns().stream())
        .collect(Collectors.toList());      
    }

    public void deleteCampaign(int id) {
        prRepo.findAll().stream()
        .flatMap(pr -> pr.getCampaigns().stream())
        .filter(campaign -> campaign.getId() == id)
        .findFirst()
        .ifPresent(campaign -> {
            PREntity pr = campaign.getPr();
            pr.getCampaigns().remove(campaign);
            campaignRepo.delete(campaign);
            prRepo.save(pr);
           
        });
    }

    public void updateCampaign(int id, CampaignEntity campaign) {
        prRepo.findAll().stream()
        .flatMap(pr -> pr.getCampaigns().stream())
        .filter(c -> c.getId() == id)
        .findFirst()
        .ifPresent(existingCampaign -> {
            existingCampaign.setDate(campaign.getDate());
            existingCampaign.setDescription(campaign.getDescription());
            existingCampaign.setLocation(campaign.getLocation());
            existingCampaign.setIsActivated(campaign.getIsActivated());
            campaignRepo.save(existingCampaign);
            prRepo.save(existingCampaign.getPr());
        });
    }
}