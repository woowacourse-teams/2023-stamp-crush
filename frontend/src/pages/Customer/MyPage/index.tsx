import { useNavigate } from 'react-router-dom';
import { FEEDBACK_FORM_LINK, ROUTER_PATH } from '../../../constants';
import { FeedbackLink, NavContainer, NavWrapper, Nickname, NicknameContainer } from './style';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import { AiOutlineUnorderedList } from '@react-icons/all-files/ai/AiOutlineUnorderedList';
import { AiOutlineLogout } from '@react-icons/all-files/ai/AiOutlineLogout';
import { AiOutlineUserDelete } from '@react-icons/all-files/ai/AiOutlineUserDelete';
import { AiOutlineFileText } from '@react-icons/all-files/ai/AiOutlineFileText';
import useCustomerRedirectRegisterPage from '../../../hooks/useCustomerRedirectRegisterPage';

const ICONS = [
  <AiOutlineUnorderedList key="rewardHistory" />,
  <AiOutlineUnorderedList key="stampHistory" />,
  <AiOutlineLogout key="logout" />,
  <AiOutlineUserDelete key="customerCancellation" />,
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
  {
    key: 'customerCancellation',
    value: '회원 탈퇴',
  },
];

const MyPage = () => {
  const { customerProfile } = useCustomerRedirectRegisterPage();
  const navigate = useNavigate();

  const navigatePage = (key: string) => () => {
    if (key === 'logout') {
      // TODO: 완전한 로그아웃 구현해야 함.
      localStorage.setItem('login-token', '');
      navigate(ROUTER_PATH.login);
      return;
    }

    navigate(ROUTER_PATH[key]);
  };

  return (
    <>
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
        <NavWrapper>
          <AiOutlineFileText />
          <FeedbackLink href={FEEDBACK_FORM_LINK}>만족도 조사</FeedbackLink>
        </NavWrapper>
      </NavContainer>
    </>
  );
};

export default MyPage;
