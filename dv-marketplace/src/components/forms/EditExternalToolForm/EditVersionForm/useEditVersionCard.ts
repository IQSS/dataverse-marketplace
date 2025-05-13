import { useState } from "react";
import useMarketplaceApiRepo from "../../../../repositories/useMarketplaceApiRepo";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";


export default function useEditVersionCard({ tool }: { tool: ExternalTool | undefined }) {

    const {
        deleteBodyRequest,
        putBodyRequest,
        postBodyRequest,
    } = useMarketplaceApiRepo();

    const defaultManifest = {}

    const [showVersionEdit, setShowVersionEdit] = useState(0);
    const [showManifestAdd, setShowManifestAdd] = useState(0);
    const [showManifestEdit, setShowManifestEdit] = useState(0);
    const [newManifest, setNewManifest] = useState(defaultManifest);
    const [showAddPanel, setShowAddPanel] = useState(false);




    const handleVersionDelete = async (versionId: number) => {

        const data = await deleteBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`);
        if (data && tool?.versions) {
            tool.versions = tool.versions.filter((version: Version) => version.id !== versionId);
        }
    }

    const handleManifestDelete = async (versionId: number, manifestId: number) => {
        const data = await deleteBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}/manifests/${manifestId}`);
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.manifests = version.manifests.filter((manifest) => manifest.manifestId !== manifestId);
            }
        }
    }

    const handleVersionEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {

        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const data = await putBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}`, formData);
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.releaseNote = formData.get("releaseNote") as string;
                version.dataverseMinVersion = formData.get("dvMinVersion") as string;
                version.version = formData.get("version") as string;
            }
        }
        setShowVersionEdit(0);
    }

    const handleJsonUpload = (event: React.ChangeEvent<HTMLInputElement>,
        versionId: number
    ) => {
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
                
                setNewManifest(() => ({
                    ...data,
                }));

                setShowManifestAdd(versionId);
                setShowAddPanel(false);

            } catch (err) {
                console.error("Invalid JSON file", err);
            }
        };

        reader.readAsText(file);
    };

    const handleManifestAdd = async (event: React.FormEvent<HTMLFormElement>, versionId: number) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        processManifestFormData(formData);
        const data = await postBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}/manifests`, formData);
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                version.manifests.push(data);
            }
        }

        setShowManifestAdd(0);
    }

    const handleManifestEdit = async (event: React.FormEvent<HTMLFormElement>, versionId: number, manifestId: number) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        processManifestFormData(formData);

        const data = await putBodyRequest(`/api/tools/${tool?.id}/versions/${versionId}/manifests/${manifestId}`, formData);

        // todo: do we need to go through tool / version to get the manifest?
        if (data) {
            const version = tool?.versions.find((version) => version.id === versionId);
            if (version) {
                const manifest = version.manifests.find((manifest) => manifest.manifestId === manifestId);
                if (manifest) {
                    // clear out the manifest first
                    for (const key in manifest) {
                        if (Object.prototype.hasOwnProperty.call(manifest, key)) {
                            manifest[key] = null;
                            // alt 1
                            //manifest[key as keyof Manifest] = null;

                        }
                    }
                    // */
                    // alt 2 
                    /*
                    (Object.keys(manifest) as (keyof Manifest)[]).forEach((key) => {
                        manifest[key] = null;
                      });
                    // */


                    // assign new values from data
                    Object.assign(manifest, data);

                }
            }
        }

        setShowManifestEdit(0);
    }


    function processManifestFormData(formData: FormData) {

        ensureArrayInFormData(formData, "types");

        // iterate through query parameters to send variable keys
        for (const formKey of formData.keys()) {
            const match = formKey.match(/^queryParameters\[(\d+)\]\.key$/);
            if (match) {
                const index = match[1];
                const keyName = formData.get(`queryParameters[${index}].key`);
                const value = formData.get(`queryParameters[${index}].value`);
                if (keyName && value) {
                    formData.append(`toolParameters.queryParameters[${index}].${keyName}`, value);
                }
            }
        }
    }

    function ensureArrayInFormData(formData: FormData, key: string) {
        const values = formData.getAll(key);
        if (values.length === 1) {
            formData.append(key, values[0]); // append duplicate to ensure array-like
        }
    }

    return {
        handleVersionDelete,
        handleManifestDelete,
        showManifestEdit,
        handleManifestEdit,
        setShowManifestEdit,
        handleVersionEdit,
        handleManifestAdd,
        showVersionEdit,
        setShowVersionEdit,
        showManifestAdd,
        setShowManifestAdd,
        defaultManifest,
        newManifest,
        setNewManifest,
        showAddPanel,
        setShowAddPanel,
        handleJsonUpload
    };

}