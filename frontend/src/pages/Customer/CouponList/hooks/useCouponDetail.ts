import { useState } from 'react';

const useCouponDetail = () => {
  const [isDetail, setIsDetail] = useState(false);
  const [isFlippedCouponShown, setIsFlippedCouponShown] = useState(false);

  const openCouponDetail = () => {
    setIsDetail(true);

    setTimeout(() => {
      setIsFlippedCouponShown(true);
    }, 700);
  };

  const closeCouponDetail = () => {
    setIsDetail(false);
    setIsFlippedCouponShown(false);
  };

  return { isDetail, isFlippedCouponShown, openCouponDetail, closeCouponDetail };
};

export default useCouponDetail;
