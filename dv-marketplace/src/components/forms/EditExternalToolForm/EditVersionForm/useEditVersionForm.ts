import { useContext, useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";
import { UserContext } from "../../../context/UserContextProvider";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool, Manifest } from "../../../../types/MarketplaceTypes";

export default function useEditVersionForm({ tool }: { tool: ExternalTool | undefined }) {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const { BASE_URL } = useMarketplaceApiRepo();

    const [showManifestEdit, setShowManifestEdit] = useState(0);
    const [manifestForm, setManifestForm] = useState<Manifest | null>(null);

    const [addVersionFormIsOpen, setAddVersionFormIsOpen] = useState(false);
    const emptyVersion = { version: '', releaseNote: '', dvMinVersion: '' };
    const [versionData, setVersionData] = useState(emptyVersion);

    const handleVersionSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const form = event.currentTarget;

        const versionData = {
            version: form.version.value,
            releaseNote: form.releaseNote.value,
            dvMinVersion: form.dvMinVersion.value,
            manifest: manifestForm
        };

        try {
            const response = await axios.post(`${BASE_URL}/api/tools/${tool?.id}/versions`, versionData, {
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                }
            });

            const data = response.data;

            if (data) {
                toast.success("Version added successfully");
                tool?.versions.push(data);
                setManifestForm(null);
                setVersionData(emptyVersion);
                setAddVersionFormIsOpen(false);
            } else {
                toast.error("Failed to add version");
            }
        } catch (error: any) {
            if (error.response?.data?.message) {
                toast.error(error.response.data.message);
            } else {
                toast.error("An error occurred");
            }
        }
    };



    return {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setAddVersionFormIsOpen,
        emptyVersion,
        versionData,
        setVersionData,
        showManifestEdit,
        setShowManifestEdit,
        manifestForm,
        setManifestForm
    };

}