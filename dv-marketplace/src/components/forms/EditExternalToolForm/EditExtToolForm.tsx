import Modal from 'react-bootstrap/Modal';
import useEditToolForm from './useEditToolForm';
import { FormInputTextField, FormInputTextArea } from '../../UI/FormInputFields';
import { Link } from 'react-router-dom';
import { Accordion, Alert, Button, Form } from 'react-bootstrap';
import { BASE_URL } from '/config';
import EditImageForm from './EditImageForm';

const EditExtToolForm = () => {

    const {
        handleSubmit,
        modalIsOpen,
        modalMessage,
        closeModal,
        tool,
        
    } = useEditToolForm();

    return (
        <div className="container" style={{ marginTop: "120px" }}>

            <Alert variant='light'>
                <div className='container '>
                    <div className='row'>
                    <h1 className='col-6'>{tool?.name}:</h1>
                    </div>
                </div>
            </Alert>

            
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <FormInputTextField label="Tool Name" name="name" id="toolName" value={tool?.name} />
                <FormInputTextArea label="Description" name="description" id="toolDescription" value={tool?.description} />
                <button type="submit" className="btn btn-primary">Update</button>
            </form>   
            <hr/>

            <Alert variant='light'>
                <div className='container '>
                    <div className='row'>
                    <h3 className='col-6'>Versions:</h3>
                    <div className='col-6 d-flex justify-content-end align-items-center'>
                        <Link to={`/edit/${tool?.id}/images/`} className='btn btn-secondary bi-plus' style={{ whiteSpace: 'nowrap' }}> Add new </Link>        
                    </div>
                    </div>
                </div>
            </Alert>
            
            <ul>
                
                {tool?.versions.map((version, index) => (
                    <li key={index}>
                        Version: {version.version}
                        <Link to={`/edit/${tool?.id}/versions/`} className='btn bi-pen px-0'><span/></Link>
                        <Link to={`/edit/${tool?.id}/versions/`} className='btn bi-trash px-0'><span/></Link>
                    </li>
                ))}

            </ul>
            
            <hr/>

            <EditImageForm tool={tool} />
            

            

            
        </div>
    );
};

export default EditExtToolForm;
