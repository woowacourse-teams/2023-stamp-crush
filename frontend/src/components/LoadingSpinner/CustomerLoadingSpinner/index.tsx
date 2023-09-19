import loadingSpinner from '../../../assets/c_loading.svg';
import { CustomerLoadingContainer } from '../style';

const CustomerLoadingSpinner = () => {
  return (
    <CustomerLoadingContainer>
      <img src={loadingSpinner} />
    </CustomerLoadingContainer>
  );
};

export default CustomerLoadingSpinner;
