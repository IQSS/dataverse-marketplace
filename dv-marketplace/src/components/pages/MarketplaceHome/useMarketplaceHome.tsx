import { useContext, useEffect, useState } from "react";
import type { ExternalTool } from '../../../types/MarketplaceTypes';
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { UserContext } from "../../context/UserContextProvider";

export default function useMarketplaceHome(onlyMine = false) {

    const [tools, setTools] = useState<ExternalTool[]>([]);
    const {BASE_URL} = useMarketplaceApiRepo();
    const userContext = useContext(UserContext);


    useEffect(() => {
        const fetchTools = async () => {
            try {
                const url = onlyMine && userContext.user?.id
                ? `${BASE_URL}/api/users/${userContext.user.id}/tools`
                : `${BASE_URL}/api/tools`; // fallback: all tools

                const response = await axios.get(url);
                setTools(response.data);
            } catch (error) {
                console.error('Error fetching tools:', error);
            }
        };



        fetchTools();
    }, [BASE_URL,userContext.user?.id,onlyMine]);


    return {
        tools,
        userContext
    };

}