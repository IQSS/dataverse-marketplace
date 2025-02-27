import { useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";

export default function useEditVersionForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        postRequest,
        deleteRequest,
    } = useMarketplaceApiRepo();
    
    const [addVersionFormIsOpen, setaddVersionFormIsOpen] = useState(false);

    const handleVersionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
       event.preventDefault();
       const formData = new FormData(event.currentTarget);
       const data = await postRequest(`/api/tools/${tool?.id}/versions`, formData);
       tool?.versions.push(data);
       setaddVersionFormIsOpen(false);
    };

    const handleVersionDelete = async (versionId: number) => {
        await deleteRequest(`/api/tools/${tool?.id}/versions/${versionId}`);
        if (tool?.versions) {
            tool.versions = tool.versions.filter((version) => version.id !== versionId);
        }
    }




    return {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setaddVersionFormIsOpen,
        handleVersionDelete
    };

}