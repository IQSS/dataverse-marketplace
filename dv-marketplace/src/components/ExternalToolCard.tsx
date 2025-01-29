import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { Link } from 'react-router-dom';

function ExternalToolCard() {
  return (
    <Card style={{ width: '18rem' }}>
      <Card.Img variant="top" src="ss.png" />
      <Card.Body>
        <Card.Title>AskTheData
        </Card.Title>
        <Card.Text>
        Experimental code to query tabular data with natural language queries. The LLM looks only at the content of the tabular data and not at the dataset description or any metadata.
        </Card.Text>
        <Link to="/install" className="btn btn-primary">Install Now!</Link>
      </Card.Body>
    </Card>
  );
}

export default ExternalToolCard;