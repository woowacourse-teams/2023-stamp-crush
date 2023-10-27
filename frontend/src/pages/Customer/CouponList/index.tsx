import Coupon from './components/Coupon';
import { CouponListContainer, ToggleContainer } from './style';
import { useState } from 'react';
import CouponDetail from './components/CouponDetail';
import Alert from '../../../components/Alert';
import useModal from '../../../hooks/useModal';
import Header from './components/Header';
import CustomerLoadingSpinner from '../../../components/LoadingSpinner/CustomerLoadingSpinner';
import { isNotEmptyArray } from '../../../utils';
import useGetCoupons from './hooks/useGetCoupons';
import usePostIsFavorites from './hooks/usePostIsFavorites';
import useCouponDetail from './hooks/useCouponDetail';
import HomeTemplate from './components/HomeTemplate';
import ToggleButton from '../../../components/ToggleButton';
import useToggleButton from '../../../hooks/useToggleButton';

const CouponList = () => {
  const [alertMessage, setAlertMessage] = useState('');
  const [currentIndex, setCurrentIndex] = useState(0);
  const { isOn: isExpanded, toggle: expandCoupons } = useToggleButton();
  const { isOn: isCollected, toggle: collectFavorites } = useToggleButton();
  const { isOpen, openModal, closeModal } = useModal();
  const { isDetail, isFlippedCouponShown, openCouponDetail, closeCouponDetail } = useCouponDetail();
  const { data: coupons, status: couponStatus } = useGetCoupons(isCollected);
  const { mutate: mutateIsFavorites } = usePostIsFavorites(closeModal, currentIndex);

  const changeCurrentIndex = (index: number) => () => {
    setCurrentIndex(index);
    openCouponDetail();
  };

  if (couponStatus === 'error') {
    return <HomeTemplate>에러가 발생했습니다.</HomeTemplate>;
  }
  if (couponStatus === 'loading')
    return (
      <HomeTemplate>
        <CustomerLoadingSpinner />
      </HomeTemplate>
    );

  if (!isNotEmptyArray(coupons)) return <HomeTemplate>보유하고 있는 쿠폰이 없습니다.</HomeTemplate>;

  const openAlert = (index: number) => () => {
    setCurrentIndex(index);
    coupons[index].cafeInfo.isFavorites
      ? setAlertMessage(`${coupons[index].cafeInfo.name}를\n 즐겨찾기에서 해제하시겠어요?`)
      : setAlertMessage(`${coupons[index].cafeInfo.name}를\n 즐겨찾기에 등록하시겠어요?`);

    openModal();
  };

  const changeFavorites = () => {
    mutateIsFavorites({
      params: {
        cafeId: coupons[currentIndex].cafeInfo.id,
      },
      body: {
        isFavorites: !coupons[currentIndex].cafeInfo.isFavorites,
      },
    });
  };

  return (
    <>
      <Header />
      <ToggleContainer $isOn={isExpanded}>
        <span>즐겨찾기 모아보기</span>
        <ToggleButton isOn={isCollected} toggle={collectFavorites} />
        <span>쿠폰 펼치기</span>
        <ToggleButton isOn={isExpanded} toggle={expandCoupons} disabled={coupons.length === 1} />
      </ToggleContainer>
      <CouponListContainer $isOn={!isExpanded}>
        {coupons.map(({ cafeInfo, couponInfos }, index) => (
          <Coupon
            key={cafeInfo.id}
            coupon={{ cafeInfo, couponInfos }}
            dataIndex={index}
            isOn={!isExpanded}
            index={index}
            onClick={changeCurrentIndex(index)}
            onClickStar={openAlert(index)}
          />
        ))}
      </CouponListContainer>
      <CouponDetail
        coupon={coupons[currentIndex]}
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
  );
};

export default CouponList;
