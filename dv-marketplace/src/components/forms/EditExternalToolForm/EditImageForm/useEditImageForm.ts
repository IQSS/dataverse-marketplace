import { useEffect, useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool, Image } from "../../../../types/MarketplaceTypes";

export default function useEditImageForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        deleteBodyRequest,
        postFormRequest,
    } = useMarketplaceApiRepo();

    
    const [addImageFormIsOpen, setAddImageFormIsOpen] = useState(false);
    const [images, setImages] = useState<Image[]>(tool?.images || []);

    useEffect(() => {
        if (tool?.images) {
            setImages(tool.images);
        }
    }, [tool]);

    const handleImageSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await postFormRequest(`/api/tools/${tool?.id}/images`, formData);
        if (Array.isArray(data)) {
            for (const item of data) {
                images.push(item);
            }
        } else {
            images.push(data);
        }

        setAddImageFormIsOpen(false);
       
    };

    const handleImageDelete = async (imageId: number) => {
        const data = await deleteBodyRequest(`/api/tools/${tool?.id}/images/${imageId}`);
        if (data && tool?.images) {
            const updatedImages = images.filter((image) => image.storedResourceId !== imageId);
            setImages(updatedImages);
        }
    };

    return {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete,
        images,
    };
}