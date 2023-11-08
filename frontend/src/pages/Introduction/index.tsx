import { CustomerPreviewGif, StampcrushLogo } from '../../assets';
import {
  Container,
  ServiceIntro,
  CustomerPreview,
  Header,
  Button,
  ApplyButton,
  OwnerIntro,
  OwnerPreviewContainer,
  OwnerPreview,
  Footer,
  DesktopButtonContainer,
  MobileButtonContainer,
  SnsIconContainer,
  IconLink,
  FooterContents,
  ButtonLink,
} from './style';
import { AiOutlineGithub } from '@react-icons/all-files/ai/AiOutlineGithub';
import { AiFillYoutube } from '@react-icons/all-files/ai/AiFillYoutube';
import { AiFillInstagram } from '@react-icons/all-files/ai/AiFillInstagram';
import { RiServiceLine } from '@react-icons/all-files/ri/RiServiceLine';
import { Parallax, ParallaxProvider } from 'react-scroll-parallax';
import { OwnerPreview2Png, OwnerPreview3Png, OwnerPreview4Png } from '../../assets/png';
import { useNavigate } from 'react-router-dom';
import ROUTER_PATH from '../../constants/routerPath';
import { AiOutlineMail } from '@react-icons/all-files/ai/AiOutlineMail';
import {
  GITHUB_STAMP_CRUSH,
  INSTAGRAM_STAMP_CRUSH,
  LINK_TO_APPLY_SERVICE,
  YOUTUBE_STAMP_CRUSH,
} from '../../constants/magicString';

const Introduction = () => {
  const navigate = useNavigate();

  const navigatePage = (path: string) => () => {
    navigate(path);
  };

  return (
    <ParallaxProvider>
      <Container>
        <Header>
          <StampcrushLogo />
          <DesktopButtonContainer>
            <Button onClick={navigatePage(ROUTER_PATH.login)}>고객님 로그인</Button>
            <Button onClick={navigatePage(ROUTER_PATH.adminLogin)} $isFilled>
              사장님 로그인
            </Button>
          </DesktopButtonContainer>
        </Header>
        <ButtonLink to={LINK_TO_APPLY_SERVICE}>
          <ApplyButton>
            서비스 상담 문의
            <RiServiceLine size={24} />
          </ApplyButton>
        </ButtonLink>
        <ServiceIntro>
          <span>STAMPCRUSH</span>
          <h1>
            이제 <br /> 종이쿠폰의 시대는 <br />
            <span>끝났다</span>
          </h1>
          <p>
            온라인 쿠폰 적립 및 관리 플랫폼, 스탬프크러쉬입니다.
            <br /> 카페사장모드, 고객모드 두가지를 운영해요. 더 이상, 꼬깃꼬깃한 종이쿠폰 주고 받지
            마시고 <br /> 종이쿠폰을 간편하게 적립하고 관리해보세요.
          </p>
        </ServiceIntro>
        <MobileButtonContainer>
          <Button onClick={navigatePage(ROUTER_PATH.login)}>고객님 로그인</Button>
          <Button onClick={navigatePage(ROUTER_PATH.adminLogin)} $isFilled>
            사장님 로그인
          </Button>
        </MobileButtonContainer>
        <Parallax translateY={['150px', '-200px']} scale={[1, 1.2]}>
          <CustomerPreview src={CustomerPreviewGif} alt="서비스 고객모드 미리보기" />
        </Parallax>
        <OwnerIntro>사용하시던 종이쿠폰 그대로</OwnerIntro>
        <OwnerPreviewContainer>
          <Parallax translateX={['300px', '-200px']}>
            <OwnerPreview src={OwnerPreview2Png} alt="서비스 고객모드 미리보기" />
          </Parallax>
          <Parallax translateX={['300px', '-200px']}>
            <OwnerPreview src={OwnerPreview3Png} alt="서비스 고객모드 미리보기" />
          </Parallax>
          <Parallax translateX={['300px', '-200px']}>
            <OwnerPreview src={OwnerPreview4Png} alt="서비스 고객모드 미리보기" />
          </Parallax>
        </OwnerPreviewContainer>
        <Footer>
          <FooterContents>
            <SnsIconContainer>
              <IconLink to={GITHUB_STAMP_CRUSH}>
                <AiOutlineGithub size={34} />
              </IconLink>
              <IconLink to={YOUTUBE_STAMP_CRUSH}>
                <AiFillYoutube size={34} />
              </IconLink>
              <IconLink to={INSTAGRAM_STAMP_CRUSH}>
                <AiFillInstagram size={34} />
              </IconLink>
            </SnsIconContainer>
            <span>
              CONTACT <AiOutlineMail /> stampcrush12@gmail.com
              <br />
            </span>
            <span>COPYRIGHT © 2023 STAMPCRUSH ALL RIGHTS RESERVED</span>
          </FooterContents>
        </Footer>
      </Container>
    </ParallaxProvider>
  );
};

export default Introduction;
