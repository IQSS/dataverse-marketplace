import React from 'react';
import { Alert, Button, Form } from "react-bootstrap";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import useEditVersionCard from "./useEditVersionCard";
import { FormInputTextArea, FormInputTextField, createFormChangeHandler } from "../../../UI/FormInputFields";
import EditManifestForm from './EditManifestForm'; // adjust the path if needed


interface EditVersionCardProps {
    version: Version;
    tool: ExternalTool | undefined;
}

const EditVersionCard = ({ version, tool }: EditVersionCardProps) => {

    const {
        showVersionEdit,
        setShowVersionEdit,
        showManifestEdit,
        setShowManifestEdit,
        handleVersionDelete,
        handleVersionEdit,
        handleManifestEdit,
        handleJsonUpload,
        defaultManifest,
        formManifest,
        setFormManifest
    } = useEditVersionCard({ tool });


    const versionData = { version: version.version, releaseNote: version.releaseNote, dvMinVersion: version.dataverseMinVersion };    
    const [formData, setFormData] = React.useState( versionData );

    const handleVersionChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleManifestChange = createFormChangeHandler(setFormManifest);

    return (
        <MarketplaceCard
            key={version.id}
            header={`Version: ${version.version}`}
        >
            {!(showVersionEdit === version.id) && (
                <div>
                    <p>Release Note : {version.releaseNote}</p>
                    <p>DV Min Version : {version.dataverseMinVersion}</p>
                    <p>Manifest Data:{" "}
                        <button
                            type="button"
                            className="btn bi-pen px-0"
                            onClick={() => {
                                setFormManifest(version.manifest)
                                setShowManifestEdit(version.id);
                            }}
                        />

                        <EditManifestForm
                            show={showManifestEdit === version.id}
                            onCancel={() => setShowManifestEdit(0)}
                            onSubmit={(e) => handleManifestEdit(e, version.id)}
                            onUpload={(e) => handleJsonUpload(e)}
                            onReset={() => setFormManifest(defaultManifest)}
                            onChange={handleManifestChange}

                            formManifest={formManifest}
                        />
                    </p>
                    <div>
                        <button
                            type="button"
                            className="btn bi-pen px-0"
                            onClick={() => setShowVersionEdit(version.id)}>
                            <span />
                        </button>
                        <button
                            type="button"
                            className="btn bi-trash px-0"
                            onClick={() => handleVersionDelete(version.id)}>
                            <span />
                        </button>
                    </div>
                </div>
            )}

            <Alert variant="light" show={showVersionEdit === version.id}>
                <Form onSubmit={(e) => handleVersionEdit(e, version.id)}>
                    <FormInputTextField
                        label="Version"
                        name="version"
                        id={`version-${version.id}`}
                        value={formData.version}
                        onChange={handleVersionChange}
                    />
                    <FormInputTextArea
                        label="Release Note"
                        name="releaseNote"
                        id={`releaseNote-${version.id}`}
                        value={formData.releaseNote}
                        onChange={handleVersionChange}

                    />
                    <FormInputTextField
                        label="DV Min Version"
                        name="dvMinVersion"
                        id={`dvMinVersion-${version.id}`}
                        value={formData.dvMinVersion}
                        onChange={handleVersionChange}

                    />

                    <Button variant="primary" type="submit">
                        Save
                    </Button>

                    <Button variant="outline-secondary" className="ms-2"
                        onClick={() => {
                            setFormData(versionData); // reset the Form
                            setShowVersionEdit(0);
                        }}
                    >
                        Cancel
                    </Button>
                </Form>
            </Alert>
        </MarketplaceCard>);

};

export default EditVersionCard;