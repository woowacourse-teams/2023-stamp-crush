import { AiOutlineUpload } from 'react-icons/ai';
import {
  ImageUpLoadInput,
  ImageUpLoadInputLabel,
} from '../../../CouponDesign/CustomCouponDesign/style';
import { StepTitle, Wrapper } from '../../style';

interface CafeImageUploadProps {
  uploadImage: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const CafeImageUpload = ({ uploadImage }: CafeImageUploadProps) => {
  return (
    <Wrapper>
      <StepTitle>step1. 내 카페를 대표하는 내부사진을 업로드해주세요.</StepTitle>
      <ImageUpLoadInput
        id="cafe-image"
        type="file"
        accept="image/jpg,image/png,image/jpeg,image/gif"
        onChange={uploadImage}
      />
      <ImageUpLoadInputLabel htmlFor={'cafe-image'}>
        <AiOutlineUpload />
        업로드
      </ImageUpLoadInputLabel>
    </Wrapper>
  );
};

export default CafeImageUpload;
