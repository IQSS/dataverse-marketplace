import { Link } from "react-router-dom";
import axios from "axios";
import type { ExternalTool, Manifest,Image } from "../../types/MarketplaceTypes";
import { Alert } from "react-bootstrap";
import { InnerCardDeck } from "../UI/CardDeck";
import { RowCard, MarketplaceCard, BaseCard } from "../UI/MarketplaceCard";
import InstallExToolFrame from "./InstallExToolFrame";
import useViewExternalTool from "./useViewExternalTool";
import { useEffect, useRef, useState } from "react";
import ImagesCarrouselView, { ModalRef } from "./ImagesCarrouselView";
import { toast } from "react-toastify";



const ViewExternalTool = () => {

    const {
        showModal,
        setShowModal,
        id,
        tool,
        setTool,
        userContext,
        BASE_URL,
        toolToInstall,
        setToolToInstall,
        downloadManifest
    } = useViewExternalTool();

    const fetchKeyRef = useRef<string>('');
    const [notFound, setNotFound] = useState(false);

    useEffect(() => {
        const fetchTool = async () => {
            if (!id) return;

            const currentFetchKey = `${id}-${userContext.user?.accessToken || 'no-token'}`;
            if (fetchKeyRef.current === currentFetchKey) return;
            fetchKeyRef.current = currentFetchKey;

            try {
                const headers = userContext.user?.accessToken
                    ? { Authorization: `Bearer ${userContext.user.accessToken}` }
                    : {};
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`, { headers });
                setTool(response.data as ExternalTool);
                setNotFound(false);
            } catch (error) {
                if (axios.isAxiosError(error) && error.response?.status === 404) {
                    setNotFound(true);
                } else {
                    toast.error(`Error fetching tool`);
                }
            }
        };
        fetchTool();
    }, [id, BASE_URL, userContext.user?.accessToken]);

    const modalRef = useRef<ModalRef>(null);

    if (notFound) {
        return (
            <div className="container" style={{ marginTop: "120px" }}>
                <Alert variant="warning">
                    <h4>Tool Not Found</h4>
                    <p>The tool you're looking for doesn't exist or you don't have permission to view it.</p>
                    <Link to="/" className="btn btn-primary">Return to Home</Link>
                </Alert>
            </div>
        );
    }

    const openModal = (selected: number) => {
        modalRef.current?.open(selected);
    }

    return (
        <div className="container" style={{ marginTop: "120px" }}>

        {userContext.user &&
        ( userContext.user.roles.includes("ADMIN") || tool?.ownerId == userContext.user.id)  &&
        <Alert variant='light' className="d-flex justify-content-end">
            <Link to ={`/edit/${id}`} className="btn btn-secondary bi-pen" > Edit</Link>
        </Alert>
        }

             <div className="container-fluid" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'left'}}>
                <div className='row col-12'>
                    <div className='col-3'>
                    <BaseCard
                    header={tool?.name}
                    imageId={tool?.images[0]?.imageId}
                    status={tool?.status}
                    />
                    </div>
                    <p className="col-9 mb-2 py-10" style={{padding: '10px'}}>
                        {tool?.description}
                    </p>
                </div>
            </div>

            <div>
                <p className='col-12 d-flex '>

                </p>
            </div>

                <div className='container'>
                    <hr />
                    <div className='row'>
                        <h3 className='col-6'>Releases</h3>
                    </div>
                </div>

            <InnerCardDeck>
                {tool?.versions.map((version) => (
                    <RowCard key={version.id} header={`Version Name: ${version.versionName}`}>
                        <p>Version Note : {version.versionNote}</p>
                        <p>Dataverse Min Version : {version.dataverseMinVersion}</p>
                        <p>Manifests:</p>
                        <table className="table">
                            <tbody>
                                {(version.manifestSet ?? []).map((manifest: Manifest) => 
                                        <tr key={version.id + "_" + manifest.contentType}>
                                            <td>
                                               {manifest.displayName}{manifest.contentType != null ? " (" + manifest.contentType + ")": ""}
                                            </td>
                                            <td>
                                                <button
                                                    type="button"
                                                    className="btn bi-rocket-takeoff"
                                                    onClick={() => {
                                                        setToolToInstall(manifest);
                                                        setShowModal(true);
                                                    }}
                                                >
                                                    <span className="me-1"></span>Install
                                                </button>

                                                <button
                                                    type="button"
                                                    className="btn bi-download"
                                                    onClick={() => {
                                                        downloadManifest(manifest);
                                                    }}
                                                >
                                                   <span className="me-1"></span>Download
                                                </button>
                                            </td>
                                        </tr>
                                )}
                            </tbody>
                        </table>

                    </RowCard>
                ))}
            </InnerCardDeck>

            <br />

                <div className='container '>
                    <hr />
                    <div className='row'>
                        <h3 className='col-6'>Images</h3>
                        <div className='col-6 d-flex justify-content-end align-items-center'>
                        </div>
                    </div>
                    
                </div>
            <ImagesCarrouselView ref={modalRef} images={tool?.images} selected={0} />
            <InnerCardDeck>
                
                {tool?.images.map((image: Image, idx: number) => (
                    <>
                    {/* <div onClick={() => openModal(idx)} style={{ cursor: "pointer" }}> */}
                        <MarketplaceCard
                            key={image.imageId}
                            imageId={image.storedResourceId}
                            onClick={() => openModal(idx)}>
                        </MarketplaceCard>
                    {/* </div> */}
                    </>
                ))}
            </InnerCardDeck>

            <InstallExToolFrame manifest={toolToInstall} showModal={showModal} setShowModal={setShowModal} />
            




        </div>
    );
};

export default ViewExternalTool;