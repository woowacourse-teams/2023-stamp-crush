import ReactDOM from 'react-dom';
import { BaseModal, CloseButton, ModalBackdrop } from './Modal.style';
import { PropsWithChildren, useState } from 'react';

interface ModalProps extends PropsWithChildren {
  id?: string;
}

const Modal = ({ children }: ModalProps) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
    document.body.style.overflow = 'hidden';
  };

  return ReactDOM.createPortal(
    isModalOpen && (
      <>
        <ModalBackdrop />
        <BaseModal>
          {children}
          <CloseButton />
        </BaseModal>
      </>
    ),
    document.querySelector('body') as HTMLBodyElement,
  );
};

export default Modal;
