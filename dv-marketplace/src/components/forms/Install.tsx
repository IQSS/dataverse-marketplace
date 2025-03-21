import { useState, useEffect, useContext } from 'react'
import axios from 'axios';
import { Alert } from 'react-bootstrap';
import { UserContext } from '../context/UserContextProvider';
import { Link } from 'react-router-dom';
import { FormInputCheckbox, FormInputTextArea } from '../UI/FormInputFields';


function Install() {

  const [data, setData] = useState('');
  const userContext = useContext(UserContext);
  const [responseMessage, setResponseMessage] = useState('');
  const tool = window.history.state?.usr?.tool;

  useEffect(() => {
    if (tool) {
      setData(JSON.stringify(tool, null, 2));
    }
  }, [tool]);

  const [hostnames, setHostnames] = useState<string[]>([]);

  useEffect(() => {
    const fetchHostnames = async () => {
      try {
        const response = await axios.get(`https://hub.dataverse.org/api/installation`);
        interface Installation {
          hostname: string;
        }
        const fetchedHostnames = response.data.map((installation: Installation) => installation.hostname);
        setHostnames(fetchedHostnames);
        console.log("Autocomplete results (hostnames):", fetchedHostnames);
      } catch (error) {
        console.error("Error fetching hostnames:", error);
      }
      setHostnames(prevHostnames => ['localhost:8080', ...prevHostnames]);
    };

    fetchHostnames();
  }, []);


  // useEffect(() => {
  //   fetch('/askthedata.json')
  //     .then(response => response.json())
  //     .then(json => setData(JSON.stringify(json, null, 2)))
  //     .catch(error => console.error('Error fetching JSON:', error))
  // }, []);

  const sendData = () => {
    fetch('http://localhost:8080/api/externalTools?key=2d5a00af-6e13-40da-a359-e6e44112f1a9', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: data
    })
    .then(response => response.json())
    .then(json => setResponseMessage(`Success: ${JSON.stringify(json, null, 2)}`))
    .catch(error => setResponseMessage(`Error: ${error.message}`));
  }

  return (

    <div className="container" style={{ marginTop: "120px" }}>
      <Alert variant='light'>
        <div className='container '>
            
            <div className='row'>
                <h1 className='col-6'>Installing: {tool?.name}</h1>
                <div className='col-6 d-flex justify-content-end align-items-center'>
                    
                </div>
            </div>
            
        </div>
        </Alert>

        <div className='col-12 d-flex'>
                <input
                  id="hostname"
                  type="text"
                  list="hostnames"
                  className='col-12 border p-2 w-full'
                  placeholder="mydataverse.org"
                />
                <datalist id="hostnames" style={{ position: 'absolute', zIndex: 1 }}>
                  {hostnames.map((hostname, index) => (
                  <option key={index} value={hostname} />
                  ))}
                </datalist>
        </div>
        <br/>
        <div className='col-12 d-flex'>
                
              <FormInputCheckbox label="Use https" name="https" id="https" value="true" />
        </div>
    </div>

    // <div className='flex flex-col justify-center items-center'>
    //   <div>
    //     <button
    //       type="button"
    //       onClick={sendData}
    //       className='bg-blue-500 text-white p-2 rounded'
    //     >
    //       Send Data
    //     </button>

    //     <textarea
    //       value={responseMessage}
    //       readOnly
    //       rows={20}
    //       cols={80}
    //       className='border p-2 mt-4'
    //     />

    //     {tool && (
    //       <pre className='border p-2 mt-4'>
    //         {JSON.stringify(tool, null, 2)}
    //       </pre>
    //     )}
    //     <div className='mt-4'>
    //       <label htmlFor="hostname" className='block mb-2'>Select Installation:</label>
    //       <input
    //         id="hostname"
    //         type="text"
    //         list="hostnames"
    //         className='border p-2 w-full'
    //         placeholder="Start typing to see suggestions..."
    //       />
    //       <datalist id="hostnames">
    //         {hostnames.map((hostname, index) => (
    //           <option key={index} value={hostname} />
    //         ))}
    //       </datalist>
    //     </div>
    //   </div>
    // </div>
  )
}

export default Install;
