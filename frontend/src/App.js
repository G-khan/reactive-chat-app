import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'tailwindcss/tailwind.css'; // Import the Tailwind CSS styles

function App() {
  const [messages, setMessages] = useState([]);
  const [username, setUsername] = useState('');
  const [message, setMessage] = useState('');

  useEffect(() => {
    const eventSource = new EventSource('http://localhost:8999/api/messages/stream');

    eventSource.addEventListener('message', (event) => {
      const newMessage = JSON.parse(event.data);
      setMessages((prevMessages) => [...prevMessages, newMessage]);
    });

    return () => {
      eventSource.close();
    };
  }, []);

  const handleSendMessage = async () => {
    if (message.trim() !== '' && username.trim() !== '') {
      try {
        await axios.post('http://localhost:8999/api/messages', {
          username,
          content: message,
        });
        setMessage('');
      } catch (error) {
        console.error('Mesaj gönderme hatası:', error);
      }
    }
  };

  return (
    <div className="flex h-screen  flex-col bg-gray-100">
      <div className="bg-gradient-to-r from-blue-500 to-purple-500 py-4">
        <h1 className="text-center text-2xl font-bold text-white"> Reactive Chat App <span className='font-light'> | gokhana.dev</span></h1>
      </div>
      <div className="flex-grow overflow-y-auto">
        <div className="flex flex-col space-y-2 p-4">
          {messages.map((message, index) => (
            <div
              key={index}
              className={` items-center rounded-xl p-2  ${
                message.username === username
                  ? 'self-end rounded-tr bg-blue-500 text-white'
                  : 'self-start rounded-tl bg-gray-300'
              }`}
            >
              <div className='font-thin font-xs	underline decoration-sky-500'> {message.username}</div>
               
              <div className='self-end'>{message.content}</div>
            </div>
          ))}
        </div>
      </div>
      <div className="flex items-center p-4">
        <input
          type="text"
          placeholder="Kullanıcı Adı"
          className="w-1/3 rounded-lg border border-gray-300 px-4 py-2"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="text"
          placeholder="Mesajınızı yazın..."
          className="w-full rounded-lg border border-gray-300 px-4 py-2"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button
          className="ml-2 rounded-lg bg-blue-500 px-4 py-2 text-white"
          onClick={handleSendMessage}
        >
          Gönder
        </button>
      </div>
    </div>
  );
}

export default App;
