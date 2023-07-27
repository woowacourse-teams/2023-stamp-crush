import { Bar, Progress } from './style';

interface Props {
  stampCount: number;
  maxCount: number;
  progressColor?: string;
}

const ProgressBar = ({ stampCount, maxCount, progressColor = 'skyblue' }: Props) => {
  return (
    <Bar>
      <Progress $width={(stampCount / maxCount) * 100} $progressColor={progressColor} />
    </Bar>
  );
};

export default ProgressBar;
