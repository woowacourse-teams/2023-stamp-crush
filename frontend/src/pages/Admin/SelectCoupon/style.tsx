import styled from 'styled-components';

interface CouponSelectorProps {
  size: number;
}

interface CouponSelectorLabelProps {
  $isChecked: boolean;
}

export const CouponSelectContainer = styled.main`
  display: flex;
  flex-direction: column;

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

export const CouponSelector = styled.input<CouponSelectorProps>`
  width: ${(props) => props.size}px;
  height: ${(props) => props.size}px;
`;

export const CouponSelectorLabel = styled.label<CouponSelectorLabelProps>`
  color: ${(props) => (props.$isChecked ? 'black' : '#888')};
`;

export const CouponSelectorWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 20px;
`;
