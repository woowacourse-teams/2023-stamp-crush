import { LoginLogo } from '../../../assets';
import { ROUTER_PATH } from '../../../constants';
import {
  Container,
  LoginContent,
  BackgroundImg,
  Title,
  SubTitle,
  Text,
  RedirectContainer,
  RedirectLink,
  CheckList,
} from './style';
import BackgroundImgSrc from '../../../assets/admin_login_background.jpg';
import CheckItem from './CheckItem';
import AdminLoginForm from './components/AdminLoginForm';

const AdminLogin = () => {
  return (
    <Container>
      <LoginContent>
        <LoginLogo width={208} />
        <Title>안녕하세요 사장님! :)</Title>
        <SubTitle>스탬프크러쉬입니다.</SubTitle>
        <Text>쿠폰 관리, 이제는 온라인으로 만나보세요.</Text>
        <CheckList>
          <CheckItem text="고객에게 간편한 적립 및 리워드 사용" />
          <CheckItem text="원하는 디자인으로 쿠폰 제작 가능" />
          <CheckItem text="쿠폰 적립 고객 리스트 제공" />
        </CheckList>
        <AdminLoginForm />
        <RedirectContainer>
          <span>고객님이신가요? </span>
          <RedirectLink to={ROUTER_PATH.login}>고객님 로그인</RedirectLink>
        </RedirectContainer>
      </LoginContent>
      <BackgroundImg src={BackgroundImgSrc} alt="배경 이미지" />
    </Container>
  );
};

export default AdminLogin;
