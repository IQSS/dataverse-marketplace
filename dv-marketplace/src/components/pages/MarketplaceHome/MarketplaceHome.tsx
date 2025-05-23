import { useState } from "react";
import CardDeck from "../../UI/CardDeck";
import { MarketplaceLinkCard } from "../../UI/MarketplaceCard";
import DvPromoCarrousel from "./DvPromoCarrousel";
import useMarketplaceHome from "./useMarketplaceHome";
function MarketplaceHome () {

    const [onlyMine, setOnlyMine] = useState(false);
    const {
        tools, userContext
    } = useMarketplaceHome(onlyMine);
    const isLoggedIn = !!userContext.user;

    return (<>
        <DvPromoCarrousel />

        <div style={{
            display: 'flex', 
            flexDirection: 'row', 
            flexWrap: 'wrap', 
            justifyContent: 'center',
            marginTop: '10px',
            minHeight: '25px',  // Space for checkbox
            }}>

                {isLoggedIn && (                
                    <label>
                        <input
                            type="checkbox"
                            checked={onlyMine}
                            onChange={(e) => setOnlyMine(e.target.checked)}
                        />
                        &nbsp;Show only my tools
                    </label>
                )}
            </div>

            <CardDeck>
                {tools.map(tool => (
                    <MarketplaceLinkCard key={tool.id}
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