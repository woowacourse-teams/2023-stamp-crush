import App from './App';
import React from 'react';
import ReactDOM from 'react-dom/client';
import GlobalStyle from './style/GlobalStyle';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <GlobalStyle />
    <App />
  </React.StrictMode>,
);
