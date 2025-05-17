import { useRef } from "react";
import { Modal, Alert, Button, Form } from "react-bootstrap";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import useEditVersionCard from "./useEditVersionCard";
import { FormInputTextArea, FormInputTextField } from "../../../UI/FormInputFields";
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
        showEditPanel,
        setShowEditPanel,
        handleVersionDelete,
        handleVersionEdit,
        handleManifestEdit,
        handleJsonUpload
    } = useEditVersionCard({ tool });

    const fileInputRef = useRef<HTMLInputElement>(null);

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
                                setShowEditPanel(true);
                            }}
                        />

                        <Modal show={showEditPanel} onHide={() => setShowEditPanel(false)} centered>
                            <Modal.Header closeButton>
                                <Modal.Title>Edit Manifest</Modal.Title>
                            </Modal.Header>

                            <Modal.Body>
                                <div className="d-flex justify-content-center mb-2">
                                    <Button
                                        variant="primary"
                                        className="me-3"
                                        onClick={() => {
                                            if (fileInputRef.current) {
                                                fileInputRef.current.click();
                                            }
                                        }}
                                    >
                                        Upload JSON
                                    </Button>

                                    <input
                                        type="file"
                                        accept=".json"
                                        ref={fileInputRef}
                                        style={{ display: 'none' }}
                                        onChange={(event) => {
                                            handleJsonUpload(event, version);
                                        }}
                                    />

                                    <Button
                                        variant="secondary"
                                        className="me-2" // Adds margin to the right of the button
                                        onClick={() => {
                                            setShowManifestEdit(version.id);
                                            setShowEditPanel(false);
                                        }}>
                                        Manual
                                    </Button>
                                </div>
                            </Modal.Body>
                        </Modal>
                        <EditManifestForm
                            show={showManifestEdit === version.id}
                            onCancel={() => setShowManifestEdit(0)}
                            onSubmit={(e) => handleManifestEdit(e, version.id)}
                            formManifest={version.manifest}
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
                        value={version.version}
                    />
                    <FormInputTextArea
                        label="Release Note"
                        name="releaseNote"
                        id={`releaseNote-${version.id}`}
                        value={version.releaseNote}
                    />
                    <FormInputTextField
                        label="DV Min Version"
                        name="dvMinVersion"
                        id={`dvMinVersion-${version.id}`}
                        value={version.dataverseMinVersion}
                    />

                    <Button variant="primary" type="submit">
                        Save
                    </Button>

                    <Button variant="outline-secondary" className="ms-2"
                        onClick={() => {
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