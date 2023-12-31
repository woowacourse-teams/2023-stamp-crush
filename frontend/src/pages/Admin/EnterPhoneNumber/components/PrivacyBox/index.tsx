import { Container, RowContainer, TableItem, TableTitleItem } from './style';

const PrivacyBox = () => {
  return (
    <Container>
      <h1>개인정보 수집 및 이용동의</h1>
      <p>
        스탬프크러쉬 서비스 회원가입, 고지사항 전달 등을 위해 아래와 같이 개인정보를 수집 및
        이용합니다. <br /> 전화번호를 입력하시면 개인정보 수집 및 이용에 동의하시는 것으로
        간주됩니다.
      </p>
      <RowContainer>
        <TableTitleItem>수집 목적</TableTitleItem>
        <TableTitleItem>수집 항목</TableTitleItem>
        <TableTitleItem>수집 근거</TableTitleItem>
        <TableTitleItem>수집 기간</TableTitleItem>
      </RowContainer>
      <RowContainer>
        <TableItem>
          회원 식별 및 회원제 서비스 제공과 <br /> 서비스 변경사항 및 고지사항 전달
        </TableItem>
        <TableItem>전화번호</TableItem>
        <TableItem>개인정보 보호법 제 15조 1항</TableItem>
        <TableItem>회원탈퇴 시까지</TableItem>
      </RowContainer>
    </Container>
  );
};

export default PrivacyBox;
