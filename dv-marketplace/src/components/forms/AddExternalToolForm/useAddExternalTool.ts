import { useContext, useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { UserContext } from "../../context/UserContextProvider";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import type { Manifest } from "../../../types/MarketplaceTypes";


export default function useAddExternalTool() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const { BASE_URL } = useMarketplaceApiRepo();
    const navigate = useNavigate();

    const [showManifestEdit, setShowManifestEdit] = useState(0);
    const [manifestForm, setManifestForm] = useState<Manifest | null>(null);

    const handleManifestEdit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        /*
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
        }*/

        setShowManifestEdit(0);
    }


    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const form = event.currentTarget;
        const formData = new FormData();

        const toolData = {
            name: form.toolName.value,
            description: form.description.value,
            version: form.version.value,
            releaseNote: form.releaseNote.value,
            dvMinVersion: form.dvMinVersion.value,
            manifest: manifestForm

        };
        formData.append("toolData", new Blob([JSON.stringify(toolData)], { type: "application/json" }));

        // Append image files
        const imageFiles = form.itemImages.files;
        for (let i = 0; i < imageFiles.length; i++) {
            formData.append("itemImages", imageFiles[i]);
        }        

        await axios.post(`${BASE_URL}/api/tools/withimages`, formData, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        }).then(() => {
            toast.success("Tool added successfully");
            navigate("/");
        }).catch((error) => {
            if (error.response.data.message) {
                toast.error(error.response.data.message);
            } else {
                toast.error("An error occurred");
            }
        });

       
    };

   

    return {
        userContext,
        handleSubmit,
        showManifestEdit,
        setShowManifestEdit,
        manifestForm,
        setManifestForm
    };

}