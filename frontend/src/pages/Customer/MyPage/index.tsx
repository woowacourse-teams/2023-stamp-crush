import { useNavigate } from 'react-router-dom';
import { FEEDBACK_FORM_LINK, ROUTER_PATH } from '../../../constants';
import { FeedbackLink, NavContainer, NavWrapper, Nickname, NicknameContainer } from './style';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import { AiOutlineUnorderedList } from '@react-icons/all-files/ai/AiOutlineUnorderedList';
import { AiOutlineLogout } from '@react-icons/all-files/ai/AiOutlineLogout';
import { AiOutlineUserDelete } from '@react-icons/all-files/ai/AiOutlineUserDelete';
import { AiOutlineFileText } from '@react-icons/all-files/ai/AiOutlineFileText';
import { GrUserSettings } from '@react-icons/all-files/gr/GrUserSettings';
import useCustomerRedirectRegisterPage from '../../../hooks/useCustomerRedirectRegisterPage';

const ICONS = [
  <AiOutlineUnorderedList key="rewardHistory" />,
  <AiOutlineUnorderedList key="stampHistory" />,
  <GrUserSettings key="customerSetting" />,
  <AiOutlineFileText key="feedback" />,
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
    key: 'customerSetting',
    value: '내 정보 변경',
  },
  {
    key: 'feedback',
    value: '서비스 만족도 조사',
  },
  {
    key: 'logout',
    value: '로그아웃',
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

  const isFeedBack = (key: string) => key === 'feedback';

  return (
    <>
      <NicknameContainer>
        <Nickname>{customerProfile?.profile.nickname}</Nickname>님
      </NicknameContainer>
      <NavContainer>
        {MYPAGE_NAV_OPTIONS.map((option, index) =>
          isFeedBack(option.key) ? (
            <FeedbackLink key={option.key} href={FEEDBACK_FORM_LINK}>
              <NavWrapper>
                {ICONS[index]}
                {option.value}
              </NavWrapper>
            </FeedbackLink>
          ) : (
            <NavWrapper key={option.key} onClick={navigatePage(option.key)}>
              {ICONS[index]}
              {option.value}
            </NavWrapper>
          ),
        )}
      </NavContainer>
    </>
  );
};

export default MyPage;
