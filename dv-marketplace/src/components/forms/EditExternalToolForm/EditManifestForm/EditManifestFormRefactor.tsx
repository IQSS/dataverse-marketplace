import { forwardRef, useImperativeHandle, useRef } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { FormInputTextField, FormInput, FormInputTextArea, FormInputSelect, KeyPairInput, SingleMultiValueInput } from "../../../UI/FormInputFieldsRefactor";
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

    const {
        toolTypes,
        scopes,
        httpMethods,
        show,
        handleClose,
        handleShow,
        handleSave,
        handleReset,
        formManifest,
        handleJsonUpload,
    } = useEditManifestFormRefactor(manifest);


    useImperativeHandle(ref, () => ({
        open: handleShow,
        close: handleClose,
    }));

    const fileInputRef = useRef<HTMLInputElement>(null);
    

   
    return (
        <Modal show={show} onHide={handleClose} size="lg" centered backdrop="static">
            <Modal.Header closeButton>
                <Modal.Title>Manifest</Modal.Title>
                <div className="d-flex ms-auto gap-2">
                    <button type="button" className="btn btn-primary"
                        onClick={() => {
                            if (fileInputRef.current) {
                                fileInputRef.current.click();
                            }
                        }}>
                        Upload JSON
                    </button>
                    <input
                        type="file"
                        accept=".json"
                        ref={fileInputRef}
                        style={{ display: 'none' }}
                        onChange={(e) => {
                           handleJsonUpload(e)
                        }} />
                    <button type="button" className="btn btn-secondary" onClick={handleReset}>
                        Reset
                    </button>
                </div>
            </Modal.Header>
            <Modal.Body>
                <Form id="manifestForm" >
                <Form.Group className="mb-3">
                    <Form.Label as="h5" className="mt-4 mb-3">Presentation</Form.Label>
                    <FormInputTextField id="displayName" name="displayName" label="Display Name" value={formManifest?.displayName} />
                    <FormInputTextArea id="description" name="description" label="Description" value={formManifest?.description} />

                    <Form.Label as="h5" className="mt-4 mb-3">Access</Form.Label>

                    <FormInputSelect id="types" name="types" label="Types"
                        options={toolTypes.map(type => ({ value: type, label: type }))}
                        multiple={true} value={formManifest?.types} />

                    <SingleMultiValueInput label="Content Types" id="contentTypes" />

                    <Form.Label as="h5" className="mt-4 mb-3">Configuration</Form.Label>
                    <FormInputTextField id="toolName" name="toolName" label="Tool Name" />
                    <FormInput id="toolUrl" name="toolUrl" label="Tool URL" htmlType="url" />
                    <FormInputSelect id="scope" name="scope" label="Scope"
                        options={scopes.map(scope => ({ value: scope, label: scope }))}
                        value={formManifest?.scope} />
                    <FormInputSelect id="httpMethod" name="httpMethod" label="HTTP Method"
                        options={httpMethods.map(method => ({ value: method, label: method }))}
                        value={formManifest?.httpMethod} />

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