import { useCallback, useState } from 'react';

const useModal = () => {
  const [isOpen, setOpen] = useState(false);

  const openModal = useCallback(() => {
    setOpen(true);
  }, []);

  const closeModal = useCallback(() => {
    setOpen(false);
  }, []);

  return {
    isOpen,
    openModal,
    closeModal,
  };
};

export default useModal;
