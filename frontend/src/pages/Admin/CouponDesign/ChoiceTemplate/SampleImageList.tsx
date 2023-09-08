import { TEMPLATE_MENU } from '../../../../constants';
import { SampleBackCouponImage, SampleImage, TemplateMenu } from '../../../../types';
import { SampleCouponRes } from '../../../../types/api';
import { useSampleImages } from './hooks/useSampleImages';
import { SampleImageContainer, SampleImg } from './style';

interface SampleImageListProps {
  templateSelect: TemplateMenu; // TODO: 타입 구체화 하기
  selectedImageUrl: string;
  clickSampleImage: (image: SampleImage | SampleBackCouponImage) => void;
}

// TODO: 네이밍 변경
const getImageFromData = (
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
  const { data: sampleImages, status } = useSampleImages();

  if (status === 'loading') return <div>페이지 로딩중..</div>;
  if (status === 'error') return <div> 이미지를 불러오는데 실패했습니다. 새로고침 해주세요. </div>;

  return (
    <SampleImageContainer>
      {getImageFromData(sampleImages, templateSelect).map((element) => (
        <SampleImg
          key={element.id}
          src={element.imageUrl}
          $templateType={templateSelect}
          $isSelected={selectedImageUrl === element.imageUrl}
          onClick={() => clickSampleImage(element)}
        />
      ))}
    </SampleImageContainer>
  );
};

export default SampleImageList;
