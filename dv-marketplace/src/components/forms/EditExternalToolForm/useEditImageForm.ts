import { useState } from "react";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { useParams } from "react-router-dom";

export default function useEditImageForm() {

    const {
        deleteRequest,
        postRequest
    } = useMarketplaceApiRepo();

    const {id} = useParams();
    const [addImageFormIsOpen, setAddImageFormIsOpen] = useState(false);

    const handleImageSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        postRequest(`/api/tools/${id}/images`, formData);
        
    };

    const handleImageDelete = async (imageId: number) => {
        deleteRequest(`/api/tools/${id}/images/${imageId}`);
    };

    return {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete
    };
}