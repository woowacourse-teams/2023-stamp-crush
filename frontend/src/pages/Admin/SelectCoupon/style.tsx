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

export const CouponSelector = styled.input`
  appearance: none;
`;

export const CouponSelectorLabel = styled.label<{ $isChecked: boolean }>`
  position: relative;
  width: 200px;
  height: ${({ $isChecked }) => ($isChecked ? '200px' : '65px')};

  color: ${({ $isChecked }) => ($isChecked ? 'black' : '#888')};

  padding: 20px;
  border-radius: 20px;
  border: 3px solid ${({ theme }) => theme.colors.main};
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

export const CouponLabelContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 10px;
`;

export const SelectTitle = styled.header`
  font-size: 20px;
  font-weight: 700;
`;

export const SelectDescription = styled.p`
  margin-top: 20px;
  line-height: 20px;
`;
