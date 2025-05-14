package org.dataverse.marketplace.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dataverse.marketplace.model.ContentType;
import org.dataverse.marketplace.model.ExternalToolManifest;
import org.dataverse.marketplace.model.ExternalToolType;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.model.QueryParameter;
import org.dataverse.marketplace.model.VersionMetadata;
import org.dataverse.marketplace.payload.AddVersionRequest;
import org.dataverse.marketplace.payload.ExternalToolManifestDTO;
import org.dataverse.marketplace.repository.ExternalToolManifestRepo;
import org.dataverse.marketplace.repository.ExternalToolVersionRepo;
import org.dataverse.marketplace.repository.VersionMetadataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ExternalToolVersionService {

    @Autowired
    private ExternalToolVersionRepo externalToolVersionRepo;

    @Autowired
    private VersionMetadataRepo versionMetadataRepo;

    @Autowired
    private ExternalToolManifestRepo externalToolManifestRepo;

    public ExternalToolVersion updateToolVersion(ExternalToolVersion externalToolVersion) {
        return externalToolVersionRepo.save(externalToolVersion);
    }

    public Integer getVersionCount(Integer toolId) {
        return externalToolVersionRepo.getVersionCount(toolId);
    }

    @Transactional
    public void deleteToolVersion(ExternalToolVersion externalToolVersion) throws IOException {
        externalToolVersionRepo.delete(externalToolVersion);
    }

    @Transactional
    public ExternalToolVersion addToolVersion(AddVersionRequest externalToolVersion, Integer toolId)
            throws IOException {

        VersionMetadata versionMetadata = new VersionMetadata();
        versionMetadata.setVersion(externalToolVersion.getVersion());
        versionMetadata.setReleaseNote(externalToolVersion.getReleaseNote());
        versionMetadata.setDataverseMinVersion(externalToolVersion.getDvMinVersion());
        versionMetadataRepo.save(versionMetadata);

        Integer externalToolNextVersion = externalToolVersionRepo.getNextIdForItemVersion(toolId);
        externalToolNextVersion = externalToolNextVersion == null ? 1 : externalToolNextVersion + 1;

        ExternalToolVersion newVersion = new ExternalToolVersion();
        newVersion.setId(externalToolNextVersion);
        newVersion.setMkItemId(toolId);
        newVersion.setVersionMetadata(versionMetadata);
        externalToolVersionRepo.save(newVersion);

        return newVersion;
    }

    @Transactional
    public ExternalToolManifest addVersionManifest(ExternalToolVersion externalToolVersion,
            ExternalToolManifestDTO manifestDTO) throws IOException {

        Long externalToolManifestId = externalToolManifestRepo.getNextIdForManifest(
                externalToolVersion.getMkItemId(), externalToolVersion.getId());

        externalToolManifestId = externalToolManifestId == null ? 1 : externalToolManifestId + 1;

        ExternalToolManifest newManifest = new ExternalToolManifest();
        newManifest.setManifestId(externalToolManifestId);
        newManifest.setMkItemId(Long.valueOf(externalToolVersion.getMkItemId()));
        newManifest.setVersionId(Long.valueOf(externalToolVersion.getId()));

        convertDTOtoEntity(manifestDTO, newManifest);
        return externalToolManifestRepo.save(newManifest);

    }

    public ExternalToolManifestDTO updateExternalToolManifest(
            ExternalToolManifestDTO externalToolManifestDTO,
            Integer toolId,
            Integer versionId,
            Long manifestId) throws IOException {

        ExternalToolManifest manifest = externalToolManifestRepo.findById(manifestId).get();
        convertDTOtoEntity(externalToolManifestDTO, manifest);
        externalToolManifestRepo.save(manifest);

        return new ExternalToolManifestDTO(manifest);
    }

    public void deleteToolManifest(Integer toolId,
            Integer versionId,
            Long manifestId) throws IOException {

        ExternalToolManifest manifest = externalToolManifestRepo.findById(manifestId).get();
        externalToolManifestRepo.delete(manifest);
    }

    private void convertDTOtoEntity(ExternalToolManifestDTO manifestDTO,
            ExternalToolManifest manifest) {

        manifest.setDisplayName(manifestDTO.getDisplayName());
        manifest.setDescription(manifestDTO.getDescription());
        manifest.setScope(manifestDTO.getScope());
        manifest.setToolUrl(manifestDTO.getToolUrl());
        manifest.setToolName(manifestDTO.getToolName());
        manifest.setHttpMethod(manifestDTO.getHttpMethod());

        Set<ContentType> existingContentTypes = manifest.getContentTypes();

        if (existingContentTypes != null) {
            existingContentTypes.clear();
        } else {
            existingContentTypes = new HashSet<>();
            manifest.setContentTypes(existingContentTypes);
        }
        if (manifestDTO.getContentTypes() != null) {
            for (String contentType : manifestDTO.getContentTypes()) {
                if (StringUtils.isNotBlank(contentType)) {
                    ContentType newContentType = new ContentType();
                    newContentType.setManifest(manifest);
                    newContentType.setContentType(contentType);
                    existingContentTypes.add(newContentType);
                }
            }
        }
        // we are uploading a manifest that uses the single content type
        if (existingContentTypes.isEmpty() && StringUtils.isNotBlank(manifestDTO.getContentType())) {
            ContentType newContentType = new ContentType();
            newContentType.setManifest(manifest);
            newContentType.setContentType(manifestDTO.getContentType());
            existingContentTypes.add(newContentType);   
        }
       
        Set<ExternalToolType> existingTypes = manifest.getExternalToolTypes();

        if (existingTypes != null) {
            existingTypes.clear();
        } else {
            existingTypes = new HashSet<>();
            manifest.setExternalToolTypes(existingTypes);
        }
        if (manifestDTO.getTypes() != null) {
            for (String type : manifestDTO.getTypes()) {
                ExternalToolType newType = new ExternalToolType();
                newType.setManifest(manifest);
                newType.setType(type);
                existingTypes.add(newType);
            }
        }

        Set<QueryParameter> existingQueryParameters = manifest.getQueryParameters();

        if (existingQueryParameters != null) {
            existingQueryParameters.clear();  
        } else {
            existingQueryParameters = new HashSet<>();
            manifest.setQueryParameters(existingQueryParameters);
        }      
        if (manifestDTO.getToolParameters() != null &&
                manifestDTO.getToolParameters().getQueryParameters() != null
                && !manifestDTO.getToolParameters().getQueryParameters().isEmpty()) {
            for (Map<String, String> qpMap : manifestDTO.getToolParameters().getQueryParameters()) {
                QueryParameter qp = new QueryParameter();
                qp.setManifest(manifest);
                qp.setKey(qpMap.keySet().iterator().next());
                qp.setValue(qpMap.get(qp.getKey()));
                existingQueryParameters.add(qp);
            }
        }

        /*
         * if (manifestDTO.getToolParameters() != null &&
         * manifestDTO.getToolParameters().getQueryParameters() != null) {
         * Set<QueryParameter> queryParameters = new HashSet<QueryParameter>();
         * for (QueryParameterDTO qpDTO :
         * manifestDTO.getToolParameters().getQueryParameters()) {
         * QueryParameter qp = new QueryParameter();
         * qp.setManifest(manifest);
         * qp.setKey(qpDTO.getKey());
         * qp.setValue(qpDTO.getValue());
         * queryParameters.add(qp);
         * 
         * }
         * manifest.setQueryParameters(queryParameters);
         * }
         */
    }

}
