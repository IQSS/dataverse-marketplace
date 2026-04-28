import { useState, useEffect } from "react";
import CardDeck from "../../UI/CardDeck";
import { MarketplaceLinkCard } from "../../UI/MarketplaceCard";
import DvPromoCarrousel from "./DvPromoCarrousel";
import useMarketplaceHome from "./useMarketplaceHome";
function MarketplaceHome () {

    const [onlyMine, setOnlyMine] = useState(false);
    const [selectedScope, setSelectedScope] = useState<string | null>(null);
    const [selectedType, setSelectedType] = useState<string | null>(null);
    const [selectedStatus, setSelectedStatus] = useState<string | null>(null);
    const {
        tools, userContext
    } = useMarketplaceHome(onlyMine, selectedScope, selectedType, selectedStatus);
    const isLoggedIn = !!userContext.user;
    const isAdmin = userContext.user?.roles.includes("ADMIN");
    const hasFilters = selectedScope !== null || selectedType !== null || selectedStatus !== null;

    // Reset status filter when user loses admin privileges
    useEffect(() => {
        if (!isAdmin && selectedStatus !== null) {
            setSelectedStatus(null);
        }
    }, [isAdmin, selectedStatus]);

    return (<>
        <DvPromoCarrousel />

        <div className="container-fluid" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center'}}>
            <div className='col-xl-8 col-lg-10 col-md-12 col-sm-12 col-12' style={{
                display: 'flex',
                flexDirection: 'row',
                flexWrap: 'wrap',
                justifyContent: 'space-between',
                marginTop: '10px',
                minHeight: '25px',
                gap: '15px',
                }}>

                    <div style={{ display: 'flex', gap: '15px' }}>
                        {isLoggedIn && (
                            <label>
                                <input
                                    type="checkbox"
                                    checked={onlyMine}
                                    onChange={(e) => {
                                        setOnlyMine(e.target.checked);
                                        if (e.target.checked) {
                                            setSelectedScope(null);
                                            setSelectedType(null);
                                            setSelectedStatus(null);
                                        }
                                    }}
                                    disabled={hasFilters}
                                />
                                &nbsp;Show only my tools
                            </label>
                        )}
                    </div>

                    <div style={{ display: 'flex', gap: '15px' }}>
                        {isAdmin && (
                            <select
                                value={selectedStatus || ''}
                                onChange={(e) => {
                                    setSelectedStatus(e.target.value || null);
                                    if (e.target.value) setOnlyMine(false);
                                }}
                                disabled={onlyMine}
                            >
                                <option value="">All Status</option>
                                <option value="draft">Draft</option>
                                <option value="public">Public</option>
                            </select>
                        )}

                        <select
                            value={selectedScope || ''}
                            onChange={(e) => {
                                setSelectedScope(e.target.value || null);
                                if (e.target.value) setOnlyMine(false);
                            }}
                            disabled={onlyMine}
                        >
                            <option value="">All Scopes</option>
                            <option value="dataset">Dataset</option>
                            <option value="file">File</option>
                        </select>

                        <select
                            value={selectedType || ''}
                            onChange={(e) => {
                                setSelectedType(e.target.value || null);
                                if (e.target.value) setOnlyMine(false);
                            }}
                            disabled={onlyMine}
                        >
                            <option value="">All Types</option>
                            <option value="preview">Preview</option>
                            <option value="explore">Explore</option>
                            <option value="configure">Configure</option>
                            <option value="query">Query</option>
                        </select>
                    </div>
                </div>
            </div>

            <CardDeck>
                {Array.isArray(tools) && tools.map(tool => (
                    <MarketplaceLinkCard key={tool.id}
                    header={tool.name}
                    imageId={tool?.images[0]?.storedResourceId}
                    text={tool.description}
                    link={`/view/${tool.id}`}
                    status={tool.status}
                    />
                ))}
            </CardDeck>

        </>
    );
}

export default MarketplaceHome;