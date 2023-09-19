import { ReactNode } from 'react';
import { InfoContainer } from '../../style';
import Header from '../Header';

interface HomeTemplateProps {
  children?: ReactNode;
}

const HomeTemplate = ({ children }: HomeTemplateProps) => {
  return (
    <>
      <Header />
      <InfoContainer>{children}</InfoContainer>
    </>
  );
};

export default HomeTemplate;
