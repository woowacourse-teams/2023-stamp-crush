import { Bar, Progress } from './style';

interface ProgressBarProps {
  stampCount: number;
  maxCount: number;
  color?: string;
}

const ProgressBar = ({ stampCount, maxCount, color = 'gray' }: ProgressBarProps) => {
  return (
    <Bar>
      <Progress $width={(stampCount / maxCount) * 100} $color={color} />
    </Bar>
  );
};

export default ProgressBar;
