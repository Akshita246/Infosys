<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to ATM Interface</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* Style the body */
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f5f5dc, #d2b48c);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Style the container */
        .container {
            background-color: #fffaf0;
            padding: 40px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            animation: fadeIn 1s ease-in-out;
        }

        /* Style for heading */
        h1 {
            color: #4b3f2a;
            font-size: 28px;
            margin-bottom: 20px;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        /* Style for paragraph */
        p {
            color: #6f4f28;
            font-size: 16px;
            line-height: 1.5;
            margin-bottom: 20px;
        }

        /* Style for section headings */
        h3 {
            color: #6f4f28;
            font-size: 18px;
            margin-bottom: 15px;
        }

        /* Style the link */
        a {
            display: inline-block;
            padding: 15px 30px;
            margin-top: 20px;
            background-color: #8b5e3c;
            color: white;
            text-decoration: none;
            font-size: 16px;
            font-weight: bold;
            border-radius: 50px;
            transition: background-color 0.3s ease;
            box-shadow: 0 3px 10px rgba(139, 94, 60, 0.3);
        }

        /* Hover effect on the link */
        a:hover {
            background-color: #6f4f28;
            box-shadow: 0 6px 15px rgba(139, 94, 60, 0.6);
        }

        /* Smooth fade-in effect for the container */
        @keyframes fadeIn {
            0% {
                opacity: 0;
                transform: translateY(-30px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Your ATM Interface 🏦</h1>
        <p>We're excited to have you here! Our ATM interface is designed to make banking easy and efficient. With just a few clicks, you can:</p>
        <h3>💵 Check your account balance</h3>
        <h3>💳 Withdraw cash quickly and securely</h3>
        <h3>📊 View recent transactions</h3>
        <h3>💸 Transfer funds between accounts</h3>
        <h3>📝 Update your personal information</h3>
        <p>To get started, simply log in using your card number and PIN. If you're new, please follow the instructions on the login page to set up your account. 🌟</p>
        <a href="loginForm.html">Click Here to Log In</a>
    </div>
</body>
</html>