import { Alert, Button, Form } from "react-bootstrap";
import useEditImageForm from "./useEditImageForm";
import { InnerCardDeck } from "../../../UI/CardDeck";
import MarketplaceCard from "../../../UI/MarketplaceCard";
import type { ExternalTool, Image } from "../../../../types/MarketplaceTypes";
import { useContext, useState } from "react";
import { UserContext } from "../../../context/UserContextProvider";
import SectionHeader from "../../../UI/SectionHeader";
import { toast } from "react-toastify";


const EditImageForm = ({ tool }: { tool: ExternalTool | undefined }) => {

    const MAX_FILE_SIZE = 1 * 1024 * 1024; // 1MB
    const [selectedFile, setSelectedFile] = useState<FileList | null>(null);

    const {
        addImageFormIsOpen,
        setAddImageFormIsOpen,
        handleImageSubmit,
        handleImageDelete,
        images
    } = useEditImageForm({ tool });

    const userContext = useContext(UserContext);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        if (files) {
            for (let i = 0; i < files.length; i++) {
                if (files[i].size > MAX_FILE_SIZE) {
                    toast.error(`File "${files[i].name}" is too large. Maximum size is 1MB.`);
                    e.target.value = '';
                    setSelectedFile(null);
                    return;
                }
            }
            setSelectedFile(files);
        }
    };


    return (
        <>
            <SectionHeader header="Images:" setAddFormIsOpen={setAddImageFormIsOpen} />

            <Alert variant='info' show={addImageFormIsOpen}>
                <Form onSubmit={handleImageSubmit} encType="multipart/form-data">
                    <Form.Group className="mb-3">
                        <Form.Label>Image</Form.Label>
                        <Form.Control
                            type="file"
                            name="images"
                            multiple
                            accept="image/*"
                            onChange={handleFileChange}
                        />
                        <Form.Text className="text-muted">
                            Maximum file size: 1MB
                        </Form.Text>
                    </Form.Group>
                    <Button variant="primary" type="submit" disabled={!selectedFile}>
                        Save
                    </Button>
                    <Button variant="outline-secondary" className="ms-2"
                        onClick={() => {
                            setAddImageFormIsOpen(false);
                            setSelectedFile(null);
                        }}>
                        Cancel
                    </Button>
                </Form>
            </Alert>

            <InnerCardDeck>

                {images.map((image: Image) => (
                    <MarketplaceCard
                        key={image.imageId}
                        imageId={image.storedResourceId}>
                        {userContext.user &&
                            <button type='button' className='btn bi-trash px-0' onClick={() => handleImageDelete(image.storedResourceId)}><span /></button>
                        }
                    </MarketplaceCard>
                ))}
            </InnerCardDeck>





        </>
    );
};

export default EditImageForm;