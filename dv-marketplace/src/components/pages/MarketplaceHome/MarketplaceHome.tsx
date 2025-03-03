import CardDeck from "../../UI/CardDeck";
import MarketplaceCard from "../../UI/MarketplaceCard";
import DvPromoCarrousel from "./DvPromoCarrousel";
import useMarketplaceHome from "./useMarketplaceHome";
function MarketplaceHome () {

    const {
        tools
    } = useMarketplaceHome();
    
    return (<>
        {/* <div className="container justify-content-center">
            <div className="col-12">
                <img className="card-img-top" src="/assets/img/promo-unc.png" alt="DCM at UNC" />
            </div>
        </div> */}
        <DvPromoCarrousel />
        <CardDeck>
            {tools.map(tool => (
                <MarketplaceCard key={tool.id}
                   header={tool.name}
                   imageId={tool?.images[0]?.storedResourceId}
                   text={tool.description}
                   link={`/view/${tool.id}`}
                   />
            ))}
        </CardDeck>
        </>
    );
}

export default MarketplaceHome;