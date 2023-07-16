import { Dispatch, SetStateAction, useRef, useState } from 'react';
import { ChoiceTemplateContainer, SampleImage, SampleImageContainer } from './ChoiceTemplate.style';
import TabBar from '../../../components/TabBar';
import DUMMY from './dummy';

const TEMPLATE_OPTIONS = [
  {
    key: 'coupon-front',
    value: '쿠폰(앞)',
  },
  {
    key: 'coupon-back',
    value: '쿠폰(뒤)',
  },
  {
    key: 'stamp',
    value: '스탬프',
  },
];

// TODO: 네이밍 변경
const getImageFromData = (templateSelected: string) => {
  switch (templateSelected) {
    case '쿠폰(앞)':
      return DUMMY.sampleFrontImages;
    case '쿠폰(뒤)':
      return DUMMY.sampleBackImages;
    case '스탬프':
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
  const [templateSelect, setTemplateSelect] = useState('쿠폰(앞)');
  const [selectedImage, setSelectedImage] = useState(frontImage);
  const imageRef = useRef<HTMLImageElement>(null);

  const selectTabBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTemplateSelect(e.target.value);
    switch (e.target.value) {
      case '쿠폰(앞)':
        setSelectedImage(frontImage);
        break;
      case '쿠폰(뒤)':
        setSelectedImage(backImage);
        break;
      case '스탬프':
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
      case '쿠폰(앞)':
        setFrontImage(imageRef.current.src);
        break;
      case '쿠폰(뒤)':
        setBackImage(imageRef.current.src);
        break;
      case '스탬프':
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
