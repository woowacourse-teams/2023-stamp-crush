import { ThemeProvider } from 'styled-components';
import Router from './Router';
import { theme } from './style/theme';

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <Router />
    </ThemeProvider>
  );
};

export default App;
