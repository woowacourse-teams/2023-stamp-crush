import Coupon from './Coupon';
import { CafeName, CouponListContainer } from './style';
import { useRef, useState } from 'react';
import { getCoupons } from '../../api/get';
import { useQuery } from '@tanstack/react-query';

interface CouponType {
  cafeInfo: {
    id: number;
    name: string;
  };
  couponInfos: [
    {
      id: number;
      isFavorites: boolean;
      stampCount: number;
      maxStampCount: number;
      rewardName: string;
      frontImageUrl: string;
      backImageUrl: string;
      stampImageUrl: string;
      // TODO: coordinate
    },
  ];
}

const CouponList = () => {
  const [currentCafeIndex, setCurrentCafeIndex] = useState(0);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [isLast, setIsLast] = useState(false);
  const { data, status } = useQuery<{ coupons: CouponType[] }>(['coupons'], getCoupons, {});

  if (status === 'error') return <>에러가 발생했습니다.</>;
  if (status === 'loading') return <>로딩 중입니다.</>;

  const swapCoupon = (e: React.MouseEvent<HTMLDivElement>) => {
    if (!couponListContainerRef.current) return;

    const coupon = couponListContainerRef.current.lastElementChild;
    if (e.target !== coupon) return;
    setIsLast(true);

    setTimeout(() => {
      setIsLast(false);
      couponListContainerRef.current?.prepend(coupon);
    }, 700);
  };

  const changeCurrentCafeIndex = (index: number) => () => {
    setCurrentCafeIndex(index);
  };

  return (
    <>
      <CafeName>{data.coupons[currentCafeIndex].cafeInfo.name}</CafeName>
      <CouponListContainer ref={couponListContainerRef} onClick={swapCoupon} $isLast={isLast}>
        {data.coupons.map(({ cafeInfo, couponInfos }, index) => (
          <Coupon
            key={cafeInfo.id}
            frontImageUrl={couponInfos[0].frontImageUrl}
            data-index={index}
            onClick={changeCurrentCafeIndex(index)}
          />
        ))}
      </CouponListContainer>
    </>
  );
};

export default CouponList;
