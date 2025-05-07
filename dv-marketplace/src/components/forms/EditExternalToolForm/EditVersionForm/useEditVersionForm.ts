import { useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";

export default function useEditVersionForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        postFormRequest,
    } = useMarketplaceApiRepo();
    
    const [addVersionFormIsOpen, setAddVersionFormIsOpen] = useState(false);

    const handleVersionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
       event.preventDefault();
       const formData = new FormData(event.currentTarget);
       const data = await postFormRequest(`/api/tools/${tool?.id}/versions`, formData);
       
       if (data) {
           tool?.versions.push(data);
           setAddVersionFormIsOpen(false);
       } else {
           console.error("Failed to add version");
       }
    };

    return {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setAddVersionFormIsOpen,
    };

}