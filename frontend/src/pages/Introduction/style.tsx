import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { Z_INDEX } from '../../constants/magicNumber';

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
  z-index: ${Z_INDEX.above};
  background: linear-gradient(to bottom, rgba(0, 0, 0, 0.1) 5%, transparent);
`;

export const ButtonContainer = styled.section`
  display: none;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
`;

export const DesktopButtonContainer = styled(ButtonContainer)`
  @media screen and (min-width: 450px) {
    display: flex;
  }
`;

export const MobileButtonContainer = styled(ButtonContainer)`
  @media screen and (max-width: 450px) {
    display: flex;
  }
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
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: sans-serif;
  width: 100%;
  overflow-x: hidden;
  background: #fcf8f2;
  padding: 0 20px;
`;

export const CustomerPreview = styled.img`
  width: 415px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);
  z-index: ${Z_INDEX.base};

  @media screen and (max-width: 450px) {
    width: 300px;
  }
`;

export const ServiceIntro = styled.main`
  display: flex;
  flex-direction: column;
  text-align: center;

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

    @media screen and (max-width: 450px) {
      font-size: 48px;
    }

    & > span {
      font-size: 60px;
      font-weight: 900;
      color: #674ea7;
      text-emphasis-style: dot;

      @media screen and (max-width: 450px) {
        font-size: 52px;
      }
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
  z-index: ${Z_INDEX.above};
`;

export const OwnerIntro = styled.h2`
  display: flex;
  text-align: center;
  font-size: 56px;
  font-weight: 700;
  margin: 10rem 0 0 0;
  line-height: 62px;

  @media screen and (max-width: 450px) {
    font-size: 42px;
  }
`;

export const OwnerPreviewContainer = styled.section`
  display: flex;
  gap: 5rem;
  padding: 7rem 0 2rem 0;

  @media screen and (max-width: 450px) {
    flex-direction: column;
    padding: 7rem 0 2rem 0;
  }
`;

export const OwnerPreview = styled.img`
  width: 600px;
  height: 400px;
  border-radius: 20px;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.1);

  @media screen and (max-width: 450px) {
    width: 450px;
    height: 300px;
  }
`;

export const Footer = styled.footer`
  width: 110%;
  height: 30rem;
  background: #4d4d4d;
  margin-top: 10rem;
`;

export const SnsIconContainer = styled.div`
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  align-items: center;
  height: 5rem;
`;

export const IconLink = styled(Link)`
  text-decoration: none;
  color: white;
`;

export const ButtonLink = styled(Link)`
  text-decoration: none;
`;

export const FooterContents = styled.div`
  color: #989898;
  line-height: 24px;
  margin: 0 5rem;

  @media screen and (max-width: 450px) {
    font-size: 12px;
    margin: 0 2rem;
  }
`;
