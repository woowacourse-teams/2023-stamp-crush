import { Dispatch, SetStateAction, useRef, useState } from 'react';
import { ChoiceTemplateContainer, SampleImage, SampleImageContainer } from './ChoiceTemplate.style';
import TabBar from '../../../components/TabBar';
import DUMMY from './dummy';
import { TEMPLATE_MENU } from '../../../constants';

const TEMPLATE_OPTIONS = [
  {
    key: 'coupon-front',
    value: TEMPLATE_MENU.FRONT_IMAGE,
  },
  {
    key: 'coupon-back',
    value: TEMPLATE_MENU.BACK_IMAGE,
  },
  {
    key: 'stamp',
    value: TEMPLATE_MENU.STAMP,
  },
];

// TODO: 네이밍 변경
const getImageFromData = (templateSelected: string) => {
  switch (templateSelected) {
    case TEMPLATE_MENU.FRONT_IMAGE:
      return DUMMY.sampleFrontImages;
    case TEMPLATE_MENU.BACK_IMAGE:
      return DUMMY.sampleBackImages;
    case TEMPLATE_MENU.STAMP:
      return DUMMY.sampleStampImages;
    default:
      break;
  }
};

interface ChoiceTemplateProps {
  frontImage: string;
  backImage: string;
  stampImage: string;
  setFrontImage: Dispatch<SetStateAction<string>>;
  setBackImage: Dispatch<SetStateAction<string>>;
  setStampImage: Dispatch<SetStateAction<string>>;
}

const ChoiceTemplate = ({
  frontImage,
  backImage,
  stampImage,
  setFrontImage,
  setBackImage,
  setStampImage,
}: ChoiceTemplateProps) => {
  const [templateSelect, setTemplateSelect] = useState(TEMPLATE_MENU.FRONT_IMAGE);
  const [selectedImage, setSelectedImage] = useState(frontImage);
  const imageRef = useRef<HTMLImageElement>(null);

  const selectTabBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTemplateSelect(e.target.value);
    switch (e.target.value) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setSelectedImage(frontImage);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        setSelectedImage(backImage);
        break;
      case TEMPLATE_MENU.STAMP:
        setSelectedImage(stampImage);
        break;
      default:
        break;
    }
  };

  const selectSampleImage = () => {
    if (!imageRef.current) return;
    console.log(imageRef.current.src);
    switch (templateSelect) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setFrontImage(imageRef.current.src);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        setBackImage(imageRef.current.src);
        break;
      case TEMPLATE_MENU.STAMP:
        setStampImage(imageRef.current.src);
        break;
      default:
        break;
    }
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
      <SampleImageContainer>
        {getImageFromData(templateSelect)?.map((element) => (
          <SampleImage
            key={element.id}
            src={element.imageUrl}
            $templateType={templateSelect}
            $isSelected={selectedImage === element.imageUrl}
            ref={imageRef}
            onClick={selectSampleImage}
          />
        ))}
      </SampleImageContainer>
    </ChoiceTemplateContainer>
  );
};

export default ChoiceTemplate;
