import Coupon from './Coupon';
import {
  BackDrop,
  CafeName,
  CouponListContainer,
  DetailButton,
  HeaderContainer,
  InfoContainer,
  LogoImg,
  MaxStampCount,
  NameContainer,
  ProgressBarContainer,
  StampCount,
} from './style';
import { MouseEvent, useRef, useState } from 'react';
import { getCafeInfo, getCoupons } from '../../api/get';
import { useQuery } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { ROUTER_PATH } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import ProgressBar from '../../components/ProgressBar';
import Color from 'color-thief-react';
import { useNavigate } from 'react-router-dom';
import { TbZoomCheck } from 'react-icons/tb';
import CouponDetail from './CouponDetail';

interface StampCoordinate {
  order: number;
  xCoordinate: number;
  yCoordinate: number;
}

export interface CouponType {
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
      coordinates: StampCoordinate[];
    },
  ];
}

export interface CafeType {
  cafe: {
    id: number;
    name: string;
    introduction: string;
    openTime: string;
    closeTime: string;
    telephoneNumber: string;
    cafeImageUrl: string;
    roadAddress: string;
    detailAddress: string;
  };
}

const CouponList = () => {
  const navigate = useNavigate();
  const [currentIndex, setCurrentIndex] = useState(0);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [isLast, setIsLast] = useState(false);
  const [isDetail, setIsDetail] = useState(false);
  const [isDetailShown, setIsDetailShown] = useState(false);
  const [cafeId, setCafeId] = useState(0);

  const { data: couponsInfosData, status } = useQuery<{ coupons: CouponType[] }>(
    ['coupons'],
    getCoupons,
    {},
  );

  const { data: cafeData, status: cafeStatus } = useQuery(
    ['cafeInfos', cafeId],
    () => getCafeInfo(cafeId),
    {},
  );

  if (status === 'error') return <>에러가 발생했습니다.</>;
  if (status === 'loading') return <>로딩 중입니다.</>;

  const swapCoupon = (e: MouseEvent<HTMLDivElement>) => {
    if (!couponListContainerRef.current || isDetail) return;

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

  const getCurrentCoupon = () => couponsInfosData.coupons[currentIndex];

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  const openCouponDetail = () => {
    setCafeId(getCurrentCoupon().cafeInfo.id);

    setIsDetail(true);
    setTimeout(() => {
      setIsDetailShown(true);
    }, 700);
  };

  const closeCouponDetail = () => {
    setIsDetail(false);
    setIsDetailShown(false);
  };

  return (
    <>
      <HeaderContainer>
        <LogoImg src={AdminHeaderLogo} />
        <GoPerson size={24} onClick={navigateMyPage} />
      </HeaderContainer>
      <InfoContainer>
        <NameContainer>
          <CafeName>{getCurrentCoupon().cafeInfo.name}</CafeName>
          {getCurrentCoupon().couponInfos[0].isFavorites ? (
            <AiFillStar size={40} color={'#FFD600'} />
          ) : (
            <AiOutlineStar size={40} color={'#FFD600'} />
          )}
        </NameContainer>
        <ProgressBarContainer>
          <Color
            src={getCurrentCoupon().couponInfos[0].frontImageUrl}
            format="hex"
            crossOrigin="anonymous"
          >
            {({ data: color }) => (
              <>
                <BackDrop $couponMainColor={color ? color : 'gray'} />
                <ProgressBar
                  stampCount={getCurrentCoupon().couponInfos[0].stampCount}
                  maxCount={getCurrentCoupon().couponInfos[0].maxStampCount}
                  progressColor={color}
                />
              </>
            )}
          </Color>
          <StampCount>{getCurrentCoupon().couponInfos[0].stampCount}</StampCount>/
          <MaxStampCount>{getCurrentCoupon().couponInfos[0].maxStampCount}</MaxStampCount>
        </ProgressBarContainer>
      </InfoContainer>
      <CouponListContainer
        ref={couponListContainerRef}
        onClick={swapCoupon}
        $isLast={isLast}
        $isDetail={isDetail}
        $isShown={isDetailShown}
      >
        {couponsInfosData.coupons.map(({ cafeInfo, couponInfos }, index) => (
          <>
            <Coupon
              key={cafeInfo.id}
              coupon={{ cafeInfo, couponInfos }}
              data-index={index}
              onClick={changeCurrentIndex(index)}
            />
          </>
        ))}
      </CouponListContainer>
      {cafeStatus !== 'loading' && cafeStatus !== 'error' && (
        <CouponDetail
          coupon={getCurrentCoupon()}
          cafe={cafeData}
          closeDetail={closeCouponDetail}
          isDetail={isDetail}
          isShown={isDetailShown}
        />
      )}
      <DetailButton onClick={openCouponDetail}>
        <TbZoomCheck size={32} color={'#424242'} />
      </DetailButton>
    </>
  );
};

export default CouponList;
