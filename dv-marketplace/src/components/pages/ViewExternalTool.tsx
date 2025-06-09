import { Link } from "react-router-dom";
import axios from "axios";
import type { ExternalTool, Manifest,Image } from "../../types/MarketplaceTypes";
import { Alert } from "react-bootstrap";
import { InnerCardDeck } from "../UI/CardDeck";
import { RowCard, MarketplaceCard, BaseCard } from "../UI/MarketplaceCard";
import InstallExToolFrame from "./InstallExToolFrame";
import useViewExternalTool from "./useViewExternalTool";
import { useEffect, useRef } from "react";
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

    useEffect(() => {
        const fetchTool = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`);
                setTool(response.data as ExternalTool);
            } catch (error) {
                toast.error(`Error fetching tool`);
            }
        };
        fetchTool();
    }, [id, BASE_URL, setTool]);

    const modalRef = useRef<ModalRef>(null);

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
            {/* <Alert variant='light'> */}

                <div className='container'>
                    <hr />
                    <div className='row'>
                        <h3 className='col-6'>Releases</h3>
                    </div>
                </div>
            {/* </Alert> */}

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