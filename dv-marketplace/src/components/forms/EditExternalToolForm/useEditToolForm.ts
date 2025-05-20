import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";
import type { ExternalTool } from "../../../types/MarketplaceTypes";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";

export default function useEditToolForm() {


    const [tool, setTool] = useState<ExternalTool | undefined>();
    const { id } = useParams();
    const navigate = useNavigate();

    const { putBodyRequest,
        deleteBodyRequest
     } = useMarketplaceApiRepo();

    const { BASE_URL } = useMarketplaceApiRepo();

    useEffect(() => {
        const fetchTool = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`);
                setTool(response.data as ExternalTool);
            } catch (error) {
                toast.error("Error fetching the tool");
            }
        };
        fetchTool();
    }, [id, BASE_URL]);


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


    return {
        handleSubmit,
        handleDelete,
        tool
    };

}