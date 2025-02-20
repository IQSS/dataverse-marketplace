package org.dataverse.marketplace.payload;

import java.io.Serializable;
import org.dataverse.marketplace.model.MarketplaceItemImage;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Marketplace item image")
public class MarketplaceItemImageDTO implements Serializable {

    @Schema(description = "The image ID", example = "1")
    private Integer imageId;

    @Schema(description = "The stored resource ID", example = "1")
    private Long storedResourceId;
    
    public MarketplaceItemImageDTO() {
    }

    public MarketplaceItemImageDTO(MarketplaceItemImage marketplaceItemImage) {
        this.imageId = marketplaceItemImage.getId();
        this.storedResourceId = marketplaceItemImage.getImageStoredResourceId();
    }

    /* Getters and Setters */

    public Integer getImageId() {
        return this.imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Long getStoredResourceId() {
        return this.storedResourceId;
    }

    public void setStoredResourceId(Long storedResourceId) {
        this.storedResourceId = storedResourceId;
    }

}