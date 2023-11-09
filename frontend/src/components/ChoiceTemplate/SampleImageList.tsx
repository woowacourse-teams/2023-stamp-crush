import { TEMPLATE_MENU } from '.';
import { SampleCouponRes } from '../../types/api/response';
import { SampleImage, SampleBackCouponImage } from '../../types/domain/coupon';
import { TemplateMenu } from '../../types/utils';
import Button from '../Button';
import { useSampleImages } from './hooks/useSampleImages';
import { WarnMsg, SampleImageContainer, SampleImg, WarnMsgBox } from './style';
import CouponLoadImg from '../../assets/coupon_loading_img.png';
import StampLoadImg from '../../assets/stamp_load_img.png';

interface SampleImageListProps {
  templateSelect: TemplateMenu;
  selectedImageUrl: string;
  clickSampleImage: (image: SampleImage | SampleBackCouponImage) => () => void;
}

const getImagesFromData = (
  sampleImages: SampleCouponRes,
  templateSelected: TemplateMenu,
): SampleImage[] | SampleBackCouponImage[] => {
  switch (templateSelected) {
    case TEMPLATE_MENU.FRONT_IMAGE:
      return sampleImages.sampleFrontImages;
    case TEMPLATE_MENU.BACK_IMAGE:
      return sampleImages.sampleBackImages;
    case TEMPLATE_MENU.STAMP:
      return sampleImages.sampleStampImages;
    default:
      return [];
  }
};

const SampleImageList = ({
  templateSelect,
  selectedImageUrl,
  clickSampleImage,
}: SampleImageListProps) => {
  const { data, status, refetch } = useSampleImages();

  const refetchSampleImages = () => {
    refetch();
  };

  if (status === 'loading')
    return (
      <SampleImageContainer>
        {Array.from({ length: 5 }, (v, i) => i).map((element) => {
          return (
            <SampleImg
              key={element}
              src={templateSelect === '스탬프' ? StampLoadImg : CouponLoadImg}
              $templateType={templateSelect}
              $isSelected={false}
            />
          );
        })}
      </SampleImageContainer>
    );
  if (status === 'error')
    return (
      <SampleImageContainer>
        <div>샘플 이미지를 불러오는데 실패했습니다.</div>
        <div>아래 버튼을 눌러 새로고침 해주세요.</div>
        <Button onClick={refetchSampleImages}>새로고침</Button>
      </SampleImageContainer>
    );

  const sampleImages = getImagesFromData(data, templateSelect);

  if (!sampleImages.length) {
    return (
      <SampleImageContainer>
        <WarnMsgBox>
          <WarnMsg>템플릿이 존재하지 않아요:(</WarnMsg>
          <WarnMsg>빠른 시일 내 준비하겠습니다.</WarnMsg>
        </WarnMsgBox>
      </SampleImageContainer>
    );
  }

  return (
    <SampleImageContainer>
      {sampleImages.map((element) => (
        <SampleImg
          key={element.id}
          src={element.imageUrl}
          $templateType={templateSelect}
          $isSelected={selectedImageUrl === element.imageUrl}
          onClick={clickSampleImage(element)}
        />
      ))}
    </SampleImageContainer>
  );
};

export default SampleImageList;
