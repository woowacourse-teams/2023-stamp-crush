import { Dispatch, MouseEvent, SetStateAction, useEffect, useRef, useState } from 'react';
import Modal from '../../../../components/Modal';
import { BackCouponWrapper, Stamp, StampBadge } from './style';
import Text from '../../../../components/Text';

interface Props {
  isOpen: boolean;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  stampPos: { x: number; y: number }[];
  setStampPos: Dispatch<SetStateAction<{ x: number; y: number }[]>>;
}

const StampCustomModal = ({ isOpen, setIsOpen, stampPos, setStampPos }: Props) => {
  const [modalRect, setModalRect] = useState<DOMRect | null>(null);
  const [drawStampPos, setDrawStampPos] = useState<{ x: number; y: number }[]>([]);
  const modalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (isOpen) {
      const modalElement = modalRef.current;
      if (modalElement) {
        setModalRect(modalElement.getBoundingClientRect());
      }
    }
  }, [isOpen]);

  const recordStampPos = (event: MouseEvent<HTMLDivElement>) => {
    if (modalRect && event.target instanceof HTMLDivElement) {
      const boundX = modalRect.left;
      const boundY = modalRect.top;

      const drawX = event.clientX - boundX;
      const drawY = event.clientY - boundY;

      const X = (event.clientX - event.target.getBoundingClientRect().left) / 2;
      const Y = (event.clientY - event.target.getBoundingClientRect().top) / 2;

      setStampPos((prevPos) => [...prevPos, { x: X, y: Y }]);
      setDrawStampPos((prevPos) => [...prevPos, { x: drawX, y: drawY }]);
    }
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  return (
    <>
      {isOpen && (
        <Modal closeModal={closeModal} ref={modalRef}>
          <Text variant="subTitle">스탬프 위치 설정</Text>
          <BackCouponWrapper onClick={recordStampPos}>
            {drawStampPos.map((pos, idx) => (
              <Stamp key={idx} X={pos.x} Y={pos.y}>
                <StampBadge>{idx + 1}</StampBadge>
                <img src="https://picsum.photos/seed/picsum/50" width={25} height={25} />
              </Stamp>
            ))}
          </BackCouponWrapper>
        </Modal>
      )}
    </>
  );
};

export default StampCustomModal;
