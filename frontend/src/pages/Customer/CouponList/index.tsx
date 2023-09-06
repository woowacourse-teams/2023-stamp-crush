import Coupon from './Coupon';
import { CouponListContainer, InfoContainer } from './style';
import { TouchEvent, useEffect, useRef, useState } from 'react';
import { getCoupons } from '../../../api/get';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { ROUTER_PATH } from '../../../constants';
import { useNavigate } from 'react-router-dom';
import CouponDetail from './CouponDetail';
import type { CouponRes } from '../../../types/api';
import Alert from '../../../components/Alert';
import useModal from '../../../hooks/useModal';
import { postIsFavorites } from '../../../api/post';
import CafeInfo from './CafeInfo';
import Header from './Header';
import { useCustomerProfile } from '../../../hooks/useCustomerProfile';
import CustomerLoadingSpinner from '../../../components/LoadingSpinner/CustomerLoadingSpinner';

const CouponList = () => {
  const navigate = useNavigate();
  const { customerProfile } = useCustomerProfile();
  const { isOpen, openModal, closeModal } = useModal();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [isLast, setIsLast] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [isDetail, setIsDetail] = useState(false);
  const [isFlippedCouponShown, setIsFlippedCouponShown] = useState(false);
  const couponListContainerRef = useRef<HTMLDivElement>(null);
  const [startY, setStartY] = useState(0);
  const [endY, setEndY] = useState(0);

  const queryClient = useQueryClient();

  const {
    data: couponData,
    status: couponStatus,
    refetch: refetchCoupons,
  } = useQuery<CouponRes>(['coupons'], getCoupons);

  useEffect(() => {
    if (localStorage.getItem('login-token') === '' || !localStorage.getItem('login-token'))
      navigate(ROUTER_PATH.login);
    if (!customerProfile?.profile.phoneNumber) navigate(ROUTER_PATH.phoneNumber);
    if (couponData) {
      setCurrentIndex(couponData?.coupons.length - 1);
    }
  }, [couponData, customerProfile?.profile.phoneNumber]);

  const { mutate: mutateIsFavorites } = useMutation(postIsFavorites, {
    onSuccess: () => {
      closeModal();
    },
    onMutate: async () => {
      await queryClient.cancelQueries(['coupons']);
      queryClient.setQueryData<CouponRes>(['coupons'], (prev) => {
        if (!prev) return;
        prev.coupons[currentIndex].cafeInfo.isFavorites =
          !prev.coupons[currentIndex].cafeInfo.isFavorites;
        return undefined;
      });
    },
  });

  if (couponStatus === 'error') return <>에러가 발생했습니다.</>;
  if (couponStatus === 'loading') return <CustomerLoadingSpinner />;

  const { coupons } = couponData;

  if (coupons.length === 0)
    return (
      <>
        <Header />
        <InfoContainer>보유하고 있는 쿠폰이 없습니다.</InfoContainer>
      </>
    );

  const currentCoupon = coupons[currentIndex];
  const [currentCouponInfo] = currentCoupon.couponInfos;

  const swapCoupon = (e: TouchEvent<HTMLDivElement>) => {
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
    if (deltaY > 100 && deltaY < 250) {
      console.log(startY, 'startY', endY, 'endY');
      const dataIndex = e.target instanceof HTMLButtonElement ? e.target.dataset.index : undefined;
      if (dataIndex !== undefined) {
        changeCurrentIndex(Number(dataIndex));
        setStartY(0);
        setEndY(0);
        swapCoupon(e);
      }
    }
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

    currentCoupon.cafeInfo.isFavorites
      ? setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에서 해제하시겠어요?`)
      : setAlertMessage(`${currentCoupon.cafeInfo.name}를\n 즐겨찾기에 등록하시겠어요?`);
  };

  const changeFavorites = () => {
    mutateIsFavorites({
      params: {
        cafeId: currentCoupon.cafeInfo.id,
      },
      body: {
        isFavorites: !currentCoupon.cafeInfo.isFavorites,
      },
    });
  };

  return (
    <>
      <Header />
      {coupons.length === 0 ? (
        <InfoContainer>보유하고 있는 쿠폰이 없습니다.</InfoContainer>
      ) : (
        <>
          <CafeInfo
            cafeName={currentCoupon.cafeInfo.name}
            stampCount={currentCouponInfo.stampCount}
            maxStampCount={currentCouponInfo.maxStampCount}
            isFavorites={currentCoupon.cafeInfo.isFavorites}
            frontImageUrl={currentCouponInfo.frontImageUrl}
            onClickStar={openAlert}
          />
          <CouponListContainer
            ref={couponListContainerRef}
            $isLast={isLast}
            $isDetail={isDetail}
            $isShown={isFlippedCouponShown}
            onTouchStart={onTouchStart}
            onTouchMove={onTouchMove}
            onTouchEnd={onTouchEnd}
          >
            {coupons.map(({ cafeInfo, couponInfos }, index) => (
              <Coupon
                key={cafeInfo.id}
                coupon={{ cafeInfo, couponInfos }}
                dataIndex={index}
                onClick={openCouponDetail}
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
