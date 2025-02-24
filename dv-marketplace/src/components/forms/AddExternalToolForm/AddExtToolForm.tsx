import Modal from 'react-bootstrap/Modal';
import useAddExternalTool from './useAddExternalTool';

const AddExtToolForm = () => {
    

    const {
        handleSubmit,
        modalIsOpen,
        modalMessage,
        closeModal
    } = useAddExternalTool();

    return (
        <div className="container">
            <h1>Add External Tool</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div className="mb-3">
                    <label htmlFor="toolName" className="form-label">Tool Name</label>
                    <input type="text" className="form-control" id="toolName" name="name" />
                </div>
                <div className="mb-3">
                    <label htmlFor="toolDescription" className="form-label">Description</label>
                    <textarea className="form-control" id="toolDescription" name="description" />
                </div>
                <div className="mb-3">
                    <label htmlFor="releaseNote" className="form-label">Release Note</label>
                    <textarea className="form-control" id="releaseNote" name="releaseNote" />
                </div>
                <div className="mb-3">
                    <label htmlFor="version" className="form-label">Version</label>
                    <input type="text" className="form-control" id="version" name="version" />
                </div>
                <div className="mb-3">
                    <label htmlFor="dataverseMinVersion" className="form-label">Dataverse Min Version</label>
                    <input type="text" className="form-control" id="dataverseMinVersion" name="dvMinVersion" />
                </div>
                <div className="mb-3">
                    <label htmlFor="manifests" className="form-label">Manifests</label>
                    <input type="file" className="form-control" id="manifests" name="jsonData" multiple />
                </div>
                <div className="mb-3">
                    <label htmlFor="images" className="form-label">Images</label>
                    <input type="file" className="form-control" id="images" name="itemImages" multiple />
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>    

            <Modal show={modalIsOpen} onHide={closeModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Response</Modal.Title>
                </Modal.Header>
                <Modal.Body>{modalMessage}</Modal.Body>
                <Modal.Footer>
                    <button type="button" className="btn btn-secondary" onClick={closeModal}>Close</button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default AddExtToolForm;
