import GlobalStyle from './style/GlobalStyle';
import { ThemeProvider } from 'styled-components';
import Router from './Router';
import { theme } from './style/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Router />
    </ThemeProvider>
  );
};

export default App;
