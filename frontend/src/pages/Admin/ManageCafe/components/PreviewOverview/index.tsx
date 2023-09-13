import { PreviewOverviewContainer } from '../../style';
import Text from '../../../../../components/Text';

interface PreviewOverviewProps {
  cafeName: string;
  introduction: string;
}

const PreviewOverview = ({ cafeName, introduction }: PreviewOverviewProps) => {
  return (
    <PreviewOverviewContainer>
      <Text variant="subTitle">{cafeName}</Text>
      <Text>{introduction}</Text>
    </PreviewOverviewContainer>
  );
};

export default PreviewOverview;
