import ControlledCarousel from "./Carrousel";
import ExternalToolCard from "./ExternalToolCard";

function Marketplace () {
    return (
        <div className='flex flex-col justify-center items-center'>
            <ControlledCarousel />
            <div className='grid grid-cols-4 gap-4'>
                        <ExternalToolCard />
                        <ExternalToolCard />
                        <ExternalToolCard />
                        <ExternalToolCard />
            </div>
        </div>
    );
}

export default Marketplace;
