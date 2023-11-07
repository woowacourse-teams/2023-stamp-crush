import styled from 'styled-components';
import { Z_INDEX } from '../../constants/magicNumber';

export const ModalBackdrop = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100vw;
  height: 120vh;
  z-index: ${Z_INDEX.highest};
  background-color: rgba(0, 0, 0, 0.3);
`;

export const BaseModal = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  width: fit-content;
  height: auto;
  padding: 35px;
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.1) 3px 3px 5px 0px;

  z-index: ${Z_INDEX.highest + Z_INDEX.above};
  background-color: ${({ theme }) => theme.colors.white};

  transform: translate(-50%, -50%);
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 16px;
  right: 16px;
  width: 25px;
  height: 25px;

  border: 2px solid black;
  border-radius: 50%;

  background: transparent;
  cursor: pointer;

  &:before,
  &:after {
    content: '';
    position: absolute;
    left: 50%;
    top: 50%;

    width: 12px;
    height: 2px;

    border-radius: 4px;
    background: #000;
  }

  &:before {
    transform: translate(-50%, -50%) rotate(-45deg);
  }

  &:after {
    transform: translate(-50%, -50%) rotate(45deg);
  }
`;
