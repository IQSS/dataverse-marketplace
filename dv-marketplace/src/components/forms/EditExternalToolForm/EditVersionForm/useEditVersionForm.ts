import { useContext, useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool } from "../../../../types/MarketplaceTypes";
import { UserContext } from "../../../context/UserContextProvider";

export default function useEditVersionForm({ tool }: { tool: ExternalTool | undefined }) {

    const {
        postBodyRequest,
        deleteBodyRequest,
        putBodyRequest,
    } = useMarketplaceApiRepo();

    const userContext = useContext(UserContext);
    const [showVersionEdit, setShowVersionEdit] = useState(0);
    
    const [addVersionFormIsOpen, setAddVersionFormIsOpen] = useState(false);

    const handleVersionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
       event.preventDefault();
       const formData = new FormData(event.currentTarget);
       const data = await postBodyRequest(`/api/tools/${tool?.id}/versions`, formData);
       tool?.versions.push(data);
       setAddVersionFormIsOpen(false);
    };

    const handleVersionDelete = async (versionId: number) => {
        await deleteBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`);
        if (tool?.versions) {
            tool.versions = tool.versions.filter((version) => version.id !== versionId);
        }
    }

    const handleVersionEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {
        
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        //const versionId = Number.parseInt(formData.get("versionId") as string);
        console.log("versionId", `/api/tools/${tool?.id}/versions/${versionId}`);
        console.log("formData", formData.get("releaseNote"));
        console.log("formData", formData.get("dvMinVersion"));
        console.log("formData", formData.get("version"));
        const data = await putBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`, formData);


        
        const version = tool?.versions.find((version) => version.id === versionId);
        if (version) {
            version.releaseNote = formData.get("releaseNote") as string;
            version.dataverseMinVersion = formData.get("dvMinVersion") as string;
            version.version = formData.get("version") as string;
        }

        setShowVersionEdit(0);
        

    }




    return {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setAddVersionFormIsOpen,
        handleVersionDelete,
        userContext,
        handleVersionEdit,
        showVersionEdit,
        setShowVersionEdit
    };

}