import useAddExternalTool from './useAddExternalTool';
import { Button } from "react-bootstrap";
import { Link } from 'react-router-dom';
import EditManifestForm from '../EditExternalToolForm/EditVersionForm/EditManifestForm';


const AddExtToolForm = () => {


    const {
        handleSubmit,
        showManifestEdit,
        setShowManifestEdit,
        manifestForm,
        setManifestForm
    } = useAddExternalTool();

    return (
        <div className="container">
            <h1>Add External Tool</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div className="mb-3">
                    <label htmlFor="toolName" className="form-label">Tool Name</label>
                    <input type="text" className="form-control" id="toolName" name="toolName" />
                </div>
                <div className="mb-3">
                    <label htmlFor="toolDescription" className="form-label">Description</label>
                    <textarea className="form-control" id="toolDescription" name="description" />
                </div>
                <div className="mb-3">
                    <label htmlFor="version" className="form-label">Version</label>
                    <input type="text" className="form-control" id="version" name="version" />
                </div>
                <div className="mb-3">
                    <label htmlFor="releaseNote" className="form-label">Release Note</label>
                    <textarea className="form-control" id="releaseNote" name="releaseNote" />
                </div>
                <div className="mb-3">
                    <label htmlFor="dataverseMinVersion" className="form-label">Dataverse Min Version</label>
                    <input type="text" className="form-control" id="dataverseMinVersion" name="dvMinVersion" />
                </div>
                <div className="mb-3">
                    <label htmlFor="images" className="form-label">Images</label>
                    <input type="file" className="form-control" id="images" name="itemImages" multiple />
                </div>

                <p>Manifest Data:{" "}
                    <button
                        type="button"
                        className="btn bi-pen px-0"
                        onClick={() => {
                            setShowManifestEdit(1);
                        }}
                    />

                    <EditManifestForm
                        show={showManifestEdit === 1}
                        initialManifest={manifestForm ?? undefined}
                        onSave={(newManifest) => {
                            setManifestForm(newManifest);
                            setShowManifestEdit(0);
                        }}
                        onCancel={() => setShowManifestEdit(0)}
                    />
                </p>


                <button type="submit" className="btn btn-primary">Save</button>
                <Button variant="outline-secondary" className="ms-2" as={Link as any} to={`/`}>
                    <span className="me-1"></span>Cancel
                </Button>
            </form>
        </div>
    );
};

export default AddExtToolForm;
