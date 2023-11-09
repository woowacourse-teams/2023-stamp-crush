import { Bar, Progress } from './style';

interface ProgressBarProps {
  stampCount: number;
  maxStampCount: number;
  color?: string;
  barWidth?: string;
}

const ProgressBar = ({
  stampCount,
  maxStampCount,
  color = 'gray',
  barWidth = '100%',
}: ProgressBarProps) => {
  return (
    <Bar $barWidth={barWidth}>
      <Progress $width={(stampCount / maxStampCount) * 100} $color={color} />
    </Bar>
  );
};

export default ProgressBar;
