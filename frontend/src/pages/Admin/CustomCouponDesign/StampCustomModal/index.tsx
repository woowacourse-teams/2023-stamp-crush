import { Dispatch, MouseEvent, SetStateAction, useEffect, useRef, useState } from 'react';
import Modal from '../../../../components/Modal';
import { BackCouponWrapper, BackImage, ButtonContainer, Stamp, StampBadge } from './style';
import Text from '../../../../components/Text';
import Button from '../../../../components/Button';
import { StampCoordinate } from '../ChoiceTemplate';
import { parseStampCount } from '../../../../utils';

interface Props {
  isOpen: boolean;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  stampPos: StampCoordinate[];
  setStampPos: Dispatch<SetStateAction<StampCoordinate[]>>;
  backImgFileUrl: string;
  stampImgFileUrl: string;
  maxStampCount: string;
}

const StampCustomModal = ({
  isOpen,
  setIsOpen,
  stampPos,
  setStampPos,
  backImgFileUrl,
  stampImgFileUrl,
  maxStampCount,
}: Props) => {
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

  const recordStampCoordinates = (event: MouseEvent<HTMLImageElement>) => {
    if (drawStampPos.length >= parseStampCount(maxStampCount)) return;

    if (modalRect && event.target instanceof HTMLImageElement) {
      const boundX = modalRect.left;
      const boundY = modalRect.top;

      const drawX = event.clientX - boundX;
      const drawY = event.clientY - boundY;

      const X = (event.clientX - event.target.getBoundingClientRect().left) / 2;
      const Y = (event.clientY - event.target.getBoundingClientRect().top) / 2;

      setStampPos((prevPos) => [
        ...prevPos,
        { order: prevPos.length + 1, xCoordinate: X, yCoordinate: Y },
      ]);
      setDrawStampPos((prevPos) => [...prevPos, { x: drawX, y: drawY }]);
    }
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  const removeLastStamp = () => {
    const updatedDrawStampPos = drawStampPos.slice(0, drawStampPos.length - 1);
    const updatedStampPos = stampPos.slice(0, stampPos.length - 1);

    setDrawStampPos(updatedDrawStampPos);
    setStampPos(updatedStampPos);
  };

  return (
    <>
      {isOpen && (
        <Modal closeModal={closeModal} ref={modalRef}>
          <Text variant="subTitle">스탬프 위치 설정</Text>
          <BackCouponWrapper onClick={recordStampCoordinates}>
            {drawStampPos.map((pos, idx) => (
              <Stamp key={idx} X={pos.x} Y={pos.y}>
                <StampBadge>{idx + 1}</StampBadge>
                <img src={stampImgFileUrl} width={50} height={50} />
              </Stamp>
            ))}
            <BackImage src={backImgFileUrl} />
          </BackCouponWrapper>
          <ButtonContainer>
            <Button variant="secondary" onClick={removeLastStamp}>
              되돌리기
            </Button>
            <Text>{`${drawStampPos.length}/${maxStampCount}`}</Text>
            <Button
              variant="primary"
              onClick={closeModal}
              disabled={drawStampPos.length !== parseStampCount(maxStampCount)}
            >
              저장하기
            </Button>
          </ButtonContainer>
        </Modal>
      )}
    </>
  );
};

export default StampCustomModal;
