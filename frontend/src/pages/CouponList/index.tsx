import Coupon from './Coupon';
import {
  BackDrop,
  CafeName,
  CouponListContainer,
  HeaderContainer,
  InfoContainer,
  LogoImg,
  MaxStampCount,
  NameContainer,
  ProgressBarContainer,
  StampCount,
} from './style';
import { useRef, useState } from 'react';
import { getCoupons } from '../../api/get';
import { useQuery } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import SelectBox from '../../components/SelectBox';
import { CUSTOMERS_ORDER_OPTIONS } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import ProgressBar from '../../components/ProgressBar';
import Color from 'color-thief-react';

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
  const [currentIndex, setCurrentIndex] = useState(0);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [isLast, setIsLast] = useState(false);
  const { data, status } = useQuery<{ coupons: CouponType[] }>(['coupons'], getCoupons, {});
  const [option, setOption] = useState({ key: 'stampCount', value: '스탬프순' });

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

  const changeCurrentIndex = (index: number) => () => {
    setCurrentIndex(index);
  };

  const getCurrentCoupon = () => data.coupons[currentIndex];
  const imgUrl = getCurrentCoupon().couponInfos[0].frontImageUrl;

  return (
    <>
      <HeaderContainer>
        <LogoImg src={AdminHeaderLogo} />
        <div>
          <SelectBox
            options={CUSTOMERS_ORDER_OPTIONS}
            checkedOption={option}
            setCheckedOption={setOption}
            width={110}
          />
          <GoPerson size={24} />
        </div>
      </HeaderContainer>
      <InfoContainer>
        <NameContainer>
          <CafeName>{getCurrentCoupon().cafeInfo.name}</CafeName>
          {getCurrentCoupon().couponInfos[0].isFavorites ? (
            <AiFillStar size={36} />
          ) : (
            <AiOutlineStar size={36} />
          )}
        </NameContainer>
        <ProgressBarContainer>
          <Color src={imgUrl} format="hex" crossOrigin="anonymous">
            {({ data: color }) => (
              <>
                <BackDrop $couponMainColor={color ? color : 'gray'} />
                <ProgressBar
                  stampCount={getCurrentCoupon().couponInfos[0].stampCount}
                  maxCount={getCurrentCoupon().couponInfos[0].maxStampCount}
                  progressColor={color ? color : 'skyblue'}
                />
              </>
            )}
          </Color>
          <StampCount>{getCurrentCoupon().couponInfos[0].stampCount}</StampCount>/
          <MaxStampCount>{getCurrentCoupon().couponInfos[0].maxStampCount}</MaxStampCount>
        </ProgressBarContainer>
      </InfoContainer>

      <CouponListContainer ref={couponListContainerRef} onClick={swapCoupon} $isLast={isLast}>
        {data.coupons.map(({ cafeInfo, couponInfos }, index) => (
          <Coupon
            key={cafeInfo.id}
            frontImageUrl={couponInfos[0].frontImageUrl}
            data-index={index}
            onClick={changeCurrentIndex(index)}
          />
        ))}
      </CouponListContainer>
    </>
  );
};

export default CouponList;
