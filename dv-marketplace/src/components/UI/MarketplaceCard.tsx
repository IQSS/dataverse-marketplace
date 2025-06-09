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
    onClick?: () => void | undefined;
}

export const MarketplaceLinkCard = ({ header, imageId, text, link, children }: CardDeckProps) => {
  return (
    <div className="col-12 col-sm-6 col-md-6 col-lg-3 mb-2 px-0">
      <Link to={link ?? ""} className="text-decoration-none">
       <BaseCard header={header} imageId={imageId} text={text} link={link}>
         {children}
       </BaseCard>
      </Link>
    </div>
  );
}

export const MarketplaceCard = ({ header, imageId, text, link, children, onClick }: CardDeckProps) => {

  return (
    <div className="col-12 col-sm-6 col-md-6 col-lg-3 mb-2 px-0"
        onClick={onClick} style={{ cursor: onClick ? 'pointer' : 'default' }}>
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

export const BaseCard = ({ header, imageId, text, children }: CardDeckProps) => {
  
  const { getImageUrl } = useMarketplaceApiRepo();
  return (    
    
      <Card>
          {header && (
            <Card.Header>
              <h5 className="tumbnail-title">{header}</h5>
            </Card.Header>
          )}
          {imageId && (
            <div className='thumbnail-image-wrapper'>
            <Card.Img variant="top" src={getImageUrl(imageId)} className="rounded-5 p-1" 
            style={{ maxHeight: '200px', minHeight: '100px', objectFit: 'scale-down' }}/>
            </div>
          )}
          <Card.Body>
            {text && (
              <Card.Text className="thumbnail-caption">
                <span>
                {text.length > 100 ? `${text.substring(0, 100)}...` : text}
                </span>
              </Card.Text>
            )}
            {children}
          </Card.Body>
        </Card>
      
  );
}

export default MarketplaceCard;