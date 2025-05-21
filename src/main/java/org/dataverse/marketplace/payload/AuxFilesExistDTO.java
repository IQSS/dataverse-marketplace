package org.dataverse.marketplace.payload;

import java.io.Serializable;
import java.util.Objects;

import org.dataverse.marketplace.model.AuxFilesExist;
import org.dataverse.marketplace.model.ExternalToolVersion;
import org.dataverse.marketplace.payload.EntitySyncUtil.EntityBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuxFilesExist", description = "Aux File Exists")
public class AuxFilesExistDTO implements Serializable {

    String formatTag;
    String formatVersion;

    public AuxFilesExistDTO() {
    }

    public AuxFilesExistDTO(String formatTag, String formatVersion) {
        this.formatTag = formatTag;
        this.formatVersion = formatVersion;
    }

    public String getFormatTag() {
        return formatTag;
    }

    public void setFormatTag(String formatTag) {
        this.formatTag = formatTag;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    @Override
    public String toString() {
        return "AuxFilesExistDTO{" +
                "formatTag='" + formatTag + '\'' +
                ", formatVersion='" + formatVersion + '\'' +
                '}';
    }

    public static EntityBuilder<AuxFilesExistDTO, AuxFilesExist> auxFilesExistBuilder(ExternalToolVersion version) {
        return new EntitySyncUtil.EntityBuilder<>() {
            @Override
            public AuxFilesExist build(AuxFilesExistDTO auxFilesExistDTO) {
                AuxFilesExist auxFilesExist = new AuxFilesExist();
                auxFilesExist.setExternalToolVersion(version);
                auxFilesExist.setFormatTag(auxFilesExistDTO.getFormatTag());
                auxFilesExist.setFormatVersion(auxFilesExistDTO.getFormatVersion());
                return auxFilesExist;
            }

            @Override
            public boolean matches(AuxFilesExistDTO auxFilesExistDTO, AuxFilesExist auxFilesExist) {
                return Objects.equals(auxFilesExistDTO.getFormatTag(), auxFilesExist.getFormatTag()) &&
                        Objects.equals(auxFilesExistDTO.getFormatVersion(), auxFilesExist.getFormatVersion());
            }
        };
    }

}
