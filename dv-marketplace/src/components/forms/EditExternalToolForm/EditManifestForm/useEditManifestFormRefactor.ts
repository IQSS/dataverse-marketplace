import { useEffect, useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { Manifest, AuxFilesExist } from "../../../../types/MarketplaceTypes";

export default function useEditManifestFormRefactor(manifest: Manifest) {

    const [show, setShow] = useState(false);
    const [toolTypes, setToolTypes] = useState<string[]>([]);
    const [scopes, setScopes] = useState<string[]>([]);
    const [httpMethods, setHttpMethods] = useState<string[]>([]);

    const [formManifest, setFormManifest] = useState<Manifest>({
        displayName: '',
        description: '',
        scope: '',
        toolUrl: '',
        httpMethod: '',
        contentType: '',
        toolParameters: { queryParameters: [] },
        toolName: '',
        contentTypes: [],
        types: [],
        allowedApiCalls: [],
        requirements: { auxFilesExist: [] as AuxFilesExist[] }
    });
        


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

    const handleClose = () => setShow(false);
    
    const handleShow = () => {

        console.log(manifest)            
        if(manifest) {            
            formManifest.displayName = manifest.displayName || '';
            formManifest.description = manifest.description || '';
            formManifest.scope = manifest.scope || '';
            formManifest.toolUrl = manifest.toolUrl || '';
            formManifest.httpMethod = manifest.httpMethod || '';
            formManifest.contentType = manifest.contentType || '';
            formManifest.toolName = manifest.toolName || '';
            formManifest.contentTypes = manifest.contentTypes || [];
            formManifest.types = manifest.types || [];
            formManifest.allowedApiCalls = manifest.allowedApiCalls || [];
            formManifest.toolParameters.queryParameters = manifest.toolParameters?.queryParameters || [];
            setFormManifest(formManifest);
        }

        setShow(true);
    };

    const handleSave = () => {
        
        const displayNameInput = document.getElementById("displayName") as HTMLInputElement;
        manifest.displayName = displayNameInput?.value;


        const form = document.getElementById("manifestForm") as HTMLFormElement;
        if (form) {
            const formData = new FormData(form);
            const formJson: Record<string, any> = {};
            formData.forEach((value, key) => {
                // Handle multiple values for the same key (e.g., multi-select)
                if (formJson[key]) {
                    if (Array.isArray(formJson[key])) {
                        formJson[key].push(value);
                    } else {
                        formJson[key] = [formJson[key], value];
                    }
                } else {
                    formJson[key] = value;
                }
            });
            // Print the JSON stringified version
            //console.log(JSON.stringify(formJson, null, 2));
        }
        console.log("Manifest saved:", manifest);
        setShow(false);
    };

    const handleReset = () => {
        
        setFormManifest({
            displayName: '',
            description: '',
            scope: '',
            toolUrl: '',
            httpMethod: '',
            contentType: '',
            toolName: '',
            contentTypes: [],
            types: [],
            allowedApiCalls: [],
            toolParameters: { queryParameters: [] },
            requirements: { auxFilesExist: [] as AuxFilesExist[] }
        });
                    
        const form = document.getElementById("manifestForm") as HTMLFormElement;
        form?.reset();
    }

    return {
        show,
        setShow,
        toolTypes,
        setToolTypes,
        scopes,
        setScopes,
        httpMethods,
        setHttpMethods,
        handleClose,
        handleShow,
        handleSave,
        handleReset,
        formManifest,
        setFormManifest
    };
}