import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import type { ExternalTool } from "../../../types/MarketplaceTypes";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";


export default function useEditToolForm() {

    
    const [tool, setTool] = useState<ExternalTool | undefined>();
    const { id } = useParams();
    const { BASE_URL } = useMarketplaceApiRepo();
    const {putBodyRequest} = useMarketplaceApiRepo();

    useEffect(() => {
        const fetchTool = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`);
                setTool(response.data as ExternalTool);
            } catch (error) {
                console.error("Error fetching the tool:", error);
            }
        };
        fetchTool();
    }, [id, BASE_URL]);


    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        await putBodyRequest(`/api/tools/${id}`, formData);
        if (tool) {
            tool.name = formData.get("name") as string;
            tool.description = formData.get("description") as string;
        }
    };


    return {
        handleSubmit,
        tool
    };

}