package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.List;

import org.dataverse.marketplace.model.ExternalTool;
import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.payload.AddToolRequest;
import org.dataverse.marketplace.payload.ExternalToolDTO;
import org.dataverse.marketplace.repository.ExternalToolManifestRepo;
import org.dataverse.marketplace.repository.ExternalToolRepo;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

@Service
public class ExternalToolService {

    
    @Autowired
    private ExternalToolRepo externalToolRepo;

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;

    @Autowired
    private ExternalToolManifestRepo externalToolManifestRepo;
    
    public List<ExternalTool> getAllTools() {
        return externalToolRepo.findAll();
    }

    @Transactional
    public ExternalToolDTO addTool(AddToolRequest externalTool) throws IOException {


        ExternalTool newTool = new ExternalTool();
        newTool.setName(externalTool.getName());
        newTool.setDescription(externalTool.getDescription());
        
        externalToolRepo.save(newTool);

        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForIten(newTool.getId());
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;
        
        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(newTool.getId());
        newVersion.setReleaseNote(externalTool.getReleaseNote());
        newVersion.setDataverseMinVersion(externalTool.getDvMinVersion());
        newVersion.setVersion(externalTool.getVersion());

        externalToolVersionRepo.save(newVersion);


        for (MultipartFile manifest : externalTool.getJsonData()) {
            
            
            ExternalToolManifest newManifest = new ExternalToolManifest();
        
            System.out.println(externalTool.getJsonData().size());
            
            
            newManifest.setMkItemId(newTool.getId());
            newManifest.setVersionId(newVersion.getId());
            newManifest.setJsonData(manifest.getBytes());

            //CHANGE THIS!!!
            newManifest.setMimeType(externalTool.getJsonData().getContentType());
            
            externalToolManifestRepo.save(newManifest);
        }

        
        
        


        return null;
    }

}
