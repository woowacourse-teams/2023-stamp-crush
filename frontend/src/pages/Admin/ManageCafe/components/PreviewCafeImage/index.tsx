import { PreviewImage } from '../../../CouponDesign/CustomCouponDesign/style';
import defaultCafeImg from '../../../../../assets/default_cafe_bg.png';
import { DefaultCafeImage } from '../../style';

interface PreviewCafeImgProps {
  cafeImageUrl: string;
}

const PreviewCafeImage = ({ cafeImageUrl }: PreviewCafeImgProps) => {
  if (!cafeImageUrl) return <DefaultCafeImage />;

  return (
    <PreviewImage
      src={!cafeImageUrl ? defaultCafeImg : cafeImageUrl}
      $width={312}
      $height={594}
      $opacity={0.25}
    />
  );
};

export default PreviewCafeImage;
