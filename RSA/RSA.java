import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RSA {
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int modExponentiation(int base, int exp, int mod) {
        long result = 1;
        long b = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * b) % mod;
            }
            b = (b * b) % mod;
            exp >>= 1;
        }
        return (int) result;
    }

    public static int computeModInverse(int e, int phi) {
        // Extended Euclidean Algorithm for finding modular inverse
        int m0 = phi;
        int y = 0, x = 1;
        
        if (phi == 1)
            return 0;
            
        while (e > 1) {
            // q is quotient
            int q = e / phi;
            int t = phi;
            
            // phi is remainder now, process same as Euclid's algo
            phi = e % phi;
            e = t;
            t = y;
            
            // Update y and x
            y = x - q * y;
            x = t;
        }
        
        // Make x positive
        if (x < 0)
            x += m0;
            
        return x;
    }

    public static int computeGcd(int x, int y) {
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

    public static int getPrime(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (isPrime(num)) {
                    return num;
                }
                System.out.println("Invalid input. Please enter a valid prime number.");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a numeric prime number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public static int[] generateRsaKeys(Scanner scanner) {
        int p = getPrime(scanner, "Enter a prime number (p): ");
        int q = getPrime(scanner, "Enter a prime number (q): ");

        int n = p * q;
        int phiN = (p - 1) * (q - 1);

        int e = 2;
        while (e < phiN) {
            if (computeGcd(e, phiN) == 1) {
                break;
            }
            e++;
        }

        int d = computeModInverse(e, phiN);
        
        // Verify that e*d mod phiN = 1
        if ((e * (long)d) % phiN != 1) {
            System.out.println("Error: Invalid modular inverse. Try different primes.");
            return null;
        }

        return new int[]{e, d, n};
    }

    public static int rsaEncrypt(int msg, int e, int n) {
        return modExponentiation(msg, e, n);
    }

    public static int rsaDecrypt(int ciphertext, int d, int n) {
        return modExponentiation(ciphertext, d, n);
    }

    public static List<Integer> textToNumeric(String text) {
        List<Integer> numList = new ArrayList<>();
        text = text.toLowerCase();
        
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                numList.add(ch - 'a');
            } else if (Character.isDigit(ch)) {
                numList.add(ch - '0' + 26);
            } else if (ch == ' ') {
                numList.add(36); // Space character
            }
        }
        
        return numList;
    }

    public static char numericToText(int num) {
        if (0 <= num && num <= 25) {
            return (char) (num + 'a');
        } else if (26 <= num && num <= 35) {
            return (char) (num - 26 + '0');
        } else if (num == 36) {
            return ' '; // Space character
        }
        return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Numeric Message Encryption ---");
        System.out.print("Enter a numeric message: ");
        try {
            int originalMessage = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            System.out.println("Original Message: " + originalMessage);
            
            int[] keys = generateRsaKeys(scanner);
            if (keys != null) {
                int e = keys[0];
                int d = keys[1];
                int n = keys[2];
                
                System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
                System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
                
                // Check if message is less than n
                if (originalMessage >= n) {
                    System.out.println("Warning: Message must be less than n=" + n + ". Using " + (originalMessage % n) + " instead.");
                    originalMessage %= n;
                }
                
                int encryptedMsg = rsaEncrypt(originalMessage, e, n);
                System.out.println("Encrypted Message: " + encryptedMsg);
                
                int decryptedMsg = rsaDecrypt(encryptedMsg, d, n);
                System.out.println("Decrypted Message: " + decryptedMsg);
            }
        } catch (Exception e) {
            System.out.println("Invalid numeric input. Please restart the program.");
            scanner.nextLine(); // Clear the invalid input
        }
        
        System.out.println("\n--- Alphanumeric Message Handling ---");
        System.out.print("Enter an alphanumeric message: ");
        String textMessage = scanner.nextLine();
        
        System.out.println("Original Message: " + textMessage);
        
        int[] keys = generateRsaKeys(scanner);
        if (keys != null) {
            int e = keys[0];
            int d = keys[1];
            int n = keys[2];
            
            System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
            System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
            
            List<Integer> messageNumbers = textToNumeric(textMessage);
            System.out.print("Numeric Representation: ");
            for (int num : messageNumbers) {
                System.out.print(num + " ");
            }
            System.out.println();
            
            List<Integer> encryptedNumbers = new ArrayList<>();
            for (int m : messageNumbers) {
                if (m >= n) {
                    System.out.println("Warning: Character value " + m + " must be less than n=" + n + ". Using " + (m % n) + " instead.");
                    m %= n;
                }
                encryptedNumbers.add(rsaEncrypt(m, e, n));
            }
            
            System.out.print("Encrypted Message (Numeric Form): ");
            for (int num : encryptedNumbers) {
                System.out.print(num + " ");
            }
            System.out.println();
            
            StringBuilder encryptedText = new StringBuilder();
            for (int num : encryptedNumbers) {
                encryptedText.append(numericToText(num % 37));
            }
            System.out.println("Encrypted Message (Alphanumeric Form): " + encryptedText);
            
            List<Integer> decryptedNumbers = new ArrayList<>();
            for (int num : encryptedNumbers) {
                decryptedNumbers.add(rsaDecrypt(num, d, n));
            }
            
            StringBuilder decryptedText = new StringBuilder();
            for (int num : decryptedNumbers) {
                decryptedText.append(numericToText(num));
            }
            System.out.println("Decrypted Message: " + decryptedText);
        }
        
        scanner.close();
    }
}
/*
 * --- Numeric Message Encryption ---
 * Enter a numeric message: 23
 * Original Message: 23
 * Enter a prime number (p): 3
 * Enter a prime number (q): 7
 * Public Key (e, n): (5, 21)
 * Private Key (d, n): (5, 21)
 * Warning: Message must be less than n=21. Using 2 instead.
 * Encrypted Message: 11
 * Decrypted Message: 2
 * 
 * --- Alphanumeric Message Handling ---
 * Enter an alphanumeric message: cryp12
 * Original Message: cryp12
 * Enter a prime number (p): 3
 * Enter a prime number (q): 5
 * Public Key (e, n): (3, 15)
 * Private Key (d, n): (3, 15)
 * Numeric Representation: 2 17 24 15 27 28
 * Warning: Character value 17 must be less than n=15. Using 2 instead.
 * Warning: Character value 24 must be less than n=15. Using 9 instead.
 * Warning: Character value 15 must be less than n=15. Using 0 instead.
 * Warning: Character value 27 must be less than n=15. Using 12 instead.
 * Warning: Character value 28 must be less than n=15. Using 13 instead.
 * Encrypted Message (Numeric Form): 8 8 9 0 3 7
 * Encrypted Message (Alphanumeric Form): iijadh
 * Decrypted Message: ccjamn
 */