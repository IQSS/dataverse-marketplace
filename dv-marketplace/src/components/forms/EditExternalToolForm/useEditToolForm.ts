import { useContext, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";
import type { ExternalTool } from "../../../types/MarketplaceTypes";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { UserContext } from "../../context/UserContextProvider";

export default function useEditToolForm() {


    const [tool, setTool] = useState<ExternalTool | undefined>();
    const { id } = useParams();
    const navigate = useNavigate();
    const userContext = useContext(UserContext);

    const { putBodyRequest,
        deleteBodyRequest
     } = useMarketplaceApiRepo();

    const { BASE_URL } = useMarketplaceApiRepo();

    useEffect(() => {
        const fetchTool = async () => {
            try {
                const headers = userContext.user?.accessToken
                    ? { Authorization: `Bearer ${userContext.user.accessToken}` }
                    : {};
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`, { headers });
                setTool(response.data as ExternalTool);
            } catch (error) {
                toast.error("Error fetching the tool");
            }
        };
        fetchTool();
    }, [id, BASE_URL, userContext.user?.accessToken]);


    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        await putBodyRequest(`/api/tools/${id}`, formData);

        if (tool) {
            setTool({
                ...tool,
                name: formData.get("name") as string,
                description: formData.get("description") as string,
            });
        }
    };

    const handleDelete = async (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
        const data = await deleteBodyRequest(`/api/tools/${id}`);
        if (data) {
            navigate("/");
        }
    };

    const handlePublish = async () => {
        try {
            const headers = userContext.user?.accessToken
                ? { Authorization: `Bearer ${userContext.user.accessToken}` }
                : {};
            await axios.put(`${BASE_URL}/api/tools/${id}/publish`, {}, { headers });
            const response = await axios.get(`${BASE_URL}/api/tools/${id}`, { headers });
            setTool(response.data as ExternalTool);
            toast.success("Tool is now public");
        } catch (error) {
            toast.error("Error publishing tool");
            console.error("Error publishing tool:", error);
        }
    };

    const handleUnpublish = async () => {
        try {
            const headers = userContext.user?.accessToken
                ? { Authorization: `Bearer ${userContext.user.accessToken}` }
                : {};
            await axios.put(`${BASE_URL}/api/tools/${id}/unpublish`, {}, { headers });
            const response = await axios.get(`${BASE_URL}/api/tools/${id}`, { headers });
            setTool(response.data as ExternalTool);
            toast.success("Tool reverted to draft");
        } catch (error) {
            toast.error("Error reverting tool to draft");
            console.error("Error reverting tool to draft:", error);
        }
    };


    return {
        handleSubmit,
        handleDelete,
        handlePublish,
        handleUnpublish,
        tool,
        userContext
    };

}