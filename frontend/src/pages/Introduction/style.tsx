import styled from 'styled-components';

export const Container = styled.main`
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  background: #fcf8f2;
`;

export const Header = styled.header`
  display: flex;
  justify-content: space-between;
  font-size: 24px;
  height: 30px;
`;

export const CustomerPreview = styled.img`
  width: 415px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);
`;

export const ServiceIntro = styled.main`
  display: flex;
  flex-direction: column;
  text-align: center;
  height: 30%;

  & > span {
    font-size: 12px;
    font-weight: 900;
    color: #674ea7;
    margin-bottom: 25px;
  }

  & > h1 {
    font-size: 52px;
    font-weight: 800;
    margin-bottom: 40px;
    line-height: 60px;
  }

  & > p {
    line-height: 21px;
    font-size: 14px;
    color: gray;
  }
  margin: 20rem 0;
`;
