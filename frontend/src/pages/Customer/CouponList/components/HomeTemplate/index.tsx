import { PropsWithChildren } from 'react';
import { InfoContainer } from '../../style';
import Header from '../Header';

const HomeTemplate = ({ children }: PropsWithChildren) => {
  return (
    <>
      <Header />
      <InfoContainer>{children}</InfoContainer>
    </>
  );
};

export default HomeTemplate;
