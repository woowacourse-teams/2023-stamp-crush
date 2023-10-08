import { Link } from 'react-router-dom';
import { HeaderContainer } from '../../style';
import { StampcrushLogo } from '../../../../../assets';
import ROUTER_PATH from '../../../../../constants/routerPath';

const Header = () => {
  return (
    <HeaderContainer>
      <Link to={ROUTER_PATH.couponList}>
        <StampcrushLogo height={25} />
      </Link>
    </HeaderContainer>
  );
};

export default Header;
