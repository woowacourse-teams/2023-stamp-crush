import styled from 'styled-components';

export const TitleWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`;

export const CouponSelector = styled.input`
  appearance: none;
`;

export const CouponSelectorLabel = styled.label<{ $isChecked: boolean }>`
  position: relative;
  width: 200px;
  height: ${({ $isChecked }) => ($isChecked ? '200px' : '60px')};
  border-radius: 20px;
  padding: 17px 20px;
  color: ${({ $isChecked, theme }) => ($isChecked ? 'black' : theme.colors.gray)};
  border: 3px solid
    ${({ $isChecked, theme }) => ($isChecked ? theme.colors.main : theme.colors.gray)};
  box-shadow: ${({ $isChecked }) => ($isChecked ? '0px 0px 5px 2px rgba(0, 0, 0, 0.25)' : '')};

  overflow: hidden;
  transform: ${({ $isChecked }) => ($isChecked ? 'scale(1.1)' : '')};
  transition: all 0.2s;

  cursor: pointer;

  & > svg {
    position: absolute;
    bottom: 10px;
    right: 10px;
  }
`;

export const SelectorItemWrapper = styled.div`
  display: flex;
  align-items: flex-start;
`;

export const CouponLabelContainer = styled.div`
  display: flex;
  gap: 20px;
`;

export const SelectTitle = styled.header`
  font-size: 20px;
  font-weight: 700;
`;

export const SelectDescription = styled.p`
  font-size: 16px;
  margin-top: 20px;
  line-height: 20px;
`;
