import { Link, useLocation } from 'react-router-dom';
import { TabBarContainer } from './style';
import { AiOutlineHome, AiOutlineGift, AiOutlineUser } from 'react-icons/ai';
import { ROUTER_PATH } from '../../../../constants';

const BottomTabBar = () => {
  const location = useLocation();

  if (
    location.pathname === ROUTER_PATH.login ||
    location.pathname === ROUTER_PATH.auth ||
    location.pathname === ROUTER_PATH.signup ||
    location.pathname === ROUTER_PATH.inputPhoneNumber
  )
    return null;

  return (
    <TabBarContainer>
      <Link to="/">
        <AiOutlineHome size={28} />홈
      </Link>
      <Link to="/reward-list">
        <AiOutlineGift size={28} />
        리워드
      </Link>
      <Link to="/my-page">
        <AiOutlineUser size={28} />
        마이페이지
      </Link>
    </TabBarContainer>
  );
};

export default BottomTabBar;
