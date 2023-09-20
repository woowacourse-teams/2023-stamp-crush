declare module '*.png';
declare module '*.ttf';
declare module '*.gif';
declare module '*.jpg';

declare module '*.svg' {
  import React = require('react');
  const SVG: React.VFC<React.SVGProps<SVGSVGElement>>;
  export default SVG;
}
