import styled from 'styled-components';
interface TabBarSelect {
  $isSelect: boolean;
}

interface TabBarWidth {
  $height: number;
  $width: number;
}

type TabBarContentProps = TabBarSelect & TabBarWidth;

export const TabBarContainer = styled.div<TabBarWidth>`
  display: flex;
  width: ${(props) => `${props.$width}px`};
  position: relative;
  height: ${(props) => `${props.$height}px`};
`;

export const TabBarLabel = styled.label`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

export const LabelContent = styled.span<TabBarContentProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.4s ease;
  color: ${(props) => (props.$isSelect ? 'black' : '#eee')};
  width: ${(props) => `${props.$width}px`};
  height: ${(props) => `${props.$height}px`};
`;

export const TabBarItem = styled.div<TabBarSelect>`
  transition: all 0.4s ease;
  border-bottom: 1px solid ${(props) => (props.$isSelect ? 'black' : '#eee')};
`;

export const TabBarInput = styled.input`
  display: none;
`;
