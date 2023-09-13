import { StepTitle } from '../../style';
import { RestrictionLabel, TextArea } from './style';

interface CafeIntroductionProps {
  introduction: string;
  inputIntroduction: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const CafeIntroduction = ({ introduction, inputIntroduction }: CafeIntroductionProps) => {
  return (
    <>
      <StepTitle>step4. 내 카페를 소개할 간단한 문장을 작성해주세요.</StepTitle>
      <TextArea
        rows={8}
        cols={50}
        onChange={inputIntroduction}
        maxLength={150}
        value={introduction}
      />
      <RestrictionLabel $isExceed={!introduction ? false : introduction.length >= 150}>
        {!introduction ? '0/150' : `${introduction.length}/150`}
      </RestrictionLabel>
    </>
  );
};

export default CafeIntroduction;
