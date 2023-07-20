import { useCallback, useState } from 'react';

const useModal = () => {
  const [isOpen, setOpen] = useState(false);

  const openModal = useCallback(() => {
    setOpen(true);
    document.body.style.overflow = 'hidden';
  }, []);

  const closeModal = useCallback(() => {
    setOpen(false);
    document.body.style.overflow = 'unset';
  }, []);

  return {
    isOpen,
    openModal,
    closeModal,
  };
};

export default useModal;
