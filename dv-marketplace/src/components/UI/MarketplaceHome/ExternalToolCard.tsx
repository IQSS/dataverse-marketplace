import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';
import type { ExternalTool } from '../../../types/MarketplaceTypes';





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
      {/* <Card.Img variant="top" src="./assets/img/ss.png" /> */}
      <Card.Body>
        <Card.Title>{tool.name}
        </Card.Title>
        <Card.Text>
        {tool.description}
        </Card.Text>
        <Link to="/install" className="btn btn-primary bi-cloud-download"><span> View </span></Link>
      </Card.Body>
    </Card>
    </div>

  );
}

export default ExternalToolCard;