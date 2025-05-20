import { useContext } from "react";
import { UserContext } from "../../context/UserContextProvider";
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

export default function useAddExternalTool() {

    const userContext = useContext(UserContext);
    const jwtToken = userContext.user ? userContext.user.accessToken : '';
    const { BASE_URL } = useMarketplaceApiRepo();
    const navigate = useNavigate();

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
        handleSubmit
    };

}