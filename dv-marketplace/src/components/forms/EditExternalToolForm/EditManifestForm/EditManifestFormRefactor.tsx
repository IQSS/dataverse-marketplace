import { useState, forwardRef, useImperativeHandle } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { FormInputTextField, FormInput } from "../../../UI/FormInputFieldsRefactor";
import type { Manifest } from "../../../../types/MarketplaceTypes";

export type ModalRef = {
    open: () => void;
    close: () => void;
}

type EditManifestFormRefactorProps = {
    manifest: Manifest;
};

const EditManifestFormRefactor = forwardRef<ModalRef, EditManifestFormRefactorProps>(({ manifest }, ref) => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSave = () => {
        
        const displayNameInput = document.getElementById("displayName") as HTMLInputElement;
        manifest.displayName = displayNameInput?.value;


        
        setShow(false);
    };

    useImperativeHandle(ref, () => ({
        open: handleShow,
        close: handleClose,
    }));

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Manifest</Modal.Title> -{manifest?.displayName}
            </Modal.Header>
            <Modal.Body>
                <Form.Group className="mb-3">
                    <Form.Label as="h5" className="mt-4 mb-3">Presentation</Form.Label>
                    <FormInputTextField id="displayName" name="displayName" label="Display Name" value={manifest?.displayName} />
                    <FormInputTextField id="description" name="description" label="Description" />

                    <Form.Label as="h5" className="mt-4 mb-3">Access</Form.Label>

                    {/* TODO IMPLEMENT THESE 2  */}
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />


                    <Form.Label as="h5" className="mt-4 mb-3">Configuration</Form.Label>
                    <FormInputTextField id="toolName" name="toolName" label="Tool Name" />
                    <FormInput id="toolUrl" name="toolUrl" label="Tool URL" htmlType="url" />

                    {/* TODO IMPLEMENT THESE 5  */}
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    <FormInput id="placeholder" name="placeholder" label="placeholder" htmlType="select" />
                    


                </Form.Group>
                <Button variant="primary" className="ms-2"
                        onClick={handleSave}>
                        Save
                </Button>
                <Button variant="outline-secondary" className="ms-2"
                        onClick={handleClose}>
                        Cancel
                </Button>
            </Modal.Body>
        </Modal>
    );
    
});


export default EditManifestFormRefactor;