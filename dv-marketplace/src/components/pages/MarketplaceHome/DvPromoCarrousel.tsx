import Carousel from 'react-bootstrap/Carousel';
import CarouselImage from './CarouselImage';

function DvPromoCarrousel() {
  
  return (

    <div className="container-fluid dv-carousel" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center'}}>
      <div className='row col-xl-8 col-lg-10 col-md-12 col-sm-12 col-12'>
        <Carousel fade className='px-0'>
          <Carousel.Item>
            <CarouselImage title="Dataverse Community Meeting 2025"
            text = "UNC (June 10th - June 13th)"
            url="/assets/img/promo-unc.png" />       
          </Carousel.Item>  
          <Carousel.Item>
            <CarouselImage title="Dataverse 6.6"
            text = "March 2025"
            url="/assets/img/dv-release.png" />
          </Carousel.Item>
        </Carousel>
      </div>
    </div>
    
  );
}

export default DvPromoCarrousel;