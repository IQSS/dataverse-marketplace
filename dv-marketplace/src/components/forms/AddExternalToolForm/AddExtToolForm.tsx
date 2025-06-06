import useAddExternalTool from './useAddExternalTool';
import { Button } from "react-bootstrap";
import { Link } from 'react-router-dom';
import { FormInputTextField, FormInputTextArea, FormInput } from '../../UI/FormInputFieldsRefactor';  
import EditManifestFormRefactor from '../EditExternalToolForm/EditManifestForm/EditManifestFormRefactor';
import type { ModalRef } from '../EditExternalToolForm/EditManifestForm/EditManifestFormRefactor';
import { useRef } from 'react';
import type { Manifest } from '../../../types/MarketplaceTypes';


const AddExtToolForm = () => {

    const modalRef = useRef<ModalRef>(null);

    const openModal = () => {
        modalRef.current?.open();   
    }

    const {
        handleSubmit,
    } = useAddExternalTool();

    const manifest: Manifest = {
        displayName: 'asd',
        description: '',
        scope: '',
        toolUrl: '',
        httpMethod: 'GET',
        contentType: '',
        toolParameters: { queryParameters: [] },
        toolName: '',
        contentTypes: [],
        types: [],
        allowedApiCalls: [],
        requirements: { auxFilesExist: [] as import('../../../types/MarketplaceTypes').AuxFilesExist[] }
    };

    return (
        <div className="container">
            <h1>Add External Tool</h1>
            <form onSubmit={handleSubmit} encType="multipart/form-data">

                <FormInputTextField label='Tool Name' name='toolName' id='toolName' />
                <FormInputTextArea label='Description' name='description' id='toolDescription' />
                <FormInputTextField label='Version Name' name='versionName' id='versionName' />
                <FormInputTextArea label='Version Note' name='versioneNote' id='versioneNote' />
                <FormInputTextField label='Dataverse Min Version' name='dataverseMinVersion' id='dataverseMinVersion' />
                <FormInput label='Images' name='images' id='images' htmlType='file' />
                <hr />
               

                <p>Manifest Data:{" "}
                    <button
                        type="button"
                        className="btn bi-pen px-0"
                        onClick={openModal}
                    />

                    <EditManifestFormRefactor ref={modalRef} manifest={manifest} />
                </p>


                <button type="submit" className="btn btn-primary">Save</button>
                <Link to="/" className="btn btn-secondary ms-2">
                    <span className="me-1">Cancel</span>
                </Link>
            </form>
        </div>
    );
};

export default AddExtToolForm;
