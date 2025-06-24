import { useState, forwardRef, useImperativeHandle } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { FormInputTextField, FormInput, FormInputTextArea, FormInputSelect, MultiValueInput, KeyPairInput, SingleMultiValueInput } from "../../../UI/FormInputFieldsRefactor";
import type { Manifest } from "../../../../types/MarketplaceTypes";
import useEditManifestFormRefactor from "./useEditManifestFormRefactor";

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
            console.log(JSON.stringify(formJson, null, 2));
        }
        setShow(false);
    };

    useImperativeHandle(ref, () => ({
        open: handleShow,
        close: handleClose,
    }));

   const {
        toolTypes,
        scopes,
        httpMethods,
   } = useEditManifestFormRefactor();

    return (
        <Modal show={show} onHide={handleClose} size="lg" centered backdrop="static">
            <Modal.Header closeButton>
                <Modal.Title>Manifest</Modal.Title>
                <div className="d-flex ms-auto gap-2">
                    <button type="button" className="btn btn-primary">
                        Upload JSON
                    </button>
                    <button type="button" className="btn btn-secondary">
                        Reset
                    </button>
                </div>
            </Modal.Header>
            <Modal.Body>
                <Form id="manifestForm" >
                <Form.Group className="mb-3">
                    <Form.Label as="h5" className="mt-4 mb-3">Presentation</Form.Label>
                    <FormInputTextField id="displayName" name="displayName" label="Display Name" value={manifest?.displayName} />
                    <FormInputTextArea id="description" name="description" label="Description" value={manifest?.description} />

                    <Form.Label as="h5" className="mt-4 mb-3">Access</Form.Label>

                    <FormInputSelect id="types" name="types" label="Types"
                        options={toolTypes.map(type => ({ value: type, label: type }))}
                        multiple={true} value={manifest?.types} />

                    <SingleMultiValueInput label="Content Types" id="contentTypes" />

                    <Form.Label as="h5" className="mt-4 mb-3">Configuration</Form.Label>
                    <FormInputTextField id="toolName" name="toolName" label="Tool Name" />
                    <FormInput id="toolUrl" name="toolUrl" label="Tool URL" htmlType="url" />
                    <FormInputSelect id="scope" name="scope" label="Scope"
                        options={scopes.map(scope => ({ value: scope, label: scope }))}
                        value={manifest?.scope} />
                    <FormInputSelect id="httpMethod" name="httpMethod" label="HTTP Method"
                        options={httpMethods.map(method => ({ value: method, label: method }))}
                        value={manifest?.httpMethod} />

                    <KeyPairInput label="Query Parameters" id="queryParams" />

                    <KeyPairInput label="Allowed API Calls" id="allowedApiCalls" />

                    <KeyPairInput label="Aux Files Exists" id="auxFiles" />
                </Form.Group>
                <Button variant="primary" className="ms-2"
                        onClick={handleSave}>
                        Save
                </Button>
                <Button variant="outline-secondary" className="ms-2"
                        onClick={handleClose}>
                        Cancel
                </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
    
});


export default EditManifestFormRefactor;