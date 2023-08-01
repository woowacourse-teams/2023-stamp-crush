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
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { ROUTER_PATH } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { AiFillStar, AiOutlineStar } from 'react-icons/ai';
import ProgressBar from '../../components/ProgressBar';
import Color from 'color-thief-react';
import { useNavigate } from 'react-router-dom';
import CouponDetail from './CouponDetail';
import type { CafeRes, CouponRes, PostIsFavoritesReq } from '../../types';
import Alert from '../../components/Alert';
import useModal from '../../hooks/useModal';
import { CiCircleMore } from 'react-icons/ci';
import { postIsFavorites } from '../../api/post';

const CouponList = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isLast, setIsLast] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [isDetail, setIsDetail] = useState(false);
  const [isFlippedCouponShown, setIsFlippedCouponShown] = useState(false);
  const [cafeId, setCafeId] = useState(0);
  const couponListContainerRef = useRef<HTMLDivElement>(null);

  const queryClient = useQueryClient();
  const { data: cafeData, status: cafeStatus } = useQuery<CafeRes>(['cafeInfos'], () =>
    getCafeInfo(cafeId),
  );
  const { data: couponRes, status: couponStatus } = useQuery<CouponRes>(['coupons'], getCoupons, {
    onSuccess: (data) => {
      setCurrentIndex(data.coupons.length - 1);
    },
  });
  const { mutate: mutateIsFavorites } = useMutation(
    ({ cafeId, isFavorites }: PostIsFavoritesReq) => postIsFavorites({ cafeId, isFavorites }),
    {
      onSuccess: () => {
        closeModal();
      },
      onMutate: async () => {
        await queryClient.cancelQueries(['coupons']);
        queryClient.setQueryData<CouponRes>(['coupons'], (prev) => {
          if (!prev) return;
          prev.coupons[currentIndex].couponInfos[0].isFavorites =
            !prev.coupons[currentIndex].couponInfos[0].isFavorites;
          return undefined;
        });
      },
    },
  );

  if (couponStatus === 'error') return <>에러가 발생했습니다.</>;
  if (couponStatus === 'loading') return <>로딩 중입니다.</>;

  const { coupons } = couponRes;
  const currentCoupon = coupons[currentIndex];

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
    setCurrentIndex((prevIndex) => {
      if (coupons) return index === 0 ? coupons.length - 1 : index - 1;
      return prevIndex;
    });
  };

  const navigateMyPage = () => {
    navigate(ROUTER_PATH.myPage);
  };

  const openCouponDetail = () => {
    setCafeId(currentCoupon.cafeInfo.id);

    setIsDetail(true);
    setTimeout(() => {
      setIsFlippedCouponShown(true);
    }, 700);
  };

  const closeCouponDetail = () => {
    setIsDetail(false);
    setIsFlippedCouponShown(false);
  };

  const openAlert = () => {
    openModal();

    currentCoupon.couponInfos[0].isFavorites
      ? setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에서 해제하시겠어요?`)
      : setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에 등록하시겠어요?`);
  };

  const changeFavorites = () => {
    mutateIsFavorites({
      cafeId: currentCoupon.cafeInfo.id,
      isFavorites: currentCoupon.couponInfos[0].isFavorites,
    });
  };

  return (
    <>
      <HeaderContainer>
        <LogoImg src={AdminHeaderLogo} />
        <GoPerson size={24} onClick={navigateMyPage} />
      </HeaderContainer>
      <InfoContainer>
        <NameContainer>
          <CafeName aria-label="카페 이름">{currentCoupon.cafeInfo.name}</CafeName>
          {currentCoupon.couponInfos[0].isFavorites ? (
            <AiFillStar size={40} color={'#FFD600'} onClick={openAlert} />
          ) : (
            <AiOutlineStar size={40} color={'#FFD600'} onClick={openAlert} />
          )}
        </NameContainer>
        <ProgressBarContainer aria-label="스탬프 개수">
          <Color
            src={currentCoupon.couponInfos[0].frontImageUrl}
            format="hex"
            crossOrigin="anonymous"
          >
            {({ data: color }) => (
              <>
                <BackDrop $couponMainColor={color ? color : 'gray'} />
                <ProgressBar
                  stampCount={currentCoupon.couponInfos[0].stampCount}
                  maxCount={currentCoupon.couponInfos[0].maxStampCount}
                  color={color}
                />
              </>
            )}
          </Color>
          <StampCount>{currentCoupon.couponInfos[0].stampCount}</StampCount>/
          <MaxStampCount>{currentCoupon.couponInfos[0].maxStampCount}</MaxStampCount>
        </ProgressBarContainer>
      </InfoContainer>
      <CouponListContainer
        ref={couponListContainerRef}
        onClick={swapCoupon}
        $isLast={isLast}
        $isDetail={isDetail}
        $isShown={isFlippedCouponShown}
      >
        {coupons.map(({ cafeInfo, couponInfos }, index) => (
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
          coupon={currentCoupon}
          cafe={cafeData.cafe}
          closeDetail={closeCouponDetail}
          isDetail={isDetail}
          isShown={isFlippedCouponShown}
        />
      )}
      <DetailButton onClick={openCouponDetail} $isDetail={isDetail} aria-label="쿠폰 상세 보기">
        <CiCircleMore size={36} color={'#424242'} />
      </DetailButton>
      {isOpen && (
        <Alert
          text={alertMessage}
          rightOption={'네'}
          leftOption={'아니오'}
          onClickRight={changeFavorites}
          onClickLeft={closeModal}
        />
      )}
    </>
  );
};

export default CouponList;
