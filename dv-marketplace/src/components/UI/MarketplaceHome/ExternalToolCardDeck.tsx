import ExternalToolCard from "./ExternalToolCard";
import useExternalToolCardDeck from "./useExternalToolCardDeck";

const ExternalToolCardDeck = () => {

    const {
        tools
    } = useExternalToolCardDeck();


    return (
        <div className="container mt-3">
          
            <div className='row justify-content-center'>
            <div className="col-12 col-md-10 col-lg-8">
                <div className="row">

                {tools.map(tool => (
                    <ExternalToolCard key={tool.id} tool={tool} />
                ))}

                </div>
                </div>
            </div>
        </div>
    );

}

export default ExternalToolCardDeck;