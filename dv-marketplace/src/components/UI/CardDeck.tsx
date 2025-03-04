import type { ReactNode } from "react";

interface CardDeckProps {
    children: ReactNode;
}

export const CardDeck = ({ children }: CardDeckProps) => {
    return (
        <div className="container-fluid justify-content-center" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center'}}>
            <div className='row col-xl-8 col-lg-10 col-md-12 col-sm-12 col-12'>
                {children}
            </div>
        </div>
    );
};

export const InnerCardDeck = ({ children }: CardDeckProps) => {
    return (
        <div className="container-fluid justify-content-center" style={{display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center'}}>
            <div className='row col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>
                {children}
            </div>
        </div>
    );
}

export default CardDeck;

