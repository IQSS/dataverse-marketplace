import React, { useRef } from 'react';
import { Modal, Button, Form } from "react-bootstrap";
import type { Manifest } from "../../../../types/MarketplaceTypes";
import { FormInput, FormInputTextArea, FormInputTextField, FormInputSelect } from "../../../UI/FormInputFields";
import MultiInputGroup from './MultiInputGroup';
import useEditManifestForm from './useEditManifestForm';


const EditManifestForm = ({
    show,
    onCancel,
    onSubmit,
    onSave,
    initialManifest,
}: {
    show: boolean;
    onCancel: () => void;
    onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
    onSave?: (manifest: Manifest) => void;
    initialManifest?: Manifest
}) => {

    const isSubmitMode = typeof onSubmit === "function";
    const fileInputRef = useRef<HTMLInputElement>(null);

    const handleCancel = () => {
        onCancel();
    };
    const handleSave = () => {
        if (onSave) {
            onSave(formManifest);
        }
    };
    const {
        scopes,
        httpMethods,
        toolTypes,
        handleManifestChange,
        handleJsonUpload,
        defaultManifest,
        formManifest,
        setFormManifest
    } = useEditManifestForm(initialManifest, show);

    return (
        <Modal show={show} onHide={handleCancel} size="lg" centered backdrop="static">
            <Modal.Header closeButton>
                <Modal.Title>Edit Manifest</Modal.Title>
                <div className="d-flex ms-auto gap-2">
                    <Button
                        variant="primary"
                        onClick={() => {
                            if (fileInputRef.current) {
                                fileInputRef.current.click();
                            }
                        }} >
                        Upload JSON
                    </Button>

                    <input
                        type="file"
                        accept=".json"
                        ref={fileInputRef}
                        style={{ display: 'none' }}
                        onChange={(e) => {
                            handleJsonUpload(e)
                            e.target.value = '';
                        }} />

                    <Button
                        variant="outline-secondary"
                        onClick={() => setFormManifest(defaultManifest)}
                    >
                        Reset
                    </Button>
                </div>
            </Modal.Header>
            <Modal.Body>

                <Form onSubmit={(e) => {
                    if (isSubmitMode) {
                        onSubmit?.(e);
                    } else {
                        e.preventDefault(); // Prevent actual submit
                    }
                }}>

                    <Form.Group className="mb-3">

                        <Form.Label as="h5" className="mt-4 mb-3">Presentation</Form.Label>

                        <FormInputTextField id="displayName" name="displayName"
                            label="Display Name" onChange={handleManifestChange} value={formManifest.displayName} />

                        <FormInputTextArea id="description" name="description"
                            label="Description" onChange={handleManifestChange} value={formManifest.description} />

                        <Form.Label as="h5" className="mt-4 mb-3">Access</Form.Label>

                        <FormInputSelect
                            id="types" name="types" label="Types" multiple
                            value={formManifest.types} onChange={handleManifestChange}
                            options={toolTypes.map(toolType => ({
                                value: toolType,
                                label: toolType
                            }))} />      

                        <MultiInputGroup
                            type="string"
                            namePrefix="contentTypes"
                            label="Content Types"
                            values={formManifest.contentTypes || []}
                            onChange={handleManifestChange}
                        />  

                        <Form.Label as="h5" className="mt-4 mb-3">Configuration</Form.Label>

                        <FormInputTextField id="toolName" name="toolName" onChange={handleManifestChange}
                            label="Tool Name" value={formManifest.toolName} />

                        <FormInput id="toolUrl" name="toolUrl" htmlType="url" onChange={handleManifestChange}
                            label="Tool URL" value={formManifest.toolUrl} />

                        <FormInputSelect
                            id="scope" name="scope" label="Scope"
                            value={formManifest.scope} onChange={handleManifestChange}
                            options={scopes.map(scope => ({
                                value: scope,
                                label: scope
                            }))} />                            

                        <FormInputSelect
                            id="httpMethod" name="httpMethod" label="HTTP Method"
                            value={formManifest.httpMethod} onChange={handleManifestChange}
                            options={httpMethods.map(httpMethod => ({
                                value: httpMethod,
                                label: httpMethod
                            }))} />

                        <MultiInputGroup
                            type="keyValue"
                            namePrefix="toolParameters.queryParameters"
                            label="Query Parameters"
                            values={formManifest.toolParameters?.queryParameters || []}
                            onChange={handleManifestChange}
                        />

                        <MultiInputGroup
                            type="object"
                            namePrefix="allowedApiCalls"
                            label="Allowed API Calls"
                            values={formManifest.allowedApiCalls || []}
                            onChange={handleManifestChange}
                            objectSchema={[
                                { name: "name", label: "Name", type: "string" },
                                { name: "httpMethod", label: "HTTP Method", type: "select", options: httpMethods },
                                { name: "urlTemplate", label: "URL Template", type: "string" },
                                { name: "timeOut", label: "Time Out", type: "number", defaultValue: 3600 },
                            ]}
                        />

                        <MultiInputGroup
                            type="object"
                            namePrefix="requirements.auxFilesExist"
                            label="Aux Files Exists"
                            values={formManifest.requirements?.auxFilesExist || []}
                            onChange={handleManifestChange}
                            objectSchema={[
                                { name: "formatTag", label: "Format Tag", type: "string" },
                                { name: "formatVersion", label: "Format Version", type: "string" },
                            ]}
                        />
                    </Form.Group>

                    <Button
                        variant="primary"
                        type={isSubmitMode ? "submit" : "button"}
                        onClick={!isSubmitMode ? handleSave : undefined}
                    >
                        Save
                    </Button>

                    <Button variant="outline-secondary" className="ms-2"
                        onClick={handleCancel}>
                        Cancel
                    </Button>
                </Form>
            </Modal.Body>
        </Modal >

    );
}

export default EditManifestForm;
