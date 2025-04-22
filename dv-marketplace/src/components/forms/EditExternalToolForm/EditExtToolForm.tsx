import useEditToolForm from './useEditToolForm';
import { FormInputTextField, FormInputTextArea } from '../../UI/FormInputFields';
import { Alert } from 'react-bootstrap';
import EditImageForm from './EditImageForm/EditImageForm';
import EditVersionForm from './EditVersionForm/EditVersionForm';

const EditExtToolForm = () => {

    const {
        handleSubmit,
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
            <EditVersionForm tool={tool}/>
            
            <hr/>
            <EditImageForm tool={tool} />

        </div>
    );
};

export default EditExtToolForm;
