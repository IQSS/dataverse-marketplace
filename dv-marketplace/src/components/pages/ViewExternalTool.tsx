import { Link, useParams } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import type { ExternalTool } from "../../types/MarketplaceTypes";
import { BASE_URL } from "/config";


const ViewExternalTool = () => {

    const { id } = useParams();

    const [tool, setTool] = useState<ExternalTool | null>(null);

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
    }, [id]);

    return (
        <div className="container" style={{ marginTop: "120px" }}>
            <div className="row">
                <div className="col-6"> <h1>{tool?.name} </h1>
                </div>
                <div className="col-6 d-flex justify-content-end">
                    <Link to ={`/edit/${id}`} className="btn btn-primary bi-pen" style={{ borderRadius: "90px", padding: "15px" }}> Edit</Link>
                </div>
            </div>
            <div className="row">
                <hr />
                <div className="col-12 col-md-12">
                    <h3>Description:</h3>
                    <p>{tool?.description}</p>
                    <h4>Latest version: {tool?.versions[0].version}</h4>
                    <p>{tool?.versions[0].releaseNote}</p>
                    <h5>Dataverse required version:</h5>
                    <p>{tool?.versions[0].dataverseMinVersion}</p>
                    <img src={`${BASE_URL}/api/stored-resource/${tool?.images[0]?.storedResourceId}`} alt={tool?.name} />
                    
                   
                </div>
            </div>
            

        </div>
    );
};

export default ViewExternalTool;