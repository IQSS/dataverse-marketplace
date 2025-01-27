import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'

import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className='bg-slate-800 text-white h-screen flex flex-col justify-center items-center'>
      <div className='flex justify-center'>
      <a href="https://vite.dev" target="_blank">
        <img src={viteLogo} className="logo" alt="Vite logo" />
      </a>
      <a href="https://react.dev" target="_blank">
        <img src={reactLogo} className="logo react" alt="React logo" />
      </a>
      </div>
      <h1 className='text-center'>Vite + React</h1>
      <div className=" text-center">
      <button className="btn btn-primary" onClick={() => setCount((count) => count + 1)}>
        count is {count}
      </button>
      <p>
        
      </p>
      </div>
      <p className="read-the-docs text-center">
      Click on the Vite and React logos to learn more
      </p>
    </div>
  )
}

export default App
