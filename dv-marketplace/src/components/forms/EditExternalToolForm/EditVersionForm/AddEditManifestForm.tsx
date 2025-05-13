import React, { useState, useEffect } from 'react';
import { Modal, Button, Form, FormSelect, Row, Col } from "react-bootstrap";
import { FormInput, FormInputTextArea, FormInputTextField } from "../../../UI/FormInputFields";
import type { Manifest } from "../../../../types/MarketplaceTypes";
import MultiInputGroup from './MultiInputGroup';


const AddEditManifestForm = ({
    show,
    onCancel,
    onSubmit,
    formManifest,
}: {
    show: boolean;
    onCancel: () => void;
    onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
    formManifest: Partial<Manifest>
}) => {


    const handleCancel = () => {
        onCancel();
    };

    return (
        <Modal show={show} onHide={handleCancel} size="lg" centered backdrop="static">
            <Modal.Header closeButton>
                <Modal.Title>Add/Edit Manifest</Modal.Title>
            </Modal.Header>
            <Modal.Body>

                <Form onSubmit={(e) => {
                    onSubmit(e); 
                }}
                >
                    <Form.Group className="mb-3">

                        <FormInputTextField id="displayName" name="displayName"
                            label="Display Name" value={formManifest.displayName} />

                        <FormInputTextArea id="description" name="description"
                            label="Description" value={formManifest.description} />

                        <Form.Group controlId="scope" className="mb-3">
                            <Form.Label>Scope</Form.Label>
                            <FormSelect name="scope" defaultValue={formManifest.scope}>
                                <option value="file">file</option>
                                <option value="dataset">dataset</option>
                            </FormSelect>
                        </Form.Group>

                        <FormInput id="toolUrl" name="toolUrl" htmlType="url"
                            label="Tool URL" value={formManifest.toolUrl} />

                        <Form.Group controlId="scope" className="mb-3">
                            <Form.Label>HTTP Method</Form.Label>
                            <FormSelect name="httpMethod" defaultValue={formManifest.httpMethod}>
                                <option value="GET">GET</option>
                                <option value="POST">POST</option>
                            </FormSelect>
                        </Form.Group>

                        <FormInputTextField id="toolName" name="toolName"
                            label="Tool Name" value={formManifest.toolName} />

                        <Form.Group controlId="types" className="mb-2">
                            <Form.Label>Types</Form.Label>
                            <Form.Select
                                name="types"
                                multiple
                                defaultValue={formManifest.types}>
                                <option value="preview">preview</option>
                                <option value="explore">explore</option>
                                <option value="config">config</option>
                                <option value="query">query</option>
                            </Form.Select>
                        </Form.Group>

                        <MultiInputGroup
                            type="string"
                            namePrefix="contentTypes"
                            label="Content Types"
                            initialValues={formManifest.contentTypes || []}
                        />


                        <MultiInputGroup
                            type="keyValue"
                            namePrefix="queryParameters"
                            label="Query Parameters"
                            initialValues={formManifest.toolParameters?.queryParameters || []}
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

export default AddEditManifestForm;

