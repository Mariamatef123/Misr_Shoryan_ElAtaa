package com.misrshoryanelataa.misr_shoryan_elataa.Models;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class PREntity extends StaffEntity {
    boolean isAdmin;

    @OneToMany(mappedBy = "pr")
    private List<CampaignEntity> campaigns;

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public PREntity() {

    }

    public void setCampaign(CampaignEntity campaign) {
        this.campaigns.add(campaign);
    }

    public List<CampaignEntity> getCampaigns() {
        return campaigns;
    }

}