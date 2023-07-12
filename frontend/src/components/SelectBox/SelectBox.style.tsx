import styled from 'styled-components';

interface StyledSelectBoxProps {
  $minWidth: number;
  $minHeight: number;
}

export const BaseSelectBox = styled.span<StyledSelectBoxProps>`
  position: relative;
  display: inline-block;
  margin-right: 1px;
  min-height: ${(props) => `${props.$minHeight}px`};
  max-height: ${(props) => `${props.$minHeight}px`};
  overflow: hidden;
  min-width: ${(props) => `${props.$minWidth}px`};

  cursor: pointer;
  text-align: left;
  white-space: nowrap;

  outline: none;
  border: 1px solid ${({ theme }) => theme.colors.main600};
  border-radius: 4px;
  background-color: white;

  transition: 0.4s all ease-in-out;

  input:checked + label {
    display: block;
    border-top: none;
    position: absolute;
    top: 0;
    width: 100%;

    &:nth-child(2) {
      margin-top: 0;
      position: relative;
    }
  }

  &::after {
    content: '';
    position: absolute;
    right: 12px;
    top: 14px;
    border: 5px solid ${({ theme }) => theme.colors.point};
    border-color: ${({ theme }) => theme.colors.point} transparent transparent transparent;
    transition: 0.4s all ease-in-out;
  }
  &.expanded {
    border: 1px solid ${({ theme }) => theme.colors.point};
    background: #fff;
    border-radius: 12px;
    padding: 0;
    box-shadow: rgba(0, 0, 0, 0.1) 3px 3px 5px 0px;
    max-height: 400px;

    label {
      border-top: 1px solid ${({ theme }) => theme.colors.gray};
      &:hover {
        color: ${({ theme }) => theme.colors.main600};
      }
    }
    input:checked + label {
      color: ${({ theme }) => theme.colors.main600};
    }

    &::after {
      transform: rotate(-180deg);
      top: 8px;
    }
  }
`;

export const SelectContent = styled.input`
  width: 1px;
  height: 1px;
  display: inline-block;
  position: absolute;
  opacity: 0.01;
`;

export const LabelContent = styled.label`
  border-top: 1px solid ${({ theme }) => theme.colors.gray};
  display: block;
  height: 33px;
  line-height: 33px;
  padding-left: 12px;
  padding-right: 66px;
  cursor: pointer;
  position: relative;
  transition: 0.4s color ease-in-out;

  &:nth-child(2) {
    margin-top: 33px;

    border-top: 1px solid ${({ theme }) => theme.colors.gray};
  }
`;
