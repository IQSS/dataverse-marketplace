import React, { useRef } from 'react';
import { Modal, Button, Form } from "react-bootstrap";
import { FormInput, FormInputTextArea, FormInputTextField, FormInputSelect } from "../../../UI/FormInputFields";
import type { Manifest } from "../../../../types/MarketplaceTypes";
import MultiInputGroup from './MultiInputGroup';


const EditManifestForm = ({
    show,
    onCancel,
    onSubmit,
    onUpload,
    onReset,
    onChange,
    formManifest,
}: {
    show: boolean;
    onCancel: () => void;
    onUpload: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onReset: () => void;
    onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
    onChange: (e: React.ChangeEvent<any>) => void;
    formManifest: Manifest
}) => {

    const fileInputRef = useRef<HTMLInputElement>(null);

    const handleCancel = () => {
        onCancel();
    };

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
                            onUpload(e);
                            e.target.value = ''; 
                        }} />

                    <Button
                        variant="outline-secondary"
                        onClick={() => {
                            onReset();
                        }} >
                        Reset
                    </Button>
                </div>
            </Modal.Header>
            <Modal.Body>

                <Form onSubmit={(e) => {
                    onSubmit(e);
                }}
                >
                    <Form.Group className="mb-3">

                        <FormInputTextField id="displayName" name="displayName"
                            label="Display Name" onChange={onChange} value={formManifest.displayName} />

                        <FormInputTextArea id="description" name="description"
                            label="Description" onChange={onChange} value={formManifest.description} />

                        <FormInputSelect
                            id="scope" name="scope" label="Scope"
                            value={formManifest.scope} onChange={onChange}
                            options={[
                                { value: "file", label: "file" },
                                { value: "dataset", label: "dataset" },
                            ]} />

                        <FormInput id="toolUrl" name="toolUrl" htmlType="url" onChange={onChange}
                            label="Tool URL" value={formManifest.toolUrl} />

                        <FormInputSelect
                            id="httpMethod" name="httpMethod" label="HTTP Method"
                            value={formManifest.httpMethod} onChange={onChange}
                            options={[
                                { value: "GET", label: "GET" },
                                { value: "POST", label: "POST" },
                            ]} />

                        <FormInputTextField id="toolName" name="toolName" onChange={onChange}
                            label="Tool Name" value={formManifest.toolName} />

                        <FormInputSelect
                            id="types" name="types" label="Types" multiple
                            value={formManifest.types} onChange={onChange}
                            options={[
                                { value: "preview", label: "preview" },
                                { value: "explore", label: "explore" },
                                { value: "configure", label: "configure" },
                                { value: "query", label: "query" },
                            ]} />

                        <MultiInputGroup
                            type="string"
                            namePrefix="contentTypes"
                            label="Content Types"
                            values={formManifest.contentTypes || []}
                            onChange={onChange}
                        />

                        <MultiInputGroup
                            type="keyValue"
                            namePrefix="toolParameters.queryParameters"
                            label="Query Parameters"
                            values={formManifest.toolParameters?.queryParameters || []}
                            onChange={onChange}
                        />

                        <MultiInputGroup
                            type="object"
                            namePrefix="allowedApiCalls"
                            label="Allowed API Calls"
                            values={formManifest.allowedApiCalls || []}
                            onChange={onChange}
                            objectSchema={[
                                { name: "name", label: "Name", type: "string" },
                                { name: "httpMethod", label: "HTTP Method", type: "select", options: ["GET", "POST"] },
                                { name: "urlTemplate", label: "URL Template", type: "string" },
                                { name: "timeOut", label: "Time Out", type: "number" },
                            ]}
                        />

                        <MultiInputGroup
                            type="object"
                            namePrefix="requirements.auxFilesExist"
                            label="Aux Files Exists"
                            values={formManifest.requirements?.auxFilesExist || []}
                            onChange={onChange}
                            objectSchema={[
                                { name: "formatTag", label: "Format Tag", type: "string" },
                                { name: "formatVersion", label: "Format Version", type: "string" },
                            ]}
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit">
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

