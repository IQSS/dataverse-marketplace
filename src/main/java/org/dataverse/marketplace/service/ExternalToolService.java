package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.MarketplaceItem;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.repository.ExternalToolRepo;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.dataverse.marketplace.repository.MarketplaceItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalToolService {

    
    @Autowired
    private ExternalToolRepo externalToolRepo;

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;
    
    public List<ExternalTool> getAllTools() {
        return externalToolRepo.findAll();
    }

    public ExternalToolDTO addTool(AddToolRequest externalTool) throws IOException {


        ExternalTool newTool = new ExternalTool();
        newTool.setName(externalTool.getName());
        newTool.setDescription(externalTool.getDescription());
        externalToolRepo.save(newTool);

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setExternalTool(newTool);
        newVersion.setReleaseNote(externalTool.getReleaseNote());
        newVersion.setDataverseMinVersion(externalTool.getDvMinVersion());
        newVersion.setVersion(externalTool.getVersion());
        newVersion.setJsonData(externalTool.getJsonData().getBytes());

        externalToolVersionRepo.save(newVersion);

        return null;
    }

}
