import styled from 'styled-components';

const CustomerNotFound = () => {
  return (
    <Container>
      <h2>해당 페이지를 찾지 못했습니다.</h2>
      <Heading>404</Heading>
      <p>주소가 잘못되었거나 더 이상 제공되지 않는 페이지입니다.</p>
      <Link href="/">홈으로 돌아가기</Link>
    </Container>
  );
};

export default CustomerNotFound;

const Container = styled.div`
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  text-align: center;
  color: ${({ theme }) => theme.colors.main};
`;

const Heading = styled.h1`
  font-size: 160px;
  margin-bottom: 6px;
  font-weight: 900;
  letter-spacing: 20px;
  color: ${({ theme }) => theme.colors.main};
`;

const Link = styled.a`
  text-decoration: none;
  background: ${({ theme }) => theme.colors.main};
  color: ${({ theme }) => theme.colors.point};
  padding: 12px 24px;
  display: inline-block;
  border-radius: 25px;
  font-size: 14px;
  text-transform: uppercase;
  transition: 0.4s;
  margin-top: 18px;

  &:hover {
    opacity: 80%;
  }
`;
