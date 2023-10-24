import { CustomerPreviewGif } from '../../assets';
import { Container, Header, ServiceIntro, CustomerPreview } from './style';
import { AiOutlineGithub } from '@react-icons/all-files/ai/AiOutlineGithub';
import { AiFillYoutube } from '@react-icons/all-files/ai/AiFillYoutube';

const Introduction = () => {
  return (
    <Container>
      <ServiceIntro>
        <span>STAMP CRUSH</span>
        <h1>
          이제 <br /> 종이쿠폰의 시대는 <br />
          끝났다.
        </h1>
        <p>
          온라인 쿠폰 적립 및 관리 플랫폼, 스탬프크러쉬입니다.
          <br /> 카페사장모드, 고객모드 두가지를 운영해요. 더 이상, 꼬깃꼬깃한 종이쿠폰 주고 받지
          마시고 <br /> 종이쿠폰을 간편하게 적립하고 관리해보세요.
        </p>
      </ServiceIntro>
      <CustomerPreview src={CustomerPreviewGif} alt="서비스 고객모드 미리보기" />
      <AiOutlineGithub />
      <AiFillYoutube />
    </Container>
  );
};

export default Introduction;
