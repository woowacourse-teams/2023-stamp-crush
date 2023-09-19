import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { ArrowIconWrapper, NavContainer, NavWrapper, Nickname, NicknameContainer } from './style';
import { BiArrowBack } from '@react-icons/all-files/bi/BiArrowBack';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import { AiOutlineUnorderedList } from '@react-icons/all-files/ai/AiOutlineUnorderedList';
import { AiOutlineLogout } from '@react-icons/all-files/ai/AiOutlineLogout';

const ICONS = [
  <AiOutlineUnorderedList key="rewardHistory" />,
  <AiOutlineUnorderedList key="stampHistory" />,
  <AiOutlineLogout key="logout" />,
];

const MYPAGE_NAV_OPTIONS = [
  {
    key: 'rewardHistory',
    value: '리워드 사용 내역',
  },
  {
    key: 'stampHistory',
    value: '스탬프 적립 내역',
  },
  {
    key: 'logout',
    value: '로그아웃',
  },
];

const MyPage = () => {
  const { customerProfile } = useCustomerProfile();
  const navigate = useNavigate();

  const navigatePage = (key: string) => () => {
    if (key === 'logout') {
      localStorage.setItem('login-token', '');
      navigate(ROUTER_PATH.login);
      return;
    }

    navigate(ROUTER_PATH[key]);
  };

  return (
    <>
      <ArrowIconWrapper
        onClick={navigatePage('couponList')}
        aria-label="홈으로 돌아가기"
        role="button"
      >
        <BiArrowBack size={24} />
      </ArrowIconWrapper>
      <NicknameContainer>
        <Nickname>{customerProfile?.profile.nickname}</Nickname>님
      </NicknameContainer>
      <NavContainer>
        {MYPAGE_NAV_OPTIONS.map((option, index) => (
          <NavWrapper key={option.key} onClick={navigatePage(option.key)}>
            {ICONS[index]}
            {option.value}
          </NavWrapper>
        ))}
      </NavContainer>
    </>
  );
};

export default MyPage;
