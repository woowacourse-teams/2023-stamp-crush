import useGetCouponDesign from '../../../ManageCafe/hooks/useGetCouponDesign';
import { CouponImageFrame, CurrentCouponContainer, Image, StampImageFrame } from './style';
import { useRedirectRegisterPage } from '../../../../../hooks/useRedirectRegisterPage';
import CouponLoadImg from '../../../../../assets/coupon_loading_img.png';
import StampLoadImg from '../../../../../assets/stamp_load_img.png';
import { UseQueryResult } from '@tanstack/react-query';
import { CouponDesign } from '../../../../../types/domain/coupon';

type ImgType = 'front' | 'back' | 'stamp';

const getImgURLByStatus = (query: UseQueryResult<CouponDesign, unknown>, imgType: ImgType) => {
  if (query.status === 'loading') {
    return imgType === 'stamp' ? StampLoadImg : CouponLoadImg;
  }

  if (query.status === 'error') {
    throw new Error('요청에 실패하였습니다.');
  }

  switch (imgType) {
    case 'front':
      return query.data.frontImageUrl;
    case 'back':
      return query.data.backImageUrl;
    case 'stamp':
      return query.data.stampImageUrl;
    default:
      throw new Error('잘못된 이미지 타입을 매개변수로 전달했습니다.');
  }
};

const CurrentCouponImages = () => {
  const cafeId = useRedirectRegisterPage();
  const couponDesign = useGetCouponDesign(cafeId);

  if (couponDesign.status === 'error') return <div>Error</div>;

  return (
    <CurrentCouponContainer>
      <h1>현재 쿠폰 이미지</h1>
      <span>앞면</span>
      <CouponImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'front')} />
      </CouponImageFrame>
      <span>뒷면</span>
      <CouponImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'back')} />
      </CouponImageFrame>
      <span>스탬프</span>
      <StampImageFrame>
        <Image src={getImgURLByStatus(couponDesign, 'stamp')} />
      </StampImageFrame>
    </CurrentCouponContainer>
  );
};

export default CurrentCouponImages;
