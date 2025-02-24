import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';
import type { ExternalTool } from '../../../types/MarketplaceTypes';




export interface ExternalToolCardProps {
  tool: ExternalTool;
}

function ExternalToolCard({ tool }: ExternalToolCardProps) {
  return (
    <Card className="col-12 col-sm-6 col-md-6 col-lg-3 mb-3" style={{  }}>
      <Card.Img variant="top" src="./assets/img/ss.png" />
      <Card.Body>
        <Card.Title>{tool.name}
        </Card.Title>
        <Card.Text>
        {tool.description}
        </Card.Text>
        <Link to="/install" className="btn btn-primary">Install Now!</Link>
      </Card.Body>
    </Card>

  );
}

export default ExternalToolCard;