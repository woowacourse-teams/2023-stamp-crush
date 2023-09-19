import { Dispatch, MouseEvent, SetStateAction, useEffect, useRef, useState } from 'react';
import Modal from '../../../../../components/Modal';
import { BackCouponWrapper, BackImage, ButtonContainer, Stamp, StampBadge } from './style';
import Text from '../../../../../components/Text';
import Button from '../../../../../components/Button';
import { StampCoordinate } from '../../../../../types/domain/coupon';
import { Coordinate } from '../../../../../types/utils';

interface Props {
  isOpen: boolean;
  stampCoordinates: StampCoordinate[];
  backImgFileUrl: string;
  stampImgFileUrl: string;
  maxStampCount: number;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  setStampCoordinates: Dispatch<SetStateAction<StampCoordinate[]>>;
}

const StampCustomModal = ({
  isOpen,
  stampCoordinates,
  backImgFileUrl,
  stampImgFileUrl,
  maxStampCount,
  setIsOpen,
  setStampCoordinates,
}: Props) => {
  const [modalRect, setModalRect] = useState<DOMRect | null>(null);
  const [drawStampCoordinates, setDrawStampCoordinates] = useState<Coordinate[]>([]);
  const modalRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (isOpen) {
      const modalElement = modalRef.current;
      if (modalElement) {
        setModalRect(modalElement.getBoundingClientRect());
      }
    }
  }, [isOpen]);

  const recordStampCoordinates = (e: MouseEvent<HTMLImageElement>) => {
    if (drawStampCoordinates.length >= maxStampCount) return;

    if (modalRect && e.target instanceof HTMLImageElement) {
      const boundX = modalRect.left;
      const boundY = modalRect.top;

      const drawX = e.clientX - boundX;
      const drawY = e.clientY - boundY;

      const xCoordinate = (e.clientX - e.target.getBoundingClientRect().left) / 2;
      const yCoordinate = (e.clientY - e.target.getBoundingClientRect().top) / 2;

      setStampCoordinates((prevPos) => [
        ...prevPos,
        { order: prevPos.length + 1, xCoordinate, yCoordinate },
      ]);
      setDrawStampCoordinates((prevPos) => [
        ...prevPos,
        { xCoordinate: drawX, yCoordinate: drawY },
      ]);
    }
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  const removeLastStamp = () => {
    setDrawStampCoordinates(drawStampCoordinates.slice(0, drawStampCoordinates.length - 1));
    setStampCoordinates(stampCoordinates.slice(0, stampCoordinates.length - 1));
  };

  return (
    <>
      {isOpen && (
        <Modal closeModal={closeModal} ref={modalRef}>
          <Text variant="subTitle">스탬프 위치 설정</Text>
          <BackCouponWrapper onClick={recordStampCoordinates}>
            {drawStampCoordinates.map((coord, index) => (
              <Stamp key={index} $x={coord.xCoordinate} $y={coord.yCoordinate}>
                <StampBadge>{index + 1}</StampBadge>
                <img src={stampImgFileUrl} width={50} height={50} />
              </Stamp>
            ))}
            <BackImage src={backImgFileUrl} />
          </BackCouponWrapper>
          <ButtonContainer>
            <Button variant="secondary" onClick={removeLastStamp}>
              되돌리기
            </Button>
            <Text>{`${drawStampCoordinates.length}/${maxStampCount}`}</Text>
            <Button
              variant="primary"
              onClick={closeModal}
              disabled={drawStampCoordinates.length !== maxStampCount}
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
