import loadingSpinner from '../../assets/loading_spinner.gif';
import { LoadingContainer } from './style';

const LoadingSpinner = () => {
  return (
    <LoadingContainer>
      <img src={loadingSpinner} alt="로딩 중입니다." />
    </LoadingContainer>
  );
};

export default LoadingSpinner;
