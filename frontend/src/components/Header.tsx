import { styled } from 'styled-components';
import AdminHeaderLogo from '../assets/admin_header_logo.png';
import { Link, useNavigate } from 'react-router-dom';

const Header = () => {
  return (
    <GlobalHeader>
      <Link to="/">
        <LogoImg src={AdminHeaderLogo} />
      </Link>
      <LoginLink to="/login">로그인</LoginLink>
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
