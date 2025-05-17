import useEditToolForm from './useEditToolForm';
import { FormInputTextField, FormInputTextArea } from '../../UI/FormInputFields';
import { Alert, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
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
                <Button variant="primary" type="submit">
                    Update
                </Button>
                <Button variant="secondary" className="bi bi-arrow-bar-left ms-2"
                as={Link as any} to={`/view/${tool?.id}`}>
                    <span className="me-1"></span>View
                </Button>
            </form>

            <hr />
            <EditVersionForm tool={tool} />

            <hr />
            <EditImageForm tool={tool} />

        </div>
    );
};

export default EditExtToolForm;
