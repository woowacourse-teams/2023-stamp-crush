import { styled } from 'styled-components';
import AdminHeaderLogo from '../assets/admin_header_logo.png';
import { Link, useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // log out 로직
    navigate('/login');
  };
  return (
    <GlobalHeader>
      <Link to="/">
        <LogoImg src={AdminHeaderLogo} />
      </Link>
      <LogoutButton onClick={handleLogout}>로그아웃</LogoutButton>
    </GlobalHeader>
  );
};

export default Header;

const GlobalHeader = styled.header`
  display: flex;
  height: 59px;
  justify-content: space-between;
  align-items: center;
  padding: 0 60px;
  border-bottom: 1px solid #888;
`;

const LogoImg = styled.img`
  height: 38px;
`;

const LogoutButton = styled.button`
  border: none;
  background: transparent;
  color: black;
  cursor: pointer;
`;
