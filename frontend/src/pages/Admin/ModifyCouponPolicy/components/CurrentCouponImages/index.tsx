import useGetCouponDesign from '../../../ManageCafe/hooks/useGetCouponDesign';
import { CouponImageFrame, CurrentCouponContainer, Image, StampImageFrame } from './style';
import LoadingSpinner from '../../../../../components/LoadingSpinner';
import { useRedirectRegisterPage } from '../../../../../hooks/useRedirectRegisterPage';

const CurrentCouponImages = () => {
  const cafeId = useRedirectRegisterPage();
  const { data: couponDesignData, status } = useGetCouponDesign(cafeId);

  if (status === 'error') return <div>Error</div>;
  if (status === 'loading') return <LoadingSpinner />;

  return (
    <CurrentCouponContainer>
      <h1>현재 쿠폰 이미지</h1>
      <span>앞면</span>
      <CouponImageFrame>
        <Image src={couponDesignData?.frontImageUrl} />
      </CouponImageFrame>
      <span>뒷면</span>
      <CouponImageFrame>
        <Image src={couponDesignData?.backImageUrl} />
      </CouponImageFrame>
      <span>스탬프</span>
      <StampImageFrame>
        <Image src={couponDesignData?.stampImageUrl} />
      </StampImageFrame>
    </CurrentCouponContainer>
  );
};

export default CurrentCouponImages;
