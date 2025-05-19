import { useEffect, useState } from "react";
import type { Manifest } from "../../../../types/MarketplaceTypes";
import { createFormChangeHandler } from "../../../UI/FormInputFields";


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

    const defaultManifest: Manifest = {
    displayName: '',
    description: '',
    scope: '',
    toolUrl: '',
    toolName: '',
    httpMethod: 'GET',
    types: [],
    contentType: '',
    contentTypes: [],
    toolParameters: {  queryParameters: []},
    allowedApiCalls: [],
    requirements: {  auxFilesExist: []}
    };

    const [formManifest, setFormManifest] = useState<Manifest>(defaultManifest);

    const handleManifestChange = createFormChangeHandler(setFormManifest);


    const handleJsonUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (!file) return;

        const reader = new FileReader();

        reader.onload = (e) => {
            const result = e.target?.result as string;
            try {
                const data = JSON.parse(result);

                // Convert 'contentType' to 'contentTypes' if necessary
                if (data.contentType && !data.contentTypes) {
                    data.contentTypes = [data.contentType];
                }

                setFormManifest(data);

            } catch (err) {
                console.error("Invalid JSON file", err);
            }
        };

        reader.readAsText(file);
    };    

    return {
        scopes,
        httpMethods,
        toolTypes,
        handleManifestChange,
        handleJsonUpload,
        defaultManifest,
        formManifest,
        setFormManifest
    };

}