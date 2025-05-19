import { useEffect, useState } from "react";


export default function useEditManifestForm() {

    const BASE_URL = 'http://localhost:8081';


    const [scopes, setScopes] = useState<string[]>([]);
    const [httpMethods, setHttpMethods] = useState<string[]>([]);
    const [toolTypes, setToolTypes] = useState<string[]>([]);


    useEffect(() => {
        fetch(BASE_URL + "/api/reference/scopes")
            .then(res => res.json())
            .then(data => setScopes(data));

        fetch(BASE_URL + "/api/reference/http-methods")
            .then(res => res.json())
            .then(data => setHttpMethods(data));

        fetch(BASE_URL + "/api/reference/tool-types")
            .then(res => res.json())
            .then(data => setToolTypes(data));
    }, []);


    return {
        scopes,
        httpMethods,
        toolTypes
    };

}