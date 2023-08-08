import Coupon from './Coupon';
import {
  CouponListContainer,
  DetailButton,
  HeaderContainer,
  InfoContainer,
  LogoImg,
} from './style';
import { MouseEvent, useEffect, useRef, useState } from 'react';
import { getCoupons } from '../../api/get';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import AdminHeaderLogo from '../../assets/admin_header_logo.png';
import { ROUTER_PATH } from '../../constants';
import { GoPerson } from 'react-icons/go';
import { useNavigate } from 'react-router-dom';
import CouponDetail from './CouponDetail';
import type { CouponRes, PostIsFavoritesReq } from '../../types/api';
import Alert from '../../components/Alert';
import useModal from '../../hooks/useModal';
import { CiCircleMore } from 'react-icons/ci';
import { postIsFavorites } from '../../api/post';
import CafeInfo from './CafeInfo';

const CouponList = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isLast, setIsLast] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [isDetail, setIsDetail] = useState(false);
  const [isFlippedCouponShown, setIsFlippedCouponShown] = useState(false);
  const couponListContainerRef = useRef<HTMLDivElement>(null);

  const queryClient = useQueryClient();

  const {
    data: couponData,
    status: couponStatus,
    refetch: refetchCoupons,
  } = useQuery<CouponRes>(['coupons'], getCoupons, {
    refetchOnWindowFocus: false,
  });

  useEffect(() => {
    if (couponData) {
      setCurrentIndex(couponData?.coupons.length - 1);
    }
  }, [couponData]);

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

  const { coupons } = couponData;
  const currentCoupon = coupons[currentIndex];
  const [currentCouponInfo] = currentCoupon.couponInfos;

  const swapCoupon = (e: MouseEvent<HTMLDivElement>) => {
    if (!couponListContainerRef.current || isDetail) return;

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
      isFavorites: !currentCouponInfo.isFavorites,
    });
  };

  return (
    <>
      <HeaderContainer>
        <LogoImg src={AdminHeaderLogo} alt="스탬프 크러쉬 로고" role="link" />
        <GoPerson size={24} onClick={navigateMyPage} aria-label="마이 페이지" role="button" />
      </HeaderContainer>
      {coupons.length === 0 ? (
        <InfoContainer>보유하고 있는 쿠폰이 없습니다.</InfoContainer>
      ) : (
        <>
          <CafeInfo
            cafeName={currentCoupon.cafeInfo.name}
            stampCount={currentCouponInfo.stampCount}
            maxStampCount={currentCouponInfo.maxStampCount}
            isFavorites={currentCouponInfo.isFavorites}
            frontImageUrl={currentCouponInfo.frontImageUrl}
            onClickStar={openAlert}
          />
          <CouponListContainer
            ref={couponListContainerRef}
            $isLast={isLast}
            $isDetail={isDetail}
            $isShown={isFlippedCouponShown}
            onClick={swapCoupon}
          >
            {coupons.map(({ cafeInfo, couponInfos }, index) => (
              <Coupon
                key={cafeInfo.id}
                coupon={{ cafeInfo, couponInfos }}
                data-index={index}
                onClick={changeCurrentIndex(index)}
                aria-label={`${cafeInfo.name} 쿠폰`}
                isFocused={currentIndex === index}
              />
            ))}
          </CouponListContainer>
          <CouponDetail
            coupon={currentCoupon}
            isDetail={isDetail}
            isShown={isFlippedCouponShown}
            refetchCoupons={refetchCoupons}
            closeDetail={closeCouponDetail}
          />
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
      )}
    </>
  );
};

export default CouponList;
