import { Link, useLocation } from 'react-router-dom';
import { TabBarContainer, TapBarItem } from './style';
import { AiOutlineHome, AiOutlineGift, AiOutlineUser } from 'react-icons/ai';
import { ROUTER_PATH } from '../../../../constants';
import { RouterPath } from '../../../../types';

const BOTTOM_TABS = [
  { path: [ROUTER_PATH.couponList], icon: <AiOutlineHome size={28} />, label: '홈' },
  { path: [ROUTER_PATH.rewardList], icon: <AiOutlineGift size={28} />, label: '리워드' },
  {
    path: [ROUTER_PATH.myPage, ROUTER_PATH.rewardHistory, ROUTER_PATH.stampHistory],
    icon: <AiOutlineUser size={28} />,
    label: '마이페이지',
  },
];

const BottomTabBar = () => {
  const location = useLocation();

  const isDisplayTabBar = ![
    ROUTER_PATH.login,
    ROUTER_PATH.auth,
    ROUTER_PATH.signup,
    ROUTER_PATH.inputPhoneNumber,
  ].includes(location.pathname as RouterPath);

  if (!isDisplayTabBar) {
    return null;
  }

  return (
    <TabBarContainer>
      {BOTTOM_TABS.map((tab, index) => (
        <TapBarItem key={index} $isSelected={tab.path.includes(location.pathname as RouterPath)}>
          <Link to={Array.isArray(tab.path) ? tab.path[0] : tab.path}>
            {tab.icon}
            {tab.label}
          </Link>
        </TapBarItem>
      ))}
    </TabBarContainer>
  );
};

export default BottomTabBar;
