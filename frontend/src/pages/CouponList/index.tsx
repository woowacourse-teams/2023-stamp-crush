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
import { useRef, useState } from 'react';
import { getCoupons } from '../../api/get';
import { useQuery } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { ROUTER_PATH } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import ProgressBar from '../../components/ProgressBar';
import Color from 'color-thief-react';
import { useNavigate } from 'react-router-dom';
import Alert from '../../components/Alert';
import useModal from '../../hooks/useModal';
import { CiCircleMore } from 'react-icons/ci';

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
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const [currentIndex, setCurrentIndex] = useState(0);
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

  const changeCurrentIndex = (index: number) => () => {
    setCurrentIndex(index);
  };

  const getCurrentCoupon = () => data.coupons[currentIndex];

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  const changeFavorites = (isFavorites: boolean) => () => {
    openModal();
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
            <AiFillStar size={40} color={'#FFD600'} onClick={changeFavorites(false)} />
          ) : (
            <AiOutlineStar size={40} color={'#FFD600'} onClick={changeFavorites(true)} />
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
      <DetailButton>
        <CiCircleMore size={36} color={'#424242'} />
      </DetailButton>
      {isOpen && (
        <Alert
          text={`${getCurrentCoupon().cafeInfo.name}를 찜하시겠어요?`}
          rightOption={'찜하기'}
          leftOption={'닫기'}
          onClickRight={closeModal}
          onClickLeft={closeModal}
        />
      )}
    </>
  );
};

export default CouponList;
