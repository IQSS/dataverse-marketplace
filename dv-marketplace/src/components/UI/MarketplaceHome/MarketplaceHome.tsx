import ControlledCarousel from "./Carrousel";
import ExternalToolCardDeck from "./ExternalToolCardDeck";

function MarketplaceHome () {
    
    return (<>
        <div className="container justify-content-center">
            <div className="col-12">
            <img className="card-img-top" src="/assets/img/promo-unc.png" alt="Description of the image" />
            </div>
                
        </div>
        
        <ExternalToolCardDeck />
        </>
    );
}

export default MarketplaceHome;