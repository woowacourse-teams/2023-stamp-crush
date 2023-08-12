import { Link } from 'react-router-dom';
import styled from 'styled-components';

interface SideBarStyleProps {
  $isSelected: boolean;
}

interface SideBarContainerStyleProps {
  $prevIndex: number;
  $nextIndex: number;
}

export const Container = styled.section`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

export const LogoHeader = styled.header<{ $currentIndex: number }>`
  background: ${({ theme }) => theme.colors.main};
  padding-top: 40px;
  border-radius: ${({ $currentIndex }) => ($currentIndex === 1 ? '0 0 40px 0' : '0')};
`;

export const LogoImgWrapper = styled.button`
  display: flex;
  align-self: flex-start;
  background: transparent;
  width: 150px;
  padding: 0 0 40px 40px;

  cursor: pointer;
`;

export const LogoImg = styled.img`
  width: 200px;
  height: 25px;
`;

export const SideBarContainer = styled.div<SideBarContainerStyleProps>`
  display: flex;
  flex-direction: column;
  padding-left: 30px;
  width: 240px;
  height: 250px;

  background: ${({ theme }) => `linear-gradient(to right, ${theme.colors.main} 20%, white 80%)`};

  div:nth-child(${(props) => props.$prevIndex}) {
    border-radius: 0 0 40px 0;
  }

  div:nth-child(${(props) => props.$nextIndex}) {
    border-radius: 0 40px 0 0;
  }
`;

export const LabelContent = styled.span<SideBarStyleProps>`
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: flex-start;
  width: 240px;
  height: 50px;
  font-size: 16px;
  font-weight: ${(props) => (props.$isSelected ? '600' : '400')};
  color: ${({ theme, $isSelected }) => ($isSelected ? `${theme.colors.text}` : 'white')};

  border-radius: 40px 0 0 40px;
  padding-left: 20px;

  cursor: pointer;
`;

export const SideBarLink = styled(Link)`
  text-decoration: none;
`;

export const SideBarContent = styled.div<{ $isSelected: boolean; $currentIndex: number }>`
  background-color: ${({ $isSelected, theme }) => ($isSelected ? 'white' : theme.colors.main)};
  border-radius: 40px 0 0 40px;

  :hover {
    opacity: 80%;
  }
`;

export const ImageWrapper = styled.div`
  position: absolute;
  bottom: 10px;
  left: 20px;
`;

export const LogoutContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  background: ${({ theme }) => theme.colors.main};
`;

export const LogoutButton = styled.button`
  border-top: 0.5px solid rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  gap: 10px;
  color: white;
  padding: 20px 0 0 20px;
  margin: 10px 30px 0 30px;
  background: transparent;

  cursor: pointer;

  &:hover {
    opacity: 80%;
  }
`;

export const CopyRight = styled.p`
  color: white;
  padding-left: 50px;
`;
