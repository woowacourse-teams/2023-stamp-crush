import { Link, useLocation } from 'react-router-dom';
import { TabBarContainer } from './style';
import { FaHome, FaGift, FaUser } from 'react-icons/fa';
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
        <FaHome size={32} />홈
      </Link>
      <Link to="/reward-list">
        <FaGift size={32} />
        리워드
      </Link>
      <Link to="/my-page">
        <FaUser size={32} />
        마이페이지
      </Link>
    </TabBarContainer>
  );
};

export default BottomTabBar;
