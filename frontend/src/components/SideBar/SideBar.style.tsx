import { Link } from 'react-router-dom';
import styled from 'styled-components';

interface SideBarSelect {
  $isSelected: boolean;
}

interface SideBarSize {
  $width: number;
  $height: number;
}

type SideBarStyleProps = SideBarSelect & SideBarSize;

export const PageSideBarWrapper = styled.div`
  padding: 48px 58px;
`;

export const SideBarContainer = styled.div<SideBarSize>`
  display: flex;
  flex-direction: column;

  position: relative;
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};

  border-top: 1px solid ${({ theme }) => theme.colors.gray};

  & > :nth-child(n) {
    border: solid ${({ theme }) => theme.colors.gray};
    border-width: 0 1px 1px 1px;
  }
`;

export const LabelContent = styled.span<SideBarStyleProps>`
  display: flex;
  align-items: center;
  justify-content: flex-start;

  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};

  font-weight: ${(props) => (props.$isSelected ? 'bold' : 'normal')};
  color: ${({ theme }) => theme.colors.gray};

  padding-left: 20px;

  transition: all 0.4s ease;
  cursor: pointer;
`;

export const SideBarLink = styled(Link)`
  text-decoration: none;
`;

export const SideBarContent = styled.div<SideBarSelect>`
  transition: all 0.4s ease;
  background-color: ${(props) => `${props.$isSelected ? '#eee' : 'transparent'}`};
`;

export const Arrow = styled.span`
  position: absolute;
  right: 35px;

  &::after {
    position: absolute;
    left: 0;
    top: -8px;

    width: 10px;
    height: 10px;

    border-top: 3px solid ${({ theme }) => theme.colors.gray};
    border-right: 3px solid ${({ theme }) => theme.colors.gray};

    content: '';
    transform: rotate(45deg);
  }
`;
