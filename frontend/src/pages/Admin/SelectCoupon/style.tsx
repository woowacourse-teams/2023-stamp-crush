import styled from 'styled-components';

export const CouponSelectContainer = styled.main`
  display: flex;
  flex-direction: column;

  width: fit-content;
  margin-top: 48px;

  & > button {
    margin-left: auto;
  }
`;

export const TitleWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

export const ExpirationDate = styled.span`
  font-size: 20px;
  color: #888;
`;

export const CouponSelector = styled.input<{ $size: number }>`
  width: ${(props) => props.size}px;
  height: ${(props) => props.size}px;
`;

export const CouponSelectorLabel = styled.label<{ $isChecked: boolean }>`
  color: ${(props) => (props.$isChecked ? 'black' : '#888')};
`;

export const SelectorItemWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 20px;
`;

export const CouponSelectorContainer = styled.div`
  display: flex;
  flex-direction: row;
`;

export const CouponSelectorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;
