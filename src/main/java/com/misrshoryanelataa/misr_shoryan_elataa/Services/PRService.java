package com.misrshoryanelataa.misr_shoryan_elataa.Services;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.CampaignEntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Models.PREntity;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.CampaignRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.PrRepo;
import com.misrshoryanelataa.misr_shoryan_elataa.Repos.UserRepo;

@Service
public class PRService {

    @Autowired
    private PrRepo prRepo;

    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    public PREntity createCampaign(CampaignEntity campaign,int prId) {
       PREntity pr= prRepo.findById(prId).orElseThrow(() -> new RuntimeException("PR not found"));;
        pr.setCampaign(campaign);
        campaign.setPr(pr);
        campaignRepo.save(campaign);
        sendEmailToUsers(campaign);
        return prRepo.save(pr);

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

public void sendEmailToUsers(CampaignEntity campaign) {
    prRepo.findAll().stream()
            .flatMap(pr -> pr.getCampaigns().stream())
            .filter(c -> c.getId() == campaign.getId())
            .findFirst()
            .ifPresent(existingCampaign -> {
                existingCampaign.setDate(campaign.getDate());
                existingCampaign.setDescription(campaign.getDescription());
                existingCampaign.setLocation(campaign.getLocation());
                existingCampaign.setIsActivated(campaign.getIsActivated());
                campaignRepo.save(existingCampaign);
                prRepo.save(existingCampaign.getPr());
            });

    userRepo.findAll().forEach(user -> {
        if (user.getEmail() == null || user.getEmail().isBlank()) return;

        String body = "Dear " + user.getName() + ",\n\n" +
                "We would like to inform you about the latest updates on our campaigns.\n\n" +
                "Description: " + campaign.getDescription() + "\n" +
                "Location: " + campaign.getLocation() + "\n" +
                "Date: " + campaign.getDate() + "\n\n" +
                "Please visit our website for more details.\n\n" +
                "Best regards,\n" +
                "Misr Shoryan Elataa Team";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("misrshoryanelataa@gmail.com");
        message.setSubject("Update on Campaigns");
        message.setText(body);

        mailSender.send(message);
    });
}
}