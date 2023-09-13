import { TEMPLATE_MENU } from '../../../../constants';
import { SampleCouponRes } from '../../../../types/api/response';
import { SampleImage, SampleBackCouponImage } from '../../../../types/domain/coupon';
import { TemplateMenu } from '../../../../types/utils';
import { useSampleImages } from './hooks/useSampleImages';
import { WarnMsg, SampleImageContainer, SampleImg, WarnMsgBox } from './style';

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
  const { data, status } = useSampleImages();

  if (status === 'loading') return <div>페이지 로딩중..</div>;
  if (status === 'error') return <div> 이미지를 불러오는데 실패했습니다. 새로고침 해주세요. </div>;

  const sampleImages = getImagesFromData(data, templateSelect);

  if (!sampleImages.length) {
    return (
      <WarnMsgBox>
        <WarnMsg>템플릿이 존재하지 않아요:(</WarnMsg>
        <WarnMsg>빠른 시일 내 준비하겠습니다.</WarnMsg>
      </WarnMsgBox>
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
