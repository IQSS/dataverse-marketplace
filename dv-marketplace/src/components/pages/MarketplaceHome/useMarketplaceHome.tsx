import { useEffect, useState } from "react";
import type { ExternalTool } from '../../../types/MarketplaceTypes';
import axios from "axios";

export default function useMarketplaceHome() {

    const [tools, setTools] = useState<ExternalTool[]>([]);

    useEffect(() => {
        const fetchTools = async () => {
            try {
                const response = await axios.get('http://localhost:8081/api/tools');
                setTools(response.data);
            } catch (error) {
                console.error('Error fetching tools:', error);
            }
        };

        fetchTools();
    }, []);


    return {
        tools
    };

}