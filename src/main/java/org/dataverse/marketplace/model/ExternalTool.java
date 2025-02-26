package org.dataverse.marketplace.model;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "external_tool")
@PrimaryKeyJoinColumn(name = "mkt_item_id")
public class ExternalTool extends MarketplaceItem implements Serializable {

    @OneToMany(mappedBy = "externalTool")
    List<ExternalToolVersion> externalToolVersions;

    public List<ExternalToolVersion> getExternalToolVersions() {
        return this.externalToolVersions;
    }

    public void setExternalToolVersions(List<ExternalToolVersion> externalToolVersions) {
        this.externalToolVersions = externalToolVersions;
    }
}