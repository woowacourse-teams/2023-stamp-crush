import Coupon from './components/Coupon';
import { CouponListContainer, InfoContainer } from './style';
import { useEffect, useRef, useState } from 'react';
import { ROUTER_PATH } from '../../../constants';
import { useNavigate } from 'react-router-dom';
import CouponDetail from './components/CouponDetail';
import Alert from '../../../components/Alert';
import useModal from '../../../hooks/useModal';
import CafeInfo from './components/CafeInfo';
import Header from './components/Header';
import CustomerLoadingSpinner from '../../../components/LoadingSpinner/CustomerLoadingSpinner';
import { isNotEmptyArray } from '../../../utils';
import useGetCoupons from './hooks/useGetCoupons';
import usePostIsFavorites from './hooks/usePostIsFavorites';
import useCouponDetail from './hooks/useCouponDetail';
import useCouponList from './hooks/useCouponList';
import HomeTemplate from './components/HomeTemplate';

const CouponList = () => {
  const navigate = useNavigate();

  const { isOpen, openModal, closeModal } = useModal();
  const [alertMessage, setAlertMessage] = useState('');
  const couponListContainerRef = useRef<HTMLDivElement>(null);

  const { data: coupons, status: couponStatus } = useGetCoupons();

  const { isDetail, isFlippedCouponShown, openCouponDetail, closeCouponDetail } = useCouponDetail();
  const { isLast, currentIndex, setCurrentIndex, onTouchStart, onTouchEnd, onTouchMove } =
    useCouponList(couponListContainerRef, isDetail, coupons!);

  const { mutate: mutateIsFavorites } = usePostIsFavorites(closeModal, currentIndex);

  useEffect(() => {
    if (localStorage.getItem('login-token') === '' || !localStorage.getItem('login-token'))
      navigate(ROUTER_PATH.login);
    if (coupons && isNotEmptyArray(coupons)) {
      setCurrentIndex(coupons.length - 1);
    }
  }, [coupons]);

  // const { customerProfile, hasPhoneNumber, redirectCustomerWithoutPhoneNumber } =
  //   useCustomerRedirectRegisterPage();

  // useEffect(() => {
  //   if (!hasPhoneNumber) redirectCustomerWithoutPhoneNumber();
  // }, [customerProfile]);

  if (couponStatus === 'error') {
    return <HomeTemplate>에러가 발생했습니다.</HomeTemplate>;
  }
  if (couponStatus === 'loading')
    return (
      <HomeTemplate>
        <CustomerLoadingSpinner />
      </HomeTemplate>
    );

  if (coupons.length === 0) return <HomeTemplate>보유하고 있는 쿠폰이 없습니다.</HomeTemplate>;

  const currentCoupon = coupons[currentIndex];
  const [currentCouponInfo] = currentCoupon.couponInfos;

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
      {!isNotEmptyArray(coupons) ? (
        <InfoContainer>보유하고 있는 쿠폰이 없습니다.</InfoContainer>
      ) : (
        <>
          <CafeInfo
            cafeInfo={currentCoupon}
            couponInfo={currentCouponInfo}
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
