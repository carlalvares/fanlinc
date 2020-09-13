import { useState } from 'react';

/**
 *
 * @function submit handles form submission
 * @param {Object} initialState initial form states and values
 */
function useForm(submit, initialState) {
  const [values, setValues] = useState(initialState);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    submit();
  };

  return { values, handleChange, handleSubmit };
}

export default useForm;
