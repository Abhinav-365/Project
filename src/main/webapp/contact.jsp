<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Contact Us - BookStore</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 40px auto;
      max-width: 800px;
      background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
      color: #ffffff;
      padding: 20px;
    }
    h1 {
      color: #ffffff;
      text-align: center;
      margin-bottom: 30px;
    }
    form {
      max-width: 500px;
      margin: 0 auto;
      display: flex;
      flex-direction: column;
      gap: 15px;
    }
    label {
      font-weight: bold;
      margin-bottom: 5px;
    }
    input, textarea {
      padding: 10px;
      border-radius: 5px;
      border: 1px solid #ccc;
      font-size: 16px;
      resize: vertical;
    }
    button {
      background: #ffffff;
      border: none;
      padding: 12px;
      font-weight: bold;
      border-radius: 5px;
      cursor: pointer;
      color: black;
      transition: background-color 0.3s ease;
    }
    button:hover {
      background: #00bfa5;
      color: white;
    }
    .info {
      text-align: center;
      margin-top: 20px;
      color: #666;
    }
  </style>
</head>
<body>

  <h1>Contact Us</h1>

  <form action="contactSubmit" method="post">
    <label for="name">Name *</label>
    <input type="text" id="name" name="name" required />

    <label for="email">Email *</label>
    <input type="email" id="email" name="email" required />

    <label for="message">Message *</label>
    <textarea id="message" name="message" rows="6" required></textarea>

    <button type="submit">Send Message</button>
  </form>

  <div class="info">
    <p>Or email us directly at <a href="mailto:support@bookstore.com">support@bookstore.com</a></p>
  </div>

</body>
</html>
