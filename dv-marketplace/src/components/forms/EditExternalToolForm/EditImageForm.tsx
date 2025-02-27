import { Alert, Button, Form } from "react-bootstrap";
import useEditImageForm from "./useEditImageForm";
import { BASE_URL } from "/config";

const EditImageForm = ({ tool }) => {

    const {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete

    } = useEditImageForm();

    return (
        <>
        <Alert variant='light'>
            <div className='container '>
                <div className='row'>
                <h3 className='col-6'>Images:</h3>
                <div className='col-6 d-flex justify-content-end align-items-center'>
                    <button type='button' className='btn btn-secondary bi-plus' onClick={() => setAddImageFormIsOpen(true)}> Add new </button>       
                </div>
                </div>
            </div>
        </Alert>

        <Alert variant='info' show={addImageFormIsOpen}>
            <Form onSubmit={handleImageSubmit} encType="multipart/form-data">
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Image</Form.Label>
                    <Form.Control type="file" name="images" />
                </Form.Group>
                <Button variant="primary" type="submit" >
                    Submit
                </Button>
            </Form>
        </Alert>

        <ul>

            {tool?.imagesResourceId.map((imageId, index) => (
                <li key={index}>
                    <img src={`${BASE_URL}/api/stored-resource/${imageId}`} alt={tool?.name} />
                    <button type='button' className='btn bi-trash px-0' onClick={() => handleImageDelete(imageId)}><span/></button>
                    
                </li>
            ))}
        </ul>
        </>
    );
};

export default EditImageForm;