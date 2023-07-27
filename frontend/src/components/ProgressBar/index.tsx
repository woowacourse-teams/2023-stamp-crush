import { Bar, Progress } from './style';

interface Props {
  stampCount: number;
  maxCount: number;
}

const ProgressBar = ({ stampCount, maxCount }: Props) => {
  return (
    <Bar>
      <Progress $width={(stampCount / maxCount) * 100} />
    </Bar>
  );
};

export default ProgressBar;
