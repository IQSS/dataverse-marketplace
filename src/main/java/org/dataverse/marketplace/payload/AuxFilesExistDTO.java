package org.dataverse.marketplace.payload;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aux File Exists data transfer object")
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

}
