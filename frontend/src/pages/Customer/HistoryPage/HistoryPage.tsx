import { PropsWithChildren } from 'react';
import { useNavigate } from 'react-router-dom';
import SubHeader from '../../../components/Header/SubHeader';
import ROUTER_PATH from '../../../constants/routerPath';

interface HistoryPageProps {
  title: string;
}

const HistoryPage = ({ title, children }: HistoryPageProps & PropsWithChildren) => {
  const navigate = useNavigate();

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  return (
    <>
      <SubHeader title={title} onClickBack={navigateMyPage} />
      {children}
    </>
  );
};

export default HistoryPage;
