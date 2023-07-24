import ReactDOM from 'react-dom';
import { BaseModal, CloseButton, ModalBackdrop } from './style';
import { PropsWithChildren } from 'react';

interface ModalProps {
  closeModal: () => void;
}

const Modal = ({ closeModal, children }: PropsWithChildren<ModalProps>) => {
  return ReactDOM.createPortal(
    <>
      <ModalBackdrop onClick={closeModal} />
      <BaseModal>
        {children}
        <CloseButton onClick={closeModal} />
      </BaseModal>
    </>,
    document.querySelector('body') as HTMLBodyElement,
  );
};

export default Modal;
