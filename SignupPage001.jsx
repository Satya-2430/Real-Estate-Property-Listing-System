import React, { useState } from 'react';
import './Signupcss.css';
 
const SignupPage001 = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    contact: '',
    role: 'Buyer', // Default to Buyer
  });
  const [isLoading, setIsLoading] = useState(false);
 
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
 
  const handleSubmit = (e) => {
    e.preventDefault();
 
    if (!formData.name || !formData.email || !formData.password || !formData.confirmPassword || !formData.contact || !formData.role) {
      alert('Please fill in all fields.');
      return;
    }
 
    if (formData.password !== formData.confirmPassword) {
      alert('Passwords do not match.');
      return;
    }
 
    setIsLoading(true);
    // Simulate an API call
    setTimeout(() => {
      console.log('Form submitted:', formData);
      setIsLoading(false);
      alert('Signup successful!');
    }, 2000);
  };
 
  const roles = ['Buyer', 'Seller', 'Agent'];
 
  return (
    <div className="signup-container">
      <div className="signup-wrapper">
        <div className="signup-form-container" style={{ overflowY: 'auto', maxHeight: 'calc(100vh - 80px)' }}>
          <div className="brand-header">
            <div className="logo-circle">
              <i className="fas fa-user-plus"></i>
            </div>
            <h1>Create an Account</h1>
            <p className="tagline">Sign up to get started on your journey.</p>
          </div>
 
          <form className="signup-form" onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name"><i className="fas fa-user"></i>Name</label>
              <input
                type="text"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="Enter your name"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="email"><i className="fas fa-envelope"></i>Email</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Enter your email address"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="password"><i className="fas fa-lock"></i>Password</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                placeholder="Enter a secure password"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="confirmPassword"><i className="fas fa-lock"></i>Confirm Password</label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                value={formData.confirmPassword}
                onChange={handleChange}
                placeholder="Re-enter your password"
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="contact"><i className="fas fa-phone"></i>Contact</label>
              <input
                type="text"
                id="contact"
                name="contact"
                value={formData.contact}
                onChange={handleChange}
                placeholder="Enter your contact number"
                required
              />
            </div>
            <div className="form-group">
              <label><i className="fas fa-user-tag"></i>Role</label>
              <div className="role-options">
                {roles.map((role) => (
                  <label className="radio-label" key={role}>
                    <input
                      type="radio"
                      name="role"
                      value={role}
                      checked={formData.role === role}
                      onChange={handleChange}
                    />
                    <span>{role}</span>
                  </label>
                ))}
              </div>
            </div>
 
            <button type="submit" className="signup-button" disabled={isLoading}>
              {isLoading ? (
                <>
                  <span className="spinner"></span>
                  Signing up...
                </>
              ) : (
                'Sign Up'
              )}
            </button>
          </form>
 
          <p className="login-link">
            Already have an account? <a href="/login">Login</a>
          </p>
        </div>
 
        <div className="signup-image-section">
          <div className="image-overlay">
            <h2>Start your real estate journey today.</h2>
            <p>Join our community to buy, sell, or rent properties with ease.</p>
            <div className="features">
              <div className="feature">
                <i className="fas fa-home"></i>
                <span>Find Homes</span>
              </div>
              <div className="feature">
                <i className="fas fa-tags"></i>
                <span>List Properties</span>
              </div>
              <div className="feature">
                <i className="fas fa-handshake"></i>
                <span>Connect with Agents</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
 
export default SignupPage001;