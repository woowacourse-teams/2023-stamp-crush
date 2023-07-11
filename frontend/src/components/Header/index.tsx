import { HeaderContainer, LogoImg, LogoutButton } from './Header.style';
import AdminHeaderLogo from '../assets/admin_header_logo.png';
import { Link, useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // log out 로직
    navigate('/login');
  };
  return (
    <HeaderContainer>
      <Link to="/">
        <LogoImg src={AdminHeaderLogo} />
      </Link>
      <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
    </HeaderContainer>
  );
};

export default Header;
