import { Dispatch, SetStateAction, useState } from 'react';
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
  const [selectedImageUrl, setSelectedImageUrl] = useState('');
  const stampCount = parseStampCount(location.state.stampCount);

  const { data: sampleImages, status } = useQuery<SampleCouponRes>(
    ['coupon-samples', stampCount],
    () => getCouponSamples(stampCount),
    {
      staleTime: Infinity,
    },
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
        setSelectedImageUrl(frontImageUrl);
        break;
      case TEMPLATE_MENU.BACK_IMAGE:
        setSelectedImageUrl(backImageUrl);
        break;
      case TEMPLATE_MENU.STAMP:
        setSelectedImageUrl(stampImageUrl);
        break;
      default:
        break;
    }
  };

  const selectSampleImageUrl = (imageUrl: string, coordinates?: StampCoordinate[]) => {
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
        break;
    }
    setSelectedImageUrl(imageUrl);
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
            $isSelected={selectedImageUrl === element.imageUrl}
            onClick={() => {
              if ('stampCoordinates' in element) {
                selectSampleImageUrl(element.imageUrl, element.stampCoordinates);
              } else {
                selectSampleImageUrl(element.imageUrl);
              }
            }}
          />
        ))}
      </SampleImageContainer>
    </ChoiceTemplateContainer>
  );
};

export default ChoiceTemplate;
