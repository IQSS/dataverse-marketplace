import React from "react";
import { Alert, Modal } from "react-bootstrap";
import type { Manifest } from "../../types/MarketplaceTypes";
import { FormInput, FormInputCheckbox, FormInputTextField } from "../UI/FormInputFields";
import useInstallExToolFrame from "./useInstallExToolFrame";


function InstallExToolFrame({
    manifest,
    showModal,
    setShowModal,
}: {
    manifest?: Manifest;
    showModal?: boolean;
    setShowModal?: (show: boolean) => void;
}) {
    const {
        handleClose,
        hostnames,
        sendData,
    } = useInstallExToolFrame({ setShowModal });

    const [formData, setFormData] = React.useState({
        hostname: "",
        apiKey: "",
        useHttps: true,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, type, checked } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    return (
        <Modal show={showModal} onHide={handleClose} size="lg" centered>
            <Modal.Header closeButton>
                <Modal.Title>Install External Tool</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Alert variant="info" className="text-center">
                    <span className="bi-info-circle">
                        &nbsp; Your API key will not be stored and will only be sent to the specified Dataverse instance.
                    </span>
                </Alert>
                <div id="installFrame">
                    <div className="container">
                        <div className="row">
                            <form encType="multipart/form-data" id="installForm">
                                <FormInputTextField
                                    label="Hostname"
                                    name="hostname"
                                    id="hostname"
                                    datalist="hostnames"
                                    value={formData.hostname}
                                    onChange={handleChange}
                                />

                                <datalist id="hostnames" style={{ position: "absolute", zIndex: 1 }}>
                                    {[...new Set(hostnames)].map((hostname: string) => (
                                        <option key={hostname} value={hostname} />
                                    ))}
                                </datalist>

                                <FormInput
                                    label="API Key"
                                    name="apiKey"
                                    id="apiKey"
                                    htmlType="password"
                                    value={formData.apiKey}
                                    onChange={handleChange}
                                />

                                <FormInputCheckbox
                                    label="Use https"
                                    name="useHttps"
                                    id="useHttps"
                                    checked={formData.useHttps}
                                    onChange={handleChange}
                                />

                                <button
                                    type="button"
                                    className="btn btn-primary bi-rocket-takeoff"
                                    onClick={() => sendData(manifest, formData)}
                                >
                                    <span className="me-1"></span>Install
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </Modal.Body>
        </Modal>
    );
}

export default InstallExToolFrame;
