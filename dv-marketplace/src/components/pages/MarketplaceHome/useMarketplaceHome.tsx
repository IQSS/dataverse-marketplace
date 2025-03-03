import { useEffect, useState } from "react";
import type { ExternalTool } from '../../../types/MarketplaceTypes';
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";

export default function useMarketplaceHome() {

    const [tools, setTools] = useState<ExternalTool[]>([]);
    const {BASE_URL} = useMarketplaceApiRepo();

    useEffect(() => {
        const fetchTools = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools`);
                setTools(response.data);
            } catch (error) {
                console.error('Error fetching tools:', error);
            }
        };

        fetchTools();
    }, [BASE_URL]);


    return {
        tools
    };

}