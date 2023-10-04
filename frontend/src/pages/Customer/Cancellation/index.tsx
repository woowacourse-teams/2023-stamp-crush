import { useNavigate } from 'react-router-dom';
import SubHeader from '../../../components/Header/SubHeader';
import { ROUTER_PATH } from '../../../constants';
import {
  CancellationButton,
  CheckParagraph,
  ContentContainer,
  Heading2,
  Paragraph,
  Strong,
  Title,
} from './style';
import { useState } from 'react';
import { Spacing } from '../../../style/layout/common';
import useDeleteCustomer from './hooks/useDeleteCustomer';

const CustomerCancellation = () => {
  const navigate = useNavigate();
  const [isConfirm, setIsConfirm] = useState(false);
  const { mutate } = useDeleteCustomer();

  const navigateCustomerSettingPage = () => {
    navigate(ROUTER_PATH.customerSetting);
  };

  const deleteCustomer = () => {
    mutate();
  };

  return (
    <>
      <SubHeader title="회원탈퇴" onClickBack={navigateCustomerSettingPage} />
      <ContentContainer>
        <Title>탈퇴 안내</Title>
        <p>
          회원탈퇴 전에 <Strong>안내 사항을 반드시</Strong> 확인해주세요.
        </p>
        <Heading2>안내사항</Heading2>
        <Paragraph>탈퇴 후 아래의 회원 정보가 삭제됩니다.</Paragraph>
        <Paragraph>
          아래의 회원 정보는 회원 탈퇴와 동시에 모두 삭제되며, 복구되지 않습니다.
        </Paragraph>
        <Spacing $size={20} />
        <Paragraph>· 보유하고 있는 적립된 모든 쿠폰, 스탬프</Paragraph>
        <Paragraph>· 쿠폰 즐겨찾기 정보</Paragraph>
        <Paragraph>· 카카오 로그인 정보, 전화번호</Paragraph>
        <Paragraph>· 보유하고 있는 리워드</Paragraph>
        <CheckParagraph>
          <input
            type="checkbox"
            checked={isConfirm}
            onChange={({ target: { checked } }) => setIsConfirm(checked)}
          />
          <p>위 내용을 충분히 숙지하였습니다.</p>
        </CheckParagraph>
        <CancellationButton isConfirm={isConfirm} disabled={!isConfirm} onClick={deleteCustomer}>
          회원 탈퇴하기
        </CancellationButton>
      </ContentContainer>
    </>
  );
};

export default CustomerCancellation;
