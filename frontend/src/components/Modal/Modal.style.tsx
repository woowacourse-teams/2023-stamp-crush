import styled from 'styled-components';

export const ModalBackdrop = styled.div`
  position: absolute;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  z-index: 0;

  background-color: rgba(0, 0, 0, 0.3);
`;

export const BaseModal = styled.div`
  position: fixed;

  top: 50%;
  left: 50%;
  width: 70vw;
  height: 50vh;

  padding: 35px;
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.1) 3px 3px 5px 0px;

  z-index: 1;
  background-color: ${({ theme }) => theme.colors.white};

  transform: translate(-50%, -50%);

  @media screen and (min-width: 768px) and (max-width: 1024px) {
    width: 70vw;
    height: 60vh;
  }
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
