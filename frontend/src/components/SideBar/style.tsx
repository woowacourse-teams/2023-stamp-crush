import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

interface SideBarStyleProps {
  $width: number;
  $height: number;
  $isSelected: boolean;
}

interface SideBarContainerStyleProps {
  $width: number;
  $height: number;
  $prevIndex: number;
  $nextIndex: number;
}

export const LogoHeader = styled.header`
  background: ${({ theme }) => theme.colors.main};
  padding-top: 40px;
`;

export const LogoImgWrapper = styled.button`
  display: flex;
  align-self: flex-start;
  background: transparent;
  width: 150px;
  padding-bottom: 20px;
  cursor: pointer;
`;

export const LogoImg = styled.img`
  width: 120px;
`;

export const PageSideBarWrapper = styled.div`
  padding: 0 0 0 30px;
`;

export const SideBarContainer = styled.div<SideBarContainerStyleProps>`
  display: flex;
  flex-direction: column;
  margin-top: 20px;

  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};

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
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
  font-size: 18px;
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
