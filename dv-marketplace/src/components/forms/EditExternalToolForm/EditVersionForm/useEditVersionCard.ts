import { useState } from "react";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";


export default function useEditVersionCard({ tool }: { tool: ExternalTool | undefined }) {

    const {
        deleteBodyRequest,
        putBodyRequest,
    } = useMarketplaceApiRepo();


    const [showVersionEdit, setShowVersionEdit] = useState(0);
    const [showManifestEdit, setShowManifestEdit] = useState(0);
    
    const handleVersionDelete = async (versionId: number) => {

        const data = await deleteBodyRequest(`/api/versions/${versionId}`);
        if (data && tool?.versions) {
            tool.versions = tool.versions.filter((version: Version) => version.id !== versionId);
        }
    }

    const handleVersionEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {

        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await putBodyRequest(`/api/versions/${versionId}`, formData);
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.versionName = formData.get("versionName") as string;                
                version.versionNote = formData.get("versionNote") as string;
                version.dataverseMinVersion = formData.get("dataverseMinVersion") as string;
            }
        }
        setShowVersionEdit(0);
    }

    const handleManifestEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        const values = formData.getAll("types");
        if (values.length === 1) {
            formData.append("types", values[0]); // append duplicate to ensure array
        }

        const data = await putBodyRequest(`/api/versions/${versionId}/manifest`, formData);
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                // assign new values from data
                Object.assign(version, data);

            }
        }

        setShowManifestEdit(0);
    }

    return {
        showVersionEdit,
        setShowVersionEdit,        
        showManifestEdit,
        setShowManifestEdit,
        handleVersionDelete,
        handleVersionEdit,
        handleManifestEdit,
    };

}