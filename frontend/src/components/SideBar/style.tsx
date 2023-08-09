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
}

export const LogoHeader = styled.header`
  background: ${({ theme }) => theme.colors.main};
  padding-top: 40px;
`;

export const LogoImgWrapper = styled.button`
  display: flex;
  align-self: flex-start;
  cursor: pointer;
  background: transparent;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  width: 150px;
  padding-bottom: 20px;
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
  padding-top: 20px;

  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};

  background: transparent;
  border-radius: 10px;
`;

export const LabelContent = styled.span<SideBarStyleProps>`
  display: flex;
  align-items: center;
  justify-content: flex-start;

  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
  font-weight: ${(props) => (props.$isSelected ? 'bold' : 'normal')};
  color: ${({ theme, $isSelected }) => ($isSelected ? `${theme.colors.text}` : 'white')};
  background-color: ${({ $isSelected, theme }) => ($isSelected ? 'white' : theme.colors.main)};
  border-radius: ${({ $isSelected }) => ($isSelected ? '40px 0 0 40px' : '')};

  padding-left: 20px;

  transition: all 0.4s ease;
  cursor: pointer;
`;

export const SideBarLink = styled(Link)`
  text-decoration: none;
`;

export const SideBarContent = styled.div<{ $isSelected: boolean }>`
  transition: all 0.4s ease;
  border-radius: ${({ $isSelected }) => ($isSelected ? '10px 0 0 10px' : '')};

  :hover {
    font-weight: 600;
    opacity: 80%;
  }
`;
