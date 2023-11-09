import { useLocation, useNavigate } from 'react-router-dom';
import Alert from '../../../../../components/Alert';
import LoadingSpinner from '../../../../../components/LoadingSpinner';
import ROUTER_PATH from '../../../../../constants/routerPath';
import { BackDrop } from './style';

interface GuideAlertProps {
  isOpen: boolean;
  phoneNumber: string;
  customerStatus: string;
  postTemporaryCustomerStatus: string;
  enterStampHandler: () => void;
  enterRewardHandler: () => void;
  retryEnter: () => void;
}

const GuideAlert = ({
  isOpen,
  phoneNumber,
  customerStatus,
  postTemporaryCustomerStatus,
  enterStampHandler,
  enterRewardHandler,
  retryEnter,
}: GuideAlertProps) => {
  const location = useLocation();
  const navigate = useNavigate();
  const exitPage = () => {
    navigate(ROUTER_PATH.customerList);
  };

  if (customerStatus === 'error')
    return (
      <Alert
        text={'전화번호로 회원을 조회하는 과정에서\n 오류가 발생했어요. '}
        rightOption={'홈으로'}
        onClickRight={exitPage}
      />
    );

  if (postTemporaryCustomerStatus === 'loading')
    return (
      <BackDrop>
        <LoadingSpinner />
      </BackDrop>
    );

  if (postTemporaryCustomerStatus === 'error')
    return (
      <Alert
        text={'임시 가입에 오류가 발생했어요. '}
        rightOption={'홈으로'}
        onClickRight={exitPage}
      />
    );

  return (
    isOpen && (
      <>
        {location.pathname === ROUTER_PATH.enterStamp ? (
          <Alert
            text={phoneNumber + '님, 첫 스탬프 적립이 맞으신가요?'}
            rightOption={'네'}
            leftOption={'다시 입력'}
            onClickRight={enterStampHandler}
            onClickLeft={retryEnter}
          />
        ) : (
          <Alert
            text={phoneNumber + '님은 \n스탬프크러쉬 회원이 아니에요 🥲'}
            rightOption={'나가기'}
            leftOption={'다시 입력'}
            onClickRight={enterRewardHandler}
            onClickLeft={retryEnter}
          />
        )}
      </>
    )
  );
};

export default GuideAlert;
