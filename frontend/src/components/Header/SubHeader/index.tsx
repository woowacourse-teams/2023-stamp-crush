import { ArrowIconWrapper, HeaderContainer } from './style';
import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { BiArrowBack } from '@react-icons/all-files/bi/BiArrowBack';

interface SubHeaderProps {
  title: string;
}

const SubHeader = ({ title }: SubHeaderProps) => {
  const navigate = useNavigate();

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  return (
    <HeaderContainer>
      <ArrowIconWrapper onClick={navigateMyPage}>
        <BiArrowBack size={24} />
      </ArrowIconWrapper>
      {title}
    </HeaderContainer>
  );
};

export default SubHeader;
