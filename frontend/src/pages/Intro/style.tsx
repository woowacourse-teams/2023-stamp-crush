import styled from 'styled-components';

export const Template = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100vw;
  height: 100%;
  background: #fcf8f2;
  font-family: sans-serif;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  position: fixed;
  width: 100vw;
  padding: 15px 40px;

  & > a {
    color: black;
  }
`;

export const Title = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  font-size: 50px;
  font-weight: 400;
  line-height: 60px;
  margin: 70px 0 60px 0;

  & > p {
    font-weight: 900;
  }

  & > span {
    text-align: center;
    font-weight: 900;
    font-size: 12px;
    color: #8a24ae;
  }
`;

export const Description = styled.p`
  font-size: 14px;
  color: gray;
  width: 500px;
  text-align: center;
  margin-top: 20px;
  line-height: 22px;
  font-weight: 200;
`;

export const ImageBox = styled.img`
  border-radius: 20px;

  background: green;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 0px 15px 5px;
`;

export const ImageGridContainer = styled.section`
  display: grid;
`;
