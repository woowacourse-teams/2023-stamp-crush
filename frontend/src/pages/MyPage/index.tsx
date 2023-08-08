import { useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../constants';
import { ArrowIconWrapper, NavContainer, NavWrapper, Nickname, NicknameContainer } from './style';
import { BiArrowBack } from 'react-icons/bi';

// TODO: 추후에 결정된 routerPath로 수정
const MYPAGE_NAV_OPTIONS = [
  {
    key: 'rewardList',
    value: '내 리워드',
  },
  {
    key: 'used-rewards',
    value: '리워드 사용 내역',
  },
  {
    key: 'earned-stamps',
    value: '스탬프 보유 내역',
  },
];

const MyPage = () => {
  const navigate = useNavigate();

  const navigatePage = (path: string) => () => {
    navigate(path);
  };

  // TODO: 추후에 oAuth후 받아온 닉네임으로 하드코딩된 값 '라잇' 수정
  return (
    <>
      <ArrowIconWrapper
        onClick={navigatePage(ROUTER_PATH.couponList)}
        aria-label="홈으로 돌아가기"
        role="button"
      >
        <BiArrowBack size={24} />
      </ArrowIconWrapper>
      <NicknameContainer>
        <Nickname>라잇</Nickname>님
      </NicknameContainer>
      <NavContainer>
        {MYPAGE_NAV_OPTIONS.map((option) => (
          <NavWrapper key={option.key} onClick={navigatePage(ROUTER_PATH[option.key])}>
            {option.value}
          </NavWrapper>
        ))}
      </NavContainer>
    </>
  );
};

export default MyPage;
