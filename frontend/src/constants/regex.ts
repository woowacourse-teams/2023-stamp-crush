const REGEX = {
  number: /^[0-9]+$/,
  id: /^[a-zA-Z0-9]+$/,
  password: /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d!@#$%^*+=-]{7,30}$/,
  phoneNumber: /[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/,
} as const;

export default REGEX;
