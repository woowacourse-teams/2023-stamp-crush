import { Dispatch, SetStateAction, useRef, useState } from 'react';
import { ChoiceTemplateContainer, SampleImg, SampleImageContainer } from './style';
import TabBar from '../../../../components/TabBar';
import { TEMPLATE_MENU, TEMPLATE_OPTIONS } from '../../../../constants';
import { useQuery } from '@tanstack/react-query';
import { useLocation } from 'react-router-dom';
import { getCouponSamples } from '../../../../api/get';
import { parseStampCount } from '../../../../utils';
import { SampleBackCouponImage, SampleImage, StampCoordinate } from '../../../../types';
import { SampleCouponRes } from '../../../../types/api';

interface ChoiceTemplateProps {
  frontImage: string;
  backImage: string;
  stampImage: string;
  setFrontImage: Dispatch<SetStateAction<string>>;
  setBackImage: Dispatch<SetStateAction<string>>;
  setStampImage: Dispatch<SetStateAction<string>>;
  setStampCoordinates: Dispatch<SetStateAction<StampCoordinate[]>>;
}

const ChoiceTemplate = ({
  frontImage,
  backImage,
  stampImage,
  setFrontImage,
  setBackImage,
  setStampImage,
  setStampCoordinates,
}: ChoiceTemplateProps) => {
  const location = useLocation();
  const [templateSelect, setTemplateSelect] = useState(TEMPLATE_MENU.FRONT_IMAGE);
  const [selectedImage, setSelectedImage] = useState(frontImage);
  const imageRef = useRef<HTMLImageElement>(null);
  const stampCount = parseStampCount(location.state.stampCount);

  const { data: sampleImages, status } = useQuery<SampleCouponRes>(
    ['coupon-samples', stampCount],
    () => getCouponSamples(stampCount),
  );

  if (status === 'loading') return <div>페이지 로딩중..</div>;
  if (status === 'error') return <div> 이미지를 불러오는데 실패했습니다. 새로고침 해주세요. </div>;

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

  const selectSampleImage = (coordinates?: StampCoordinate[]) => {
    if (!imageRef.current) return;
    switch (templateSelect) {
      case TEMPLATE_MENU.FRONT_IMAGE:
        setFrontImage(imageRef.current.src);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        if (!coordinates) return;
        setBackImage(imageRef.current.src);
        setStampCoordinates([...coordinates]);
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