import Coupon from './Coupon';
import { CouponListContainer, Test } from './style';
import { useRef, useState } from 'react';

const randomColor = ['blue', 'orange', 'green', 'pink'];

const CouponList = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [coupons, setCoupons] = useState<string[]>(randomColor);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [isLast, setIsLast] = useState(false);

  const swapCoupon = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    const card = couponListContainerRef.current?.lastElementChild as HTMLDivElement;
    if (e.target !== card) return;

    setIsLast(true);

    setTimeout(() => {
      setIsLast(false);
      couponListContainerRef.current?.prepend(card);
    }, 700);
  };

  return (
    <CouponListContainer ref={couponListContainerRef} onClick={swapCoupon} $isLast={isLast}>
      {coupons.map((color, index) => (
        <Test key={index} style={{ background: color }} />
      ))}
    </CouponListContainer>
  );
};

export default CouponList;
