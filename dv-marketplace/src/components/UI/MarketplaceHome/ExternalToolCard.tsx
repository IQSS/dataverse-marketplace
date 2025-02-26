import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';
import type { ExternalTool } from '../../../types/MarketplaceTypes';
import { BASE_URL } from "/config";


export interface ExternalToolCardProps {
  tool: ExternalTool;
}

function ExternalToolCard({ tool }: ExternalToolCardProps) {
  return (
    <div className="col-12 col-sm-6 col-md-6 col-lg-3 mb-2 px-0">
      <Card>
        <Card.Header>
          <h5>{tool.name}</h5>
        </Card.Header>
        <Card.Img variant="top" src={`${BASE_URL}/api/stored-resource/${tool.imagesResourceId[0]}`} />
        <Card.Body>
          <Card.Text>
          {tool.description}
          </Card.Text>
          <Link to={`/view/${tool.id}`} className="btn btn-primary bi-arrow-bar-right"><span> View </span></Link>
        </Card.Body>
      </Card>
    </div>

  );
}

export default ExternalToolCard;