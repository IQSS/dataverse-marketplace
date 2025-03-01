import useEditVersionForm from './useEditVersionForm';
import { Alert, Button, Form } from 'react-bootstrap';
import type { ExternalTool } from '../../../../types/MarketplaceTypes';
import MarketplaceCard from '../../../UI/MarketplaceCard';
import { FormInputTextArea, FormInputTextField } from '../../../UI/FormInputFields';
import {InnerCardDeck} from '../../../UI/CardDeck';
import { useState } from 'react';
const EditVersionForm = ({ tool }: { tool: ExternalTool | undefined }) => {
    

    const {
        handleVersionSubmit,
        addVersionFormIsOpen,
        setAddVersionFormIsOpen,
        handleVersionDelete,
        userContext,
        handleVersionEdit,
        showVersionEdit,
        setShowVersionEdit
    } = useEditVersionForm({ tool });

    



    return (
        <>
        <Alert variant='light'>
            <div className='container '>
                <div className='row'>
                <h3 className='col-6'>Versions:</h3>
                <div className='col-6 d-flex justify-content-end align-items-center'>
                    {userContext.user &&
                        <button type='button' className='btn btn-secondary bi-plus' onClick={() => setAddVersionFormIsOpen(true)}> Add new </button>       
                    }
                </div>
                </div>
            </div>
        </Alert>

        <Alert variant='info' show={addVersionFormIsOpen}>
            <Form onSubmit={handleVersionSubmit} encType="multipart/form-data">
                <FormInputTextArea label="Release Note" name="releaseNote" id="releaseNote" />
                <FormInputTextField label="DV Min Version" name="dvMinVersion" id="dvMinVersion" />
                <FormInputTextField label="Version" name="version" id="version" />
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Manifests</Form.Label>
                    <Form.Control type="file" name="jsonData" multiple />
                </Form.Group>
                <Button variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </Alert>
        <ul>
        <InnerCardDeck>
            {tool?.versions.map((version) => (
                <MarketplaceCard key={version.id} header={`Version: ${version.version}`}>

                    { !(showVersionEdit === version.id) &&
                       <div >
                            <p>Release Note : {version.releaseNote}</p>
                            <p>DV Min Version : {version.dataverseMinVersion}</p>
                            <p>Manifests:</p>
                            <ul>
                                <li>{version.manifests[0].fileName} </li>
                                <li>{version.manifests[0].manifestId} </li>
                                <li>{version.manifests[0].storedResourceId} </li>
                            </ul>

                            <>
                            {/* <Link to={`/edit/${tool?.id}/versions/`} className='btn bi-pen px-0'><span/></Link> */}
                            <button type='button' className='btn bi-pen px-0' onClick={() => setShowVersionEdit(version.id)}><span/></button>
                            <button type='button' className='btn bi-trash px-0' onClick={() => handleVersionDelete(version.id)}><span/></button>
                            </>
                        </div>
                        
                    }
                    

                    
                    
                    <Alert variant='light' show={showVersionEdit === version.id}>
                        <Form onSubmit={(e) => handleVersionEdit(e, version.id)} >
                            
                            <FormInputTextArea label="Release Note" name="releaseNote" id={`releaseNote-${version.id}`} value={version.releaseNote} />
                            <FormInputTextField label="DV Min Version" name="dvMinVersion" id={`dvMinVersion-${version.id}`} value={version.dataverseMinVersion} />
                            <FormInputTextField label="Version" name="version" id={`version-${version.id}`} value={version.version} />
                            
                            <Button variant="primary" type="submit">
                                Save Changes
                            </Button>
                        </Form>
                    </Alert>

                    


                </MarketplaceCard>                        
            ))}
        </InnerCardDeck> 
        </ul>
        </>        
    );
};

export default EditVersionForm;
