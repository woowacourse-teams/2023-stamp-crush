import { PreviewImage } from '../../../CouponDesign/CustomCouponDesign/style';
import defaultCafeImg from '../../../../../assets/default_cafe_bg.png';

interface PreviewCafeImgProps {
  cafeImage: string;
}

const PreviewCafeImage = ({ cafeImage }: PreviewCafeImgProps) => {
  return (
    <PreviewImage
      src={!cafeImage ? defaultCafeImg : cafeImage}
      $width={312}
      $height={594}
      $opacity={0.25}
    />
  );
};

export default PreviewCafeImage;
