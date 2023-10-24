import styled from 'styled-components';

interface ButtonStyledProps {
  $isFilled?: boolean;
}

export const Header = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0 40px;
  height: 78px;
  position: fixed;
  top: 0;
  background: linear-gradient(to bottom, rgba(0, 0, 0, 0.1) 5%, transparent);
`;

export const ButtonContainer = styled.section`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
`;

export const Button = styled.button<ButtonStyledProps>`
  padding: 13px 15px;
  background: ${({ $isFilled }) => ($isFilled ? 'black' : 'transparent')};
  color: ${({ $isFilled }) => ($isFilled ? 'white' : 'black')};
  border-radius: 5px;
  border: ${({ $isFilled }) => ($isFilled ? 'none' : '1px solid black')};

  &:hover {
    font-weight: 600;
  }
`;

export const Container = styled.main`
  font-family: sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  background: #fcf8f2;
`;

export const CustomerPreview = styled.img`
  width: 415px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);
  z-index: 2;
`;

export const ServiceIntro = styled.main`
  display: flex;
  flex-direction: column;
  text-align: center;
  /* height: 30%; */

  & > span {
    font-size: 12px;
    font-weight: 900;
    color: #674ea7;
    margin-bottom: 25px;
  }

  & > h1 {
    font-size: 56px;
    font-weight: 700;
    margin-bottom: 40px;
    line-height: 62px;

    & > span {
      font-size: 52px;
      font-weight: 900;
      color: #674ea7;
      text-emphasis-style: dot;
    }
  }

  & > p {
    line-height: 21px;
    font-size: 16px;
    color: gray;
  }
  margin: 10rem 0 5rem 0;
`;

export const ApplyButton = styled.button`
  display: flex;
  align-items: center;
  gap: 10px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);
  border: none;
  background: white;
  border-radius: 10px;
  padding: 20px;
  position: fixed;
  bottom: 3rem;
  right: 4rem;
  font-size: 18px;
  font-weight: 600;
  z-index: 10;
`;

export const OwnerIntro = styled.h2`
  display: flex;
  text-align: center;
  font-size: 56px;
  font-weight: 700;
  margin: 10rem 0 0 0;
  line-height: 62px;
`;

export const OwnerPreviewContainer = styled.section`
  display: flex;
  gap: 5rem;
  overflow: hidden;
  margin-top: 5rem;
  padding: 2rem 0;
`;

export const OwnerPreview = styled.img`
  width: 600px;
  height: 400px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);
`;

export const Footer = styled.footer`
  width: 100%;
  height: 30rem;
  background: #808080;
  margin-top: 10rem;
`;
