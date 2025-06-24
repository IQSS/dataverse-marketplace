import { useEffect, useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";

export default function useEditManifestFormRefactor(){

    const [toolTypes, setToolTypes] = useState<string[]>([]);
    const [scopes, setScopes] = useState<string[]>([]);
    const [httpMethods, setHttpMethods] = useState<string[]>([]);

    const { BASE_URL } = useMarketplaceApiRepo();

    useEffect(() => {
        fetch(`${BASE_URL}/api/reference/scopes`)
            .then(res => res.json())
            .then(data => setScopes(data));

        fetch(`${BASE_URL}/api/reference/http-methods`)
            .then(res => res.json())
            .then(data => setHttpMethods(data));

        fetch(`${BASE_URL}/api/reference/tool-types`)
            .then(res => res.json())
            .then(data => setToolTypes(data));
    }, [BASE_URL]);

    return {
        toolTypes,
        setToolTypes,
        scopes,
        setScopes,
        httpMethods,
        setHttpMethods
    };
}