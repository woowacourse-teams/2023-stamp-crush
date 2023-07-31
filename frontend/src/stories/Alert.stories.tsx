import { StoryFn, Meta } from '@storybook/react';
import Alert, { AlertProps } from '../components/Alert';
import useModal from '../hooks/useModal';

export default {
  title: 'Alert',
  component: Alert,
  tags: ['autodocs'],
} as Meta<AlertProps>;

const Template: StoryFn<AlertProps> = (props: AlertProps) => {
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <>
      <button onClick={openModal}>Alert 열기</button>
      {isOpen ? (
        <Alert
          text={props.text}
          rightOption={'확인'}
          leftOption={'취소'}
          onClickRight={closeModal}
          onClickLeft={closeModal}
        />
      ) : (
        <></>
      )}
    </>
  );
};

export const FavoritesAlert = Template.bind({});
FavoritesAlert.args = {
  text: '우아한 카페를 찜하시겠어요?',
};

export const DeletionAlert = Template.bind({});
DeletionAlert.args = {
  text: '우아한 카페의 쿠폰을 삭제하시겠어요?',
};
