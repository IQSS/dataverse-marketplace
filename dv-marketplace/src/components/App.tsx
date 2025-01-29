import { useState, useEffect } from 'react'
// import reactLogo from '../assets/react.svg'
// import viteLogo from '/vite.svg'

import '../assets/css/App.css'

function App() {
  const [data, setData] = useState('');
  const [responseMessage, setResponseMessage] = useState('');

  useEffect(() => {
    fetch('/askthedata.json')
      .then(response => response.json())
      .then(json => setData(JSON.stringify(json, null, 2)))
      .catch(error => console.error('Error fetching JSON:', error))
  }, []);

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
    <div className='flex flex-col justify-center items-center'>
      <div>
        <button
          type="button"
          onClick={sendData}
          className='bg-blue-500 text-white p-2 rounded'
        >
          Send Data
        </button>

        <textarea
          value={responseMessage}
          readOnly
          rows={20}
          cols={80}
          className='border p-2 mt-4'
        />
      </div>
    </div>
  )
}

export default App
