import { Link, useLocation } from 'react-router-dom';
import { TabBarContainer, TapBarItem } from './style';
import { AiOutlineHome } from '@react-icons/all-files/ai/AiOutlineHome';
import { AiOutlineGift } from '@react-icons/all-files/ai/AiOutlineGift';
import { AiOutlineUser } from '@react-icons/all-files/ai/AiOutlineUser';
import { RouterPath } from '../../../../types/utils';
import ROUTER_PATH from '../../../../constants/routerPath';
import { FaStamp } from '@react-icons/all-files/fa/FaStamp';
import { FaListUl } from '@react-icons/all-files/fa/FaListUl';
import { FaMedal } from '@react-icons/all-files/fa/FaMedal';

type UserType = 'admin' | 'customer';

interface TabBarProps {
  userType: UserType;
}

const CUSTOMER_TABS = [
  { path: [ROUTER_PATH.couponList], icon: <AiOutlineHome size={28} />, label: '홈' },
  { path: [ROUTER_PATH.rewardList], icon: <AiOutlineGift size={28} />, label: '리워드' },
  {
    path: [ROUTER_PATH.myPage],
    icon: <AiOutlineUser size={28} />,
    label: '마이페이지',
  },
];

const ADMIN_TABS = [
  { path: [ROUTER_PATH.customerList], icon: <FaListUl size={28} />, label: '고객 목록' },
  {
    path: [ROUTER_PATH.enterStamp, ROUTER_PATH.earnStamp],
    icon: <FaStamp size={28} />,
    label: '스탬프 적립',
  },
  {
    path: [ROUTER_PATH.enterReward, ROUTER_PATH.useReward],
    icon: <FaMedal size={28} />,
    label: '리워드 사용',
  },
];

const BottomTabBar = ({ userType }: TabBarProps) => {
  const location = useLocation();

  const tabToShow = userType === 'admin' ? ADMIN_TABS : CUSTOMER_TABS;

  return (
    <TabBarContainer isShow={userType === 'customer'}>
      {tabToShow.map((tab, index) => (
        <TapBarItem key={index} $isSelected={tab.path.includes(location.pathname as RouterPath)}>
          <Link to={tab.path[0]}>
            {tab.icon}
            {tab.label}
          </Link>
        </TapBarItem>
      ))}
    </TabBarContainer>
  );
};

export default BottomTabBar;
