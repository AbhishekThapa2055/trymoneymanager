export const validateEmail = (email) => {
  if (email.trim()) {
    const regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    return regex.test(email);
  }
  return false;
};
