import { HeaderContainer, LogoImg, LogoutButton } from './style';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { Link, useNavigate } from 'react-router-dom';
import { ROUTER_PATH } from '../../constants';

const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // TODO: log out 로직
    navigate(ROUTER_PATH.adminLogin);
  };

  return (
    <HeaderContainer>
      <Link to={ROUTER_PATH.customerList}>
        <LogoImg src={AdminHeaderLogo} alt="스탬프 크러쉬 로고" />
      </Link>
      <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
    </HeaderContainer>
  );
};

export default Header;
