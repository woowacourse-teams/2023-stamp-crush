import { Dispatch, SetStateAction, useState } from 'react';
import { ChoiceTemplateContainer } from './style';
import TabBar from '../../../../components/TabBar';
import { TEMPLATE_MENU, TEMPLATE_OPTIONS } from '../../../../constants';
import { SampleBackCouponImage, SampleImage, StampCoordinate } from '../../../../types';
import SampleImageList from './SampleImageList';

interface ChoiceTemplateProps {
  frontImageUrl: string;
  backImageUrl: string;
  stampImageUrl: string;
  setFrontImageUrl: Dispatch<SetStateAction<string>>;
  setBackImageUrl: Dispatch<SetStateAction<string>>;
  setStampImageUrl: Dispatch<SetStateAction<string>>;
  setStampCoordinates: Dispatch<SetStateAction<StampCoordinate[]>>;
}

const ChoiceTemplate = ({
  frontImageUrl,
  backImageUrl,
  stampImageUrl,
  setFrontImageUrl,
  setBackImageUrl,
  setStampImageUrl,
  setStampCoordinates,
}: ChoiceTemplateProps) => {
  const [templateSelect, setTemplateSelect] = useState(TEMPLATE_MENU.FRONT_IMAGE);
  const [selectedImageUrl, setSelectedImageUrl] = useState('');

  const selectTabBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    switch (e.target.value) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setSelectedImageUrl(frontImageUrl);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        setSelectedImageUrl(backImageUrl);
        break;
      case TEMPLATE_MENU.STAMP:
        setSelectedImageUrl(stampImageUrl);
        break;
      default:
        throw new Error('유효하지 않은 템플릿 입니다.');
    }
    setTemplateSelect(e.target.value);
  };

  const selectSampleImage = (imageUrl: string, coordinates?: StampCoordinate[]) => {
    switch (templateSelect) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setFrontImageUrl(imageUrl);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        if (!coordinates) return;
        setBackImageUrl(imageUrl);
        setStampCoordinates([...coordinates]);
        break;
      case TEMPLATE_MENU.STAMP:
        setStampImageUrl(imageUrl);
        break;
      default:
        throw new Error('유효하지 않은 템플릿 입니다.');
    }
    setSelectedImageUrl(imageUrl);
  };

  const clickSampleImage = (image: SampleImage | SampleBackCouponImage) => {
    if ('stampCoordinates' in image) {
      selectSampleImage(image.imageUrl, image.stampCoordinates);
      return;
    }
      selectSampleImage(image.imageUrl);
  };

  return (
    <ChoiceTemplateContainer>
      <TabBar
        name="template-type"
        options={TEMPLATE_OPTIONS}
        selectedValue={templateSelect}
        onChange={selectTabBar}
        height={54}
        width={350}
      />
      <SampleImageList
        templateSelect={templateSelect}
        selectedImageUrl={selectedImageUrl}
        clickSampleImage={clickSampleImage}
      />
    </ChoiceTemplateContainer>
  );
};

export default ChoiceTemplate;
