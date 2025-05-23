package org.dataverse.marketplace.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.CascadeType;


@Entity
@PrimaryKeyJoinColumn(name = "mkt_item_id")
public class ExternalTool extends MarketplaceItem {

    @OneToMany(mappedBy = "externalTool", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ExternalToolVersion> externalToolVersions;

    public List<ExternalToolVersion> getExternalToolVersions() {
        return this.externalToolVersions;
    }

    public void setExternalToolVersions(List<ExternalToolVersion> externalToolVersions) {
        this.externalToolVersions = externalToolVersions;
    }
}