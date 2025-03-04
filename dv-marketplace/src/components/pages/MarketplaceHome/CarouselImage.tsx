import { Carousel } from 'react-bootstrap';

export interface PromoCarrouselItemContenProps {
  title?: string | undefined;
  text: string;
  url: string;
}

const CarouselImage = ({title, text, url}:PromoCarrouselItemContenProps ) => (
  <div>
    <img className="card-img-top"
            src={url}
            alt="DCM at UNC" />
   <Carousel.Caption>
      <h3>{title}</h3>
      <p>{text}</p>
    </Carousel.Caption>
  </div>

);
export default CarouselImage;