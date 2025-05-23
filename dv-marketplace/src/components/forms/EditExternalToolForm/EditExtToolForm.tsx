import React from 'react';
import useEditToolForm from './useEditToolForm';
import { FormInputTextField, FormInputTextArea } from '../../UI/FormInputFields';
import { Alert, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import EditImageForm from './EditImageForm/EditImageForm';
import EditVersionForm from './EditVersionForm/EditVersionForm';

const EditExtToolForm = () => {

    const {
        handleSubmit,
        handleDelete,
        tool,
    } = useEditToolForm();

    const [formData, setToolData] = React.useState({ name: '', description: '' });

    React.useEffect(() => {
        if (tool) {
            setToolData({
                name: tool.name || '',
                description: tool.description || '',
            });
        }
    }, [tool]);

    const handleToolChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setToolData(prev => ({ ...prev, [name]: value }));
    };


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
                <FormInputTextField label="Tool Name" name="name" id="toolName" value={formData.name} onChange={handleToolChange} />
                <FormInputTextArea label="Description" name="description" id="toolDescription" value={formData.description} onChange={handleToolChange} />

                <div className="d-flex align-items-center">
                    <div>
                        <Button variant="primary" type="submit">
                            Update
                        </Button>
                        <Button
                            variant="secondary"
                            className="bi bi-arrow-bar-left ms-2"
                            as={Link as any}
                            to={`/view/${tool?.id}`}
                        >
                            <span className="me-1"></span>View
                        </Button>
                    </div>
                    <div className="ms-auto">
                        <Button
                            variant="outline-secondary"
                            className="bi bi-trash"
                            onClick={handleDelete}
                        >
                            <span className="me-1"></span>Delete
                        </Button>
                    </div>
                </div>

            </form>

            <hr />
            <EditVersionForm tool={tool} />

            <hr />
            <EditImageForm tool={tool} />

        </div>
    );
};

export default EditExtToolForm;
