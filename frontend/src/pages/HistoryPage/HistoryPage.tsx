import { PropsWithChildren } from 'react';
import SubHeader from '../../components/Header/SubHeader';

interface HistoryPageProps {
  title: string;
}

const HistoryPage = ({ title, children }: HistoryPageProps & PropsWithChildren) => {
  return (
    <>
      <SubHeader title={title} />
      {children}
    </>
  );
};

export default HistoryPage;
