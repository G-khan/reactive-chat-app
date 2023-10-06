// src/ChatInput.js
import React, { useState } from 'react';

const ChatInput = ({ onSendMessage }) => {
  const [username, setUsername] = useState('');
  const [message, setMessage] = useState('');

  const handleMessageChange = (event) => {
    setMessage(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleSendMessage = () => {
    if (message.trim() !== '') {
      onSendMessage(username, message);
      setMessage('');
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Kullanıcı Adı"
        value={username}
        onChange={handleUsernameChange}
      />
      <input
        type="text"
        placeholder="Mesajınızı yazın..."
        value={message}
        onChange={handleMessageChange}
      />
      <button onClick={handleSendMessage}>Gönder</button>
    </div>
  );
};

export default ChatInput;
