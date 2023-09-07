import { Link, useLocation } from 'react-router-dom';
import { TabBarContainer, TapBarItem } from './style';
import { AiOutlineHome, AiOutlineGift, AiOutlineUser } from 'react-icons/ai';
import { ROUTER_PATH } from '../../../../constants';
import { RouterPath } from '../../../../types';

const BOTTOM_TABS = [
  { path: [ROUTER_PATH.couponList], icon: <AiOutlineHome size={28} />, label: '홈' },
  { path: [ROUTER_PATH.rewardList], icon: <AiOutlineGift size={28} />, label: '리워드' },
  {
    path: [ROUTER_PATH.myPage],
    icon: <AiOutlineUser size={28} />,
    label: '마이페이지',
  },
];

const BottomTabBar = () => {
  const location = useLocation();

  const isTabBarVisible = [
    ROUTER_PATH.couponList,
    ROUTER_PATH.rewardList,
    ROUTER_PATH.myPage,
  ].includes(location.pathname as RouterPath);

  return (
    isTabBarVisible && (
      <TabBarContainer>
        {BOTTOM_TABS.map((tab, index) => (
          <TapBarItem key={index} $isSelected={tab.path.includes(location.pathname as RouterPath)}>
            <Link to={tab.path[0]}>
              {tab.icon}
              {tab.label}
            </Link>
          </TapBarItem>
        ))}
      </TabBarContainer>
    )
  );
};

export default BottomTabBar;
