import ExternalToolCard from "./ExternalToolCard";
import useExternalToolCardDeck from "./useExternalToolCardDeck";

const ExternalToolCardDeck = () => {

    const {
        tools
    } = useExternalToolCardDeck();


    return (
        <div className="container-fluid">
            <div className='row justify-content-center'>
                    {tools.map(tool => (
                        <ExternalToolCard key={tool.id} tool={tool} />
                    ))}
            </div>
        </div>
    );

}

export default ExternalToolCardDeck;