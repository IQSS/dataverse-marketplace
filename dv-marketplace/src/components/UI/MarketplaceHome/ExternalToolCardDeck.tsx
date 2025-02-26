import ExternalToolCard from "./ExternalToolCard";
import useExternalToolCardDeck from "./useExternalToolCardDeck";

const ExternalToolCardDeck = () => {

    const {
        tools
    } = useExternalToolCardDeck();


    return (
        <div className="container-fluid justify-content-center" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center'}}>
            <div className='row col-xl-8 col-lg-10 col-md-12 col-sm-12 col-12'>
                {tools.map(tool => (
                    <ExternalToolCard key={tool.id} tool={tool} />
                ))}
            </div>
        </div>
    );
}

export default ExternalToolCardDeck;