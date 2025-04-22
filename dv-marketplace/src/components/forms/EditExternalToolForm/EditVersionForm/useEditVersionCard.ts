import { useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";


export default function useEditVersionCard({ tool }: { tool: ExternalTool | undefined }) {

    const {
            deleteBodyRequest,
            putBodyRequest,
            postFormRequest,
        } = useMarketplaceApiRepo();

    
    const [showVersionEdit, setShowVersionEdit] = useState(0);
    const [showAddManifest, setShowAddManifest] = useState(0);

    const handleVersionDelete = async (versionId: number) => {
        
        const data = await deleteBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`);
        if (data && tool?.versions) {
            tool.versions = tool.versions.filter((version: Version) => version.id !== versionId);
        }
    }

    const handleManifestDelete = async (versionId: number, manifestId: number) => {
        const data = await deleteBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}/manifests/${manifestId}`);
        if(data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.manifests = version.manifests.filter((manifest) => manifest.manifestId !== manifestId);
            }
        }
    }

    const handleVersionEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {
        
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await putBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`, formData);
        if(data) {  
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.releaseNote = formData.get("releaseNote") as string;
                version.dataverseMinVersion = formData.get("dvMinVersion") as string;
                version.version = formData.get("version") as string;
            }
        }    
        setShowVersionEdit(0);
    }

    const handleAddManifestSubmit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        
        const data = await postFormRequest(`/api/tools/${tool?.id}/versions/${versionId}/manifests`, formData);
        if(data) {
            for (const manifest of data) {
                const version = tool?.versions.find((version) => version.id === versionId);
                if (version) {
                    version.manifests.push(manifest);
                }
            }
        }
        setShowAddManifest(0);
    }

    return {
        handleVersionDelete,
        handleManifestDelete,
        handleVersionEdit,
        handleAddManifestSubmit,
        showVersionEdit,
        setShowVersionEdit,
        showAddManifest,
        setShowAddManifest,
    };

}