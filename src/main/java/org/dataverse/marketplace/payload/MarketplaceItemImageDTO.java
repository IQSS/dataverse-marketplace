package org.dataverse.marketplace.payload;

import java.io.Serializable;
import org.dataverse.marketplace.model.MarketplaceItemImage;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "MarketplaceItemImage",
    description = "Marketplace item image")
public class MarketplaceItemImageDTO implements Serializable {

    @Schema(description = "The image ID", example = "1")
    private Long imageId;

    @Schema(description = "The stored resource ID", example = "1")
    private Long storedResourceId;
    
    public MarketplaceItemImageDTO() {
    }

    public MarketplaceItemImageDTO(MarketplaceItemImage marketplaceItemImage) {
        this.imageId = marketplaceItemImage.getId();
        this.storedResourceId = marketplaceItemImage.getStoredResource().getId();
    }

    /* Getters and Setters */

    public Long getImageId() {
        return this.imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getStoredResourceId() {
        return this.storedResourceId;
    }

    public void setStoredResourceId(Long storedResourceId) {
        this.storedResourceId = storedResourceId;
    }

}