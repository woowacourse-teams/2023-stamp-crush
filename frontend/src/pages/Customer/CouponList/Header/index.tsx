import { Link } from 'react-router-dom';
import { ROUTER_PATH } from '../../../../constants';
import { HeaderContainer, LogoImg } from '../style';
import { StampcrushLogo } from '../../../../assets';

const Header = () => {
  return (
    <HeaderContainer>
      <Link to={ROUTER_PATH.couponList}>
        <LogoImg src={StampcrushLogo} alt="스탬프 크러쉬 로고" role="link" />
      </Link>
    </HeaderContainer>
  );
};

export default Header;
