import { Alert, Button, Form } from "react-bootstrap";
import type { ExternalTool, Version } from "../../../../types/MarketplaceTypes";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import useEditVersionCard from "./useEditVersionCard";
import { FormInputTextArea, FormInputTextField } from "../../../UI/FormInputFields";


interface EditVersionCardProps {
    version: Version;
    tool: ExternalTool | undefined;
}

const EditVersionCard = ({ version,tool }: EditVersionCardProps) => {
        
    const {
        handleVersionEdit,
        handleManifestDelete,
        handleVersionDelete,
        handleAddManifestSubmit,
        showVersionEdit,
        setShowVersionEdit,
        showAddManifest,
        setShowAddManifest,
    } = useEditVersionCard({ tool });
    
    return (
    <MarketplaceCard
        key={version.id}
        header={`Version: ${version.version}`}
    >
        {!(showVersionEdit === version.id) && (
            <div>
                <p>Release Note : {version.releaseNote}</p>
                <p>DV Min Version : {version.dataverseMinVersion}</p>
                <p>
                    Manifests:{" "}
                    <button
                        type="button"
                        className="btn bi-plus px-0"
                        onClick={() => {
                            setShowAddManifest(version.id);
                        }}
                    />
                </p>
                <Alert variant="info" show={showAddManifest === version.id}>
                    <Form onSubmit={(e) => {
                            handleAddManifestSubmit(e, version.id);
                        }}
                        encType="multipart/form-data"
                    >
                        <Form.Group className="mb-3">
                            <Form.Label>Manifest:</Form.Label>
                            <Form.Control type="file" name="jsonData" multiple />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Submit
                        </Button>
                    </Form>
                </Alert>
                <ul>
                    {version.manifests.map((manifest) => (
                        <li key={manifest.manifestId}>
                            {manifest.fileName}{" "}
                            <button type="button"
                                    className="btn bi-trash px-0"
                                    onClick={() =>
                                        handleManifestDelete(
                                            version.id,
                                            manifest.manifestId,)}>
                                <span/>
                            </button>
                        </li>
                    ))}
                </ul>

                <>
                    <button
                        type="button"
                        className="btn bi-pen px-0"
                        onClick={() => setShowVersionEdit(version.id)}
                    >
                        <span />
                    </button>
                    <button
                        type="button"
                        className="btn bi-trash px-0"
                        onClick={() => handleVersionDelete(version.id)}
                    >
                        <span />
                    </button>
                </>
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
                    Save Changes
                </Button>
            </Form>
        </Alert>
    </MarketplaceCard>);

};


export default EditVersionCard;