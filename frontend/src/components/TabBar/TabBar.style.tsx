import styled from 'styled-components';
interface StyledTabBarSelect {
  $isSelect: boolean;
}

interface StyledTabBarWidth {
  $height: number;
  $width: number;
}

type StyledTabBarContentProps = StyledTabBarSelect & StyledTabBarWidth;

export const TabBarContainer = styled.div<StyledTabBarWidth>`
  display: flex;
  position: relative;
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
`;

export const TabBarLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

export const LabelContent = styled.span<StyledTabBarContentProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.4s ease;
  color: ${(props) => (props.$isSelect ? 'black' : '#eee')};
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
`;

export const TabBarItem = styled.div<StyledTabBarSelect>`
  transition: all 0.4s ease;
  border-bottom: 1px solid ${(props) => (props.$isSelect ? 'black' : '#eee')};
`;

export const TabBarInput = styled.input`
  display: none;
`;
