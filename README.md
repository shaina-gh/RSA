# 🔐 RSA Encryption & Decryption in Java

> **Java implementation of the RSA asymmetric cryptographic system supporting both numeric and alphanumeric message encryption.**

---

### 📌 Author
**Shaina**  

---

## 🎯 Objective

To implement **RSA public-key encryption and decryption** in Java without using cryptographic libraries. This implementation supports:
- Numeric message encryption/decryption
- Alphanumeric message conversion and processing
- Key generation from user-supplied prime inputs

---

## ✅ Features

- RSA Key Generation with validation
- Numeric message encryption and decryption
- Alphanumeric message conversion and secure encryption
- Manual modular inverse calculation
- Custom base-37 encoding for alphanumeric characters

---

## 🛠️ How RSA Works

1. **Choose two prime numbers**, `p` and `q`
2. Compute:
   - `n = p * q`
   - `φ(n) = (p - 1) * (q - 1)`
3. Choose public exponent `e` such that `gcd(e, φ(n)) = 1`
4. Compute private exponent `d` such that `(d * e) % φ(n) = 1`
5. Encrypt: `cipher = (message ^ e) % n`
6. Decrypt: `message = (cipher ^ d) % n`

---

## ▶️ How to Compile & Run

```bash
javac RSA.java
java RSA
```

---

##🧪 Sample Input/Output

### 🔢 Numeric Example
```text
Enter a numeric message: 23
Original Message: 23
Enter a prime number (p): 3
Enter a prime number (q): 7
Public Key (e, n): (5, 21)
Private Key (d, n): (5, 21)
Warning: Message must be less than n=21. Using 2 instead.
Encrypted Message: 11
Decrypted Message: 2
```

### 🔡 Alphanumeric Example
```text
Enter an alphanumeric message: cryp12
Original Message: cryp12
Enter a prime number (p): 3
Enter a prime number (q): 5
Public Key (e, n): (3, 15)
Private Key (d, n): (3, 15)
Numeric Representation: 2 17 24 15 27 28
Encrypted Message (Numeric Form): 8 8 9 0 3 7
Encrypted Message (Alphanumeric Form): iijadh
Decrypted Message: ccjamn
```

---

## ✅ Result
The RSA encryption scheme was successfully implemented. Different ciphertexts were generated for different key pairs, and the original message was correctly restored after decryption.

---

## ⚠️ Disclaimer
This RSA implementation is intended for educational purposes only. It uses small prime numbers and lacks real-world security features like padding, secure key storage, and strong entropy for key generation.

---

## 🍴 How to Fork This Repository
Want to use or improve this project?

🔗 Repository URL
https://github.com/shaina-gh/RSA

🔄 Step-by-Step Guide
Visit https://github.com/shaina-gh/RSA

Click the "Fork" button on the top-right.

Clone your forked repository:
```bash
git clone https://github.com/YOUR-USERNAME/RSA.git
cd RSA
```

Compile and run:
```bash
javac RSA.java
java RSA
```

---






