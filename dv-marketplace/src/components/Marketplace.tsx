import ControlledCarousel from "./Carrousel";
import ExternalToolCard from "./ExternalToolCard";

function Marketplace () {
    return (<>
        <ControlledCarousel/>
        <div id="mkt_container" className='container d-flex flex-column align-items-center'>
                <div className="p-4">
                    
                </div>
                <div className='d-flex flex-wrap gap-4'>
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                    <ExternalToolCard />
                </div>
        </div>
        </>
    );
}

export default Marketplace;
