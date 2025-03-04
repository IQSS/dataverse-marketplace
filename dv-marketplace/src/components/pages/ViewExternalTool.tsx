import { Link, useParams } from "react-router-dom";
import axios from "axios";
import { useContext, useEffect, useState } from "react";
import type { ExternalTool, Image } from "../../types/MarketplaceTypes";
import { Alert } from "react-bootstrap";
import { UserContext } from "../context/UserContextProvider";
import { InnerCardDeck } from "../UI/CardDeck";
import { RowCard, MarketplaceCard } from "../UI/MarketplaceCard";
import useMarketplaceApiRepo from "../../repositories/useMarketplaceApiRepo";



const ViewExternalTool = () => {

    const { id } = useParams();

    const [tool, setTool] = useState<ExternalTool | undefined>();
    const userContext = useContext(UserContext);
    const { BASE_URL } = useMarketplaceApiRepo();
    useEffect(() => {
        const fetchTool = async () => {
            try {
                const response = await axios.get(`${BASE_URL}/api/tools/${id}`);
                setTool(response.data as ExternalTool);
            } catch (error) {
                console.error("Error fetching the tool:", error);
            }
        };
        fetchTool();
    }, [id, BASE_URL]);

    return (
        <div className="container" style={{ marginTop: "120px" }}>


        <Alert variant='light'>
            <div className='container '>
                
                <div className='row'>
                    <h1 className='col-6'>{tool?.name}:</h1>
                    <div className='col-6 d-flex justify-content-end align-items-center'>
                        {userContext.user &&
                            <Link to ={`/edit/${id}`} className="btn btn-secondary bi-pen" > Edit</Link>
                        }
                    </div>
                </div>
            </div>
            </Alert>

            <div>
                <p className='col-12 d-flex '>
                    {tool?.description}
                </p>
            </div>
            <Alert variant='light'>
                <div className='container '>
                    <div className='row'>
                    <h3 className='col-6'>Releases:</h3>
                    </div>
                </div>
            </Alert>

            <InnerCardDeck>
                {tool?.versions.map((version) => (
                    <RowCard key={version.id} header={`Version: ${version.version}`}>
                        <p>Release Note : {version.releaseNote}</p>
                        <p>DV Min Version : {version.dataverseMinVersion}</p>
                        <p>Manifests:</p>
                        <table className="table">
                            <tbody>
                            {version.manifests.map((manifest) => (
                                <tr key={manifest.manifestId}>
                                    <td>{manifest.fileName}</td>
                                    <td>
                                        <Link to="" className="btn bi-download" onClick={() =>{} }> Install </Link>
                                    </td>
                                    </tr>
                                
                            ))}
                            </tbody>                            
                        </table>
                        
                    </RowCard>                        
                ))}
            </InnerCardDeck> 

            <Alert variant='light'>
                <div className='container '>
                    <div className='row'>
                    <h3 className='col-6'>Images:</h3>
                        <div className='col-6 d-flex justify-content-end align-items-center'>
                    </div>
                    </div>
                </div>
            </Alert>
            <InnerCardDeck>

                {tool?.images.map((image: Image) => (
                    <MarketplaceCard
                        key={image.id}
                        imageId={image.storedResourceId}>
                    </MarketplaceCard>
                ))}  
            </InnerCardDeck>

        </div>
    );
};

export default ViewExternalTool;