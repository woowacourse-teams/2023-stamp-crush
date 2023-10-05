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
      <p>현재 쿠폰 이미지</p>
      <label>앞면</label>
      <CouponImageFrame>
        <Image src={couponDesignData?.frontImageUrl} />
      </CouponImageFrame>
      <label>뒷면</label>
      <CouponImageFrame>
        <Image src={couponDesignData?.backImageUrl} />
      </CouponImageFrame>
      <label>스탬프</label>
      <StampImageFrame>
        <Image src={couponDesignData?.stampImageUrl} />
      </StampImageFrame>
    </CurrentCouponContainer>
  );
};

export default CurrentCouponImages;
