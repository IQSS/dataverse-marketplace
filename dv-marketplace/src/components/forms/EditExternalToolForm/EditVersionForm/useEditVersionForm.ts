import React, { useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";

export default function useEditVersionForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        postFormRequest,
    } = useMarketplaceApiRepo();

    const [addVersionFormIsOpen, setAddVersionFormIsOpen] = useState(false);
    const emptyVersion = { version: '', releaseNote: '', dvMinVersion: '' };
    const [versionData, setVersionData] = React.useState(emptyVersion);

    const handleVersionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await postFormRequest(`/api/tools/${tool?.id}/versions`, formData);

        if (data) {
            tool?.versions.push(data);
            setVersionData(emptyVersion);
            setAddVersionFormIsOpen(false);
        } else {
            console.error("Failed to add version");
        }
    };




    return {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setAddVersionFormIsOpen,
        emptyVersion,
        versionData,
        setVersionData
    };

}