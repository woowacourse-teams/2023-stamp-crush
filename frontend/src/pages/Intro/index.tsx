import { Title, Template, Description, ImageGridContainer, ImageBox, Header } from './style';
import { IntroCouponList, StampcrushLogo } from '../../assets';
import { AiFillGithub } from '@react-icons/all-files/ai/AiFillGithub';
import { Link } from 'react-router-dom';

const Intro = () => {
  return (
    <Template>
      <Header>
        <StampcrushLogo />

        <Link to="https://github.com/woowacourse-teams/2023-stamp-crush">
          <AiFillGithub size={28} />
        </Link>
      </Header>
      <Title>
        <span>STAMP CRUSH</span>이제, <p>종이쿠폰의 시대는 끝났다.</p>
        <Description>
          온라인 쿠폰 적립 및 관리 플랫폼, 스탬프크러쉬입니다. <br /> 카페사장모드, 고객모드
          두가지를 운영해요. 더 이상, 꼬깃꼬깃한 종이쿠폰 주고 받지 마시고, 종이쿠폰을 간편하게
          적립하고 관리해보세요.
        </Description>
      </Title>
      <section>
        <ImageBox alt="고객 모드 동작 화면" width={'300px'} src={IntroCouponList} />

        <ImageGridContainer>
          {/* <ImageBox width={600} src={IntroCouponList} /> */}
        </ImageGridContainer>
      </section>
    </Template>
  );
};

export default Intro;
