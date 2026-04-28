import { useContext, useEffect, useState } from "react";
import type { ExternalTool } from '../../../types/MarketplaceTypes';
import axios from "axios";
import useMarketplaceApiRepo from "../../../repositories/useMarketplaceApiRepo";
import { UserContext } from "../../context/UserContextProvider";
import { toast } from "react-toastify";

export default function useMarketplaceHome(onlyMine = false, scope: string | null = null, type: string | null = null, status: string | null = null) {

    const [tools, setTools] = useState<ExternalTool[]>([]);
    const {BASE_URL} = useMarketplaceApiRepo();
    const userContext = useContext(UserContext);


    useEffect(() => {
        const fetchTools = async () => {
            try {
                const url = onlyMine && userContext.user?.id
                ? `${BASE_URL}/api/users/${userContext.user.id}/tools`
                : `${BASE_URL}/api/tools`; // fallback: all tools

                const params = new URLSearchParams();
                if (!onlyMine) {
                    if (scope) params.append('scope', scope);
                    if (type) params.append('type', type);
                    if (status) params.append('status', status);
                }

                const fullUrl = params.toString() ? `${url}?${params}` : url;

                const headers = userContext.user?.accessToken
                    ? { Authorization: `Bearer ${userContext.user.accessToken}` }
                    : {};

                console.log('Fetching tools from:', fullUrl);
                const response = await axios.get(fullUrl, { headers });
                console.log('Tools response:', response.data);
                setTools(response.data);
            } catch (error) {
                console.error('Error fetching tools:', error);
                toast.error(`Error fetching tools`);
            }
        };



        fetchTools();
    }, [BASE_URL,userContext.user?.id,userContext.user?.accessToken,onlyMine,scope,type,status]);


    return {
        tools,
        userContext
    };

}