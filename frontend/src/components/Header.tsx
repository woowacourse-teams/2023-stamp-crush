import { GlobalHeader, LoginLink, LogoImg } from './Header.style';
import AdminHeaderLogo from '../assets/admin_header_logo.png';
import { Link } from 'react-router-dom';

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
