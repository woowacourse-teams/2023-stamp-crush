import { RefObject, TouchEvent, useState } from 'react';
import { Coupon } from '../../../../types/domain/coupon';

const useCouponList = (
  couponListContainerRef: RefObject<HTMLDivElement>,
  isDetail: boolean,
  coupons: Coupon[],
) => {
  const [startY, setStartY] = useState(0);
  const [endY, setEndY] = useState(0);

  const [currentIndex, setCurrentIndex] = useState(0);
  const [isLast, setIsLast] = useState(false);

  const swapCoupon = (e: TouchEvent<HTMLDivElement>) => {
    if (!couponListContainerRef.current || isDetail) return;
    if (coupons.length === 1) return;

    const coupon = couponListContainerRef.current.lastElementChild;
    if (e.target !== coupon) return;
    setIsLast(true);

    setTimeout(() => {
      setIsLast(false);
      couponListContainerRef.current?.prepend(coupon);
      const newCoupon = couponListContainerRef.current?.lastElementChild;
      if (newCoupon instanceof HTMLButtonElement) newCoupon.focus();
    }, 700);
  };

  const changeCurrentIndex = (index: number) => {
    setCurrentIndex((prevIndex) => {
      if (coupons) return index === 0 ? coupons.length - 1 : index - 1;
      return prevIndex;
    });
  };

  const onTouchStart = (e: TouchEvent<HTMLDivElement>) => {
    setStartY(e.touches[0].clientY);
  };

  const onTouchMove = (e: TouchEvent<HTMLDivElement>) => {
    setEndY(e.touches[0].clientY);
  };

  const onTouchEnd = (e: TouchEvent<HTMLDivElement>) => {
    const deltaY = startY - endY;
    if (deltaY < 100 || deltaY > 250) return;

    const dataIndex = e.target instanceof HTMLButtonElement ? e.target.dataset.index : undefined;
    if (dataIndex !== undefined) {
      changeCurrentIndex(Number(dataIndex));
      setStartY(0);
      setEndY(0);
      swapCoupon(e);
    }
  };

  return {
    isLast,
    setIsLast,
    currentIndex,
    setCurrentIndex,
    onTouchStart,
    onTouchEnd,
    onTouchMove,
  };
};

export default useCouponList;
