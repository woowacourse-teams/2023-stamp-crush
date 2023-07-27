import ReactDOM from 'react-dom';
import { BaseModal, CloseButton, ModalBackdrop } from './style';
import { ForwardedRef, PropsWithChildren, forwardRef } from 'react';

interface ModalProps {
  closeModal: () => void;
}

const Modal = forwardRef(
  ({ closeModal, children }: PropsWithChildren<ModalProps>, ref: ForwardedRef<HTMLDivElement>) => {
    return ReactDOM.createPortal(
      <>
        <ModalBackdrop onClick={closeModal} />
        <BaseModal ref={ref}>
          {children}
          <CloseButton onClick={closeModal} />
        </BaseModal>
      </>,
      document.querySelector('body') as HTMLBodyElement,
    );
  },
);

Modal.displayName = 'Modal';

export default Modal;
