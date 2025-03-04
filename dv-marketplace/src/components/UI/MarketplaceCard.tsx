import type { ReactNode } from 'react';
import Card from 'react-bootstrap/Card';
import useMarketplaceApiRepo from '../../repositories/useMarketplaceApiRepo';
import { Link } from 'react-router-dom';

interface CardDeckProps {
    children?: ReactNode | undefined;
    header?: string | undefined;
    imageId?: number | undefined;
    text?: string | undefined;
    link?: string | undefined;
}

export const MarketplaceCard = ({ header, imageId, text, link, children }: CardDeckProps) => {

  return (
    <div className="col-12 col-sm-6 col-md-6 col-lg-3 mb-2 px-0">
       <BaseCard header={header} imageId={imageId} text={text} link={link}>
         {children}
        </BaseCard>
    </div>
  );
};

export const RowCard = ({ header, imageId, text, link, children }: CardDeckProps) => {
    return(
    <div className="col-12">
       <BaseCard header={header} imageId={imageId} text={text} link={link}>
         {children}
        </BaseCard>
    </div>
    );
}

export const BaseCard = ({ header, imageId, text, link, children }: CardDeckProps) => {
  
  const { getImageUrl } = useMarketplaceApiRepo();
  return (
    <Card>
        {header && (
          <Card.Header>
            <h5>{header}</h5>
          </Card.Header>
        )}
        {imageId && (
          <Card.Img variant="top" src={getImageUrl(imageId)} />
        )}
        <Card.Body>
          {text && (
            <Card.Text className="card-text">
              {text.length > 100 ? `${text.substring(0, 100)}...` : text}
            </Card.Text>
          )}
          {link && (
            <Link to={link} className="btn btn-primary bi-arrow-bar-right"><span> View </span></Link>
          )}
          {children}
        </Card.Body>
      </Card>
  );
}

export default MarketplaceCard;