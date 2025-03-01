import { useState } from "react";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool } from "../../../types/MarketplaceTypes";

export default function useEditImageForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        deleteBodyRequest,
        postFormRequest
    } = useMarketplaceApiRepo();

    
    const [addImageFormIsOpen, setAddImageFormIsOpen] = useState(false);

    const handleImageSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await postFormRequest(`/api/tools/${tool?.id}/images`, formData);
        if (Array.isArray(data)) {
            for (const item of data) {
                tool?.images.push(item);
            }
        } else {
            tool?.images.push(data);
        }

        setAddImageFormIsOpen(false);
       
    };

    const handleImageDelete = async (imageId: number) => {
        deleteBodyRequest(`/api/tools/${tool?.id}/images/${imageId}`);
        if (tool?.images) {
            tool.images = tool.images.filter((image) => image.storedResourceId !== imageId);
        }
    };

    return {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete
    };
}