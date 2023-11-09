import ButtonLoadingSpinner from '../../../../../assets/button_loading_spinner.svg';
import { LoadingSpinnerContainer } from './style';

const LoadingSpinner = () => {
  return (
    <LoadingSpinnerContainer>
      <ButtonLoadingSpinner width={24} height={24} />
    </LoadingSpinnerContainer>
  );
};

export default LoadingSpinner;
