import React from 'react';
import { Alert, Button, Form } from "react-bootstrap";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";
import { FormInputTextArea, FormInputTextField } from "../../../UI/FormInputFields";
import EditManifestForm from './EditManifestForm';
import useEditVersionCard from "./useEditVersionCard";


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
    } = useEditVersionCard({ tool });


    const versionData = { versionName: version.versionName, versionNote: version.versionNote, dataverseMinVersion: version.dataverseMinVersion };
    const [formData, setFormData] = React.useState(versionData);

    const handleVersionChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    return (
        <MarketplaceCard
            key={version.id}
            header={`Version Name: ${version.versionName}`}
        >
            {!(showVersionEdit === version.id) && (
                <div>
                    <p>Version Note : {version.versionNote}</p>
                    <p>Dataverse Min Version : {version.dataverseMinVersion}</p>
                    <p>Manifest Data:{" "}
                        <button
                            type="button"
                            className="btn bi-pen px-0"
                            onClick={() => {
                                setShowManifestEdit(version.id);
                            }}
                        />

                        <EditManifestForm
                            show={showManifestEdit === version.id}
                            onCancel={() => setShowManifestEdit(0)}
                            onSubmit={(e) => handleManifestEdit(e, version.id)}
                            initialManifest={version.manifest} 
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
                        label="Version Name"
                        name="versionName"
                        id={`versionName-${version.id}`}
                        value={formData.versionName}
                        onChange={handleVersionChange}
                    />
                    <FormInputTextArea
                        label="Version Note"
                        name="versionNote"
                        id={`versionNote-${version.id}`}
                        value={formData.versionNote}
                        onChange={handleVersionChange}

                    />
                    <FormInputTextField
                        label="Dataverse Min Version"
                        name="dataverseMinVersion"
                        id={`dataverseMinVersion-${version.id}`}
                        value={formData.dataverseMinVersion}
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