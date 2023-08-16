import { Dispatch, SetStateAction, useRef, useState } from 'react';
import { ChoiceTemplateContainer, SampleImg, SampleImageContainer } from './style';
import TabBar from '../../../../components/TabBar';
import { TEMPLATE_MENU, TEMPLATE_OPTIONS } from '../../../../constants';
import { useLocation } from 'react-router-dom';
import { getCouponSamples } from '../../../../api/get';
import { parseStampCount } from '../../../../utils';
import { SampleBackCouponImage, SampleImage, StampCoordinate } from '../../../../types';
import useSuspendedQuery from '../../../../hooks/api/useSuspendedQuery';

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
  const location = useLocation();
  const [templateSelect, setTemplateSelect] = useState(TEMPLATE_MENU.FRONT_IMAGE);
  const [selectedImage, setSelectedImage] = useState(frontImageUrl);
  const imageRef = useRef<HTMLImageElement>(null);
  const maxStampCount = parseStampCount(location.state.stampCount);

  const { data: sampleImages } = useSuspendedQuery({
    queryKey: ['coupon-samples'],
    queryFn: () => getCouponSamples({ params: { maxStampCount } }),
  });

  // TODO: 네이밍 변경
  const getImageFromData = (templateSelected: string): SampleImage[] | SampleBackCouponImage[] => {
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

  const selectTabBar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTemplateSelect(e.target.value);
    switch (e.target.value) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setSelectedImage(frontImageUrl);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        setSelectedImage(backImageUrl);
        break;
      case TEMPLATE_MENU.STAMP:
        setSelectedImage(stampImageUrl);
        break;
      default:
        break;
    }
  };

  const selectSampleImage = (coordinates?: StampCoordinate[]) => {
    if (!imageRef.current) return;
    switch (templateSelect) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setFrontImageUrl(imageRef.current.src);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        if (!coordinates) return;
        setBackImageUrl(imageRef.current.src);
        setStampCoordinates([...coordinates]);
        break;
      case TEMPLATE_MENU.STAMP:
        setStampImageUrl(imageRef.current.src);
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
        {getImageFromData(templateSelect).map((element) => (
          <SampleImg
            key={element.id}
            src={element.imageUrl}
            $templateType={templateSelect}
            $isSelected={selectedImage === element.imageUrl}
            ref={imageRef}
            onClick={() => {
              if ('stampCoordinates' in element) {
                selectSampleImage(element.stampCoordinates);
              } else {
                selectSampleImage();
              }
            }}
          />
        ))}
      </SampleImageContainer>
    </ChoiceTemplateContainer>
  );
};

export default ChoiceTemplate;
