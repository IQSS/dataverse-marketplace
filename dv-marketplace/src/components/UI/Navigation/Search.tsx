const Search = () => {
    
    return (
        <div style={{ marginBottom: '1rem', display: 'flex'  }}>
            <input
            type="text"
            className="form-control"
            placeholder=""
            style={{ borderRadius: 
                '0.5rem 0 0 0.5rem', 
                border: '1px solid lightgray' 
            }}
            />
            <button  style={{ backgroundColor: '#c55b28', 
                color: 'white', 
                padding: '0.5rem 1rem', 
                borderRadius: '0 0.5rem 0.5rem 0',
                border: '1px solid lightgray'
            }}>
            <i className="bi bi-search"></i>
            </button>
        </div>
    );
}

export default Search;