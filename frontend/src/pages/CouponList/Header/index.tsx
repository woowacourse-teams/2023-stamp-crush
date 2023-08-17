import { Link, useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../../constants';
import { HeaderContainer, LogoImg, MyPageIconWrapper } from '../style';
import { StampcrushLogo } from '../../../assets';
import { GoPerson } from 'react-icons/go';

const Header = () => {
  const navigate = useNavigate();
  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  return (
    <HeaderContainer>
      <Link to={ROUTER_PATH.couponList}>
        <LogoImg src={StampcrushLogo} alt="스탬프 크러쉬 로고" role="link" />
      </Link>
      <MyPageIconWrapper onClick={navigateMyPage} aria-label="마이 페이지" role="button">
        <GoPerson size={24} color="black" />
      </MyPageIconWrapper>
    </HeaderContainer>
  );
};

export default Header;
