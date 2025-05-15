package org.dataverse.marketplace.service;

import java.io.IOException;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.payload.ExternalToolManifestDTO;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ExternalToolVersionService {

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;

    public ExternalToolVersion getToolVersionById(Long versionId) {
        return externalToolVersionRepo.findById(versionId).orElse(null);
    }

    public ExternalToolVersion updateToolVersion(ExternalToolVersion externalToolVersion) {
        return externalToolVersionRepo.save(externalToolVersion);
    }

    public Long getVersionCount(Long toolId) {
        return externalToolVersionRepo.countByExternalToolId(toolId);
    }

    @Transactional
    public void deleteToolVersion(ExternalToolVersion externalToolVersion) throws IOException {
        externalToolVersionRepo.delete(externalToolVersion);
    }

    @Transactional
    public ExternalToolVersion addToolVersion(AddVersionRequest externalToolVersion, ExternalTool externalTool)
            throws IOException {
        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setExternalTool(externalTool);
        newVersion.setVersion(externalToolVersion.getVersion());
        newVersion.setReleaseNote(externalToolVersion.getReleaseNote());
        newVersion.setDataverseMinVersion(externalToolVersion.getDvMinVersion());        
        externalToolVersionRepo.save(newVersion);

        return newVersion;
    }


    @Transactional
    public ExternalToolVersion updateVersionManifest(ExternalToolVersion externalToolVersion,
            ExternalToolManifestDTO manifestDTO) throws IOException {

        manifestDTO.convertDTOtoEntity(externalToolVersion);
        return externalToolVersionRepo.save(externalToolVersion);

    }

}
